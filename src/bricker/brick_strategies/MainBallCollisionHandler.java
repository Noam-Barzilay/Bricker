package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * MainBallCollisionHandler is a collision strategy for handling collisions between
 * the main ball and other game objects. This class keeps track of the main ball when
 * a camera change was set till it hits a certain number of collisions, then it resets.
 * @author Idan Hippach, Noam Barzilay
 */
public class MainBallCollisionHandler implements CollisionStrategy {
    private BrickerGameManager gameManager;
    private Counter cameraCounter;

    /**
     * Constructs a MainBallCollisionHandler with the specified parameters.
     * @param gameManager The game manager responsible for managing the overall game state.
     */
    public MainBallCollisionHandler(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
        this.cameraCounter = new Counter();
    }

    /**
     * Handles collisions between the main ball and other game objects.
     * Keeps track of the main ball when a camera change was set till it hits a certain number of collisions,
     * then it resets.
     * @param thisObj  The game object associated with this collision strategy (main ball).
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.camera() != null) {
            cameraCounter.increment();
            if (cameraCounter.value() == Constants.CAMERA_COLLISIONS_TILL_DESTRUCTION + 1) {
                cameraCounter.reset();
                gameManager.setCamera(null);
            }
        }
    }
}
