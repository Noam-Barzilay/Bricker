package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * CollisionStrategyFactory is a factory class responsible for creating different collision strategies
 * based on randomly generated values. It allows the creation of various collision strategies,
 * including strategies for extra pucks, extra paddle, camera change, dropping heart, double behavior, and
 * basic behavior.
 * @author Idan Hippach, Noam Barzilay
 */
public class CollisionStrategyFactory {
    private BrickerGameManager gameManager;
    private GameObjectCollection gameObjects;
    private Ball ball;
    private Vector2 windowDimensions;
    private SoundReader soundReader;
    private ImageReader imageReader;
    private UserInputListener userInputListener;
    private WindowController windowController;
    private Counter livesCounter;
    private Random random = new Random();

    /**
     * Constructs a CollisionStrategyFactory with the specified parameters.
     * @param gameManager         The BrickerGameManager responsible for managing the game state.
     * @param gameObjects         The collection of game objects to be used for collision handling.
     * @param ball                The Ball object associated with collision strategies.
     * @param windowDimensions    The dimensions of the game window.
     * @param soundReader         The SoundReader for reading sounds associated with collision strategies.
     * @param imageReader         The ImageReader for reading images associated with collision strategies.
     * @param userInputListener   The UserInputListener for handling user input in collision strategies.
     * @param windowController    The WindowController for managing the game window.
     * @param livesCounter        The Counter object tracking the number of lives in the game.
     */
    public CollisionStrategyFactory(BrickerGameManager gameManager, GameObjectCollection gameObjects,
                                    Ball ball, Vector2 windowDimensions, SoundReader soundReader,
                                    ImageReader imageReader, UserInputListener userInputListener,
                                    WindowController windowController, Counter livesCounter) {
        this.gameManager = gameManager;
        this.gameObjects = gameObjects;
        this.ball = ball;
        this.windowDimensions = windowDimensions;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.userInputListener = userInputListener;
        this.windowController = windowController;
        this.livesCounter = livesCounter;
    }

    /**
     * Randomly generates a collision strategy based on predefined probabilities.
     * @return A CollisionStrategy instance representing the randomly generated strategy.
     */
    public CollisionStrategy buildStrategy() {
        double generatedNum = random.nextDouble();
        if (generatedNum > 0.9) {
            return selectStrategy(StrategyType.EXTRA_PUCKS);
        }
        else if (generatedNum > 0.8) {
            return selectStrategy(StrategyType.EXTRA_PADDLE);
        }
        else if (generatedNum > 0.7) {
            return selectStrategy(StrategyType.CAMERA_CHANGE);
        }
        else if (generatedNum > 0.6) {
            return selectStrategy(StrategyType.DROPPING_HEART);
        }
        else if (generatedNum > 0.5) {
            return createDoubleBehaviorStrategy();
        }
        return selectStrategy(StrategyType.BASIC_BEHAVIOR);
    }

    /*
     * Creates a specific collision strategy based on the given enum of the desired collision strategy
     * Returns a CollisionStrategy instance corresponding to the selected strategy index (or null for
     * Double Behavior).
     */
    private CollisionStrategy selectStrategy(StrategyType strategyType) {
        if (strategyType == StrategyType.EXTRA_PUCKS) {
            return new ExtraPucksStrategy(gameObjects, soundReader, imageReader);
        }
        if (strategyType == StrategyType.EXTRA_PADDLE) {
            return new ExtraPaddleStrategy(gameObjects, imageReader, userInputListener, windowDimensions);
        }
        if (strategyType == StrategyType.CAMERA_CHANGE) {
            return new CameraChangeStrategy(gameObjects, ball, windowController, gameManager);
        }
        if (strategyType == StrategyType.DROPPING_HEART) {
            return new BrickCollisionAddsHeartStrategy(livesCounter, gameObjects, imageReader,
                    windowDimensions);
        }
        if (strategyType == StrategyType.DOUBLE_BEHAVIOR) {
            return null;
        }
        if (strategyType == StrategyType.BASIC_BEHAVIOR) {
            return new BasicCollisionStrategy(gameObjects);
        }
        return null;
    }

    /*
     * Creates a CollisionStrategy with a combination of two randomly selected strategies,
     * ensuring that at least one strategy is not null.
     */
    private CollisionStrategy createDoubleBehaviorStrategy() {
        StrategyType[] values = StrategyType.values();
        // Exclude BASIC_BEHAVIOR from random selection, adjust range accordingly
        int range = values.length - 1; // Assuming BASIC_BEHAVIOR is the last enum and we want to exclude it
        // Select two random strategies from special strategies
        StrategyType index1 = values[random.nextInt(range)];
        StrategyType index2 = values[random.nextInt(range)];
        CollisionStrategy collisionStrategy1 = selectStrategy(index1);
        CollisionStrategy collisionStrategy2 = selectStrategy(index2);

        // Ensure that both strategies are not double behavior
        while (collisionStrategy1 == null && collisionStrategy2 == null) {
            index1 = values[random.nextInt(range)];
            index2 = values[random.nextInt(range)];
            collisionStrategy1 = selectStrategy(index1);
            collisionStrategy2 = selectStrategy(index2);
        }
        // if one of the strategies is null, create a double behavior with a random special strategy
        // Exclude DOUBLE_BEHAVIOR from random selection, adjust range accordingly
        range--; // Assuming DOUBLE_BEHAVIOR is one place before BASIC_BEHAVIOR
        if (collisionStrategy1 == null) {
            index1 = values[random.nextInt(range)];
            index2 = values[random.nextInt(range)];
            collisionStrategy1 = new DoubleBehaviorStrategy(selectStrategy(index1), selectStrategy(index2));
        }
        if (collisionStrategy2 == null) {
            index1 = values[random.nextInt(range)];
            index2 = values[random.nextInt(range)];
            collisionStrategy2 = new DoubleBehaviorStrategy(selectStrategy(index1), selectStrategy(index2));
        }
        return new DoubleBehaviorStrategy(collisionStrategy1, collisionStrategy2);
    }
}

