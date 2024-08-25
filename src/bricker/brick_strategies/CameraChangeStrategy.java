package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * CameraChangeStrategy represents a collision strategy for handling collisions between bricks and other game
 * objects.
 * When a collision occurs between the specified Ball object and another game object, this strategy changes
 * the camera to follow the Ball, creating a dynamic camera effect.
 * @author Idan Hippach, Noam Barzilay
 */
public class CameraChangeStrategy extends BasicCollisionStrategy {
    private BrickerGameManager gameManager;
    private Ball ball;
    private WindowController windowController;

    /**
     * Constructs a CameraChangeStrategy with the specified parameters.
     * @param gameObjects      The collection of game objects to be used for collision handling.
     * @param ball             The Ball object associated with this strategy.
     * @param windowController The WindowController for managing the game window.
     * @param gameManager      The BrickerGameManager responsible for managing the game state.
     */
    public CameraChangeStrategy(GameObjectCollection gameObjects, Ball ball,
                                WindowController windowController, BrickerGameManager gameManager) {
        super(gameObjects);
        this.ball = ball;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Handles the collision between two game objects.
     * If the game manager's camera is not set and the collision involves the specified Ball object,
     * a new Camera is created to follow the Ball, providing a dynamic camera effect.
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        super.onCollision(thisObj, otherObj);
        if (gameManager.camera() == null && otherObj == ball) {
            gameManager.setCamera(
                    new Camera(
                            ball, //object to follow
                            Vector2.ZERO, //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                            windowController.getWindowDimensions() //share the window dimensions
                    )
            );

        }
    }

}
