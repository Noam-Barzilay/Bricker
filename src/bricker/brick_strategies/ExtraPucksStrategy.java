package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * ExtraPucksStrategy is a collision strategy for handling collisions between bricks
 * and the main paddle. When a collision occurs, this strategy spawns two extra pucks
 * at the center of the brick, each with a randomized velocity direction.
 * @author Idan Hippach, Noam Barzilay
 */
public class ExtraPucksStrategy extends BasicCollisionStrategy {
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Random rand = new Random();

    /**
     * Constructs an ExtraPucksStrategy with the specified parameters.
     * @param gameObjects  The collection of game objects to be used for collision handling.
     * @param soundReader  The SoundReader for reading sounds associated with the extra pucks.
     * @param imageReader  The ImageReader for reading images associated with the extra pucks.
     */
    public ExtraPucksStrategy(GameObjectCollection gameObjects, SoundReader soundReader,
                              ImageReader imageReader) {
        super(gameObjects);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Handles the collision between bricks and the main paddle.
     * Spawns two extra pucks at the center of the brick, each with a randomized velocity direction.
     * @param thisObj  The game object associated with this collision strategy (brick).
     * @param otherObj The other game object involved in the collision (main paddle).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        // spawn both pucks
        addPucks(thisObj);
    }

    /*
     * Spawns two extra pucks at the center of the specified brick, each with a randomized velocity direction.
     */
    private void addPucks(GameObject curBrick){
        Renderable ballImage = imageReader.readImage(Constants.PUCK_IMAGE, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND);
        Ball puck1 = new Ball(Vector2.ZERO, new Vector2(Constants.PUCK_RADIUS, Constants.PUCK_RADIUS),
                ballImage, collisionSound,
                null);
        Ball puck2 = new Ball(Vector2.ZERO, new Vector2(Constants.PUCK_RADIUS, Constants.PUCK_RADIUS),
                ballImage, collisionSound,
                null);
        spawnBall(puck1, curBrick);
        spawnBall(puck2, curBrick);
        gameObjects.addGameObject(puck1);
        gameObjects.addGameObject(puck2);
    }

    /*
     * Sets the position and velocity of the specified puck based in the center of the brick
     * and randomizes the direction of each axis' velocity.
     */
    private void spawnBall(Ball puck, GameObject curBrick) {
        // set puck to spawn at the center of the brick
        puck.setCenter(new Vector2(curBrick.getCenter()));
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        // by 50% chance we invert the direction of each axis' velocity
        if (rand.nextBoolean()){
            ballVelX *= -1;
        }
        if (rand.nextBoolean()){
            ballVelY *= -1;
        }
        puck.setVelocity(new Vector2(ballVelX, ballVelY));
    }

}
