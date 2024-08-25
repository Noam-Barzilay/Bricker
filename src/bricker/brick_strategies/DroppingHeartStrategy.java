package bricker.brick_strategies;

import bricker.gameobjects.GraphicLifeCounter;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * DroppingHeartStrategy is a collision strategy for handling collisions between DroppingHeart objects
 * and the main paddle. When a collision occurs, this strategy increments the lives counter
 * if the number of lives is below the maximum limit and removes the DroppingHeart from the game.
 */
public class DroppingHeartStrategy implements CollisionStrategy {

    /**
     * Counter for tracking the number of lives in the game.
     */
    private Counter livesCounter;

    /**
     * Collection of game objects used to manage and update the game state.
     */
    private GameObjectCollection gameObjects;

    /**
     * Constructs a DroppingHeartStrategy with the specified parameters.
     *
     * @param livesCounter The Counter object tracking the number of lives in the game.
     * @param gameObjects  The collection of game objects to be used for collision handling.
     */
    public DroppingHeartStrategy(Counter livesCounter, GameObjectCollection gameObjects) {
        this.livesCounter = livesCounter;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision between a DroppingHeart object and the main paddle.
     * Increments the lives counter if the number of lives is below the maximum limit,
     * and removes the DroppingHeart from the game.
     *
     * @param thisObj  The game object associated with this collision strategy (DroppingHeart).
     * @param otherObj The other game object involved in the collision (main paddle).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (thisObj.shouldCollideWith(otherObj)) {
            // Increment lives counter if the number of lives is below the maximum limit
            if (livesCounter.value() < Constants.MAX_NUM_OF_HEARTS) {
                livesCounter.increment();
            }
            // Remove the DroppingHeart from the game
            gameObjects.removeGameObject(thisObj);
        }
    }
}
