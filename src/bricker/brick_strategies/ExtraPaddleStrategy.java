package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * ExtraPaddleStrategy is a collision strategy for handling collisions between
 * bricks and the main paddle. When a collision occurs, this strategy checks if
 * there is already an ExtraPaddle in the game, and if not, it adds a new one.
 * @author Idan Hippach, Noam Barzilay
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private ExtraPaddle extraPaddle;

    /**
     * Constructs an ExtraPaddleStrategy with the specified parameters.
     * @param gameObjects      The collection of game objects to be used for collision handling.
     * @param imageReader      The ImageReader for reading images associated with the ExtraPaddle.
     * @param inputListener    The UserInputListener for handling user input in the ExtraPaddle.
     * @param windowDimensions The dimensions of the game window.
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjects, ImageReader imageReader, UserInputListener
            inputListener, Vector2 windowDimensions) {
        super(gameObjects);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Handles the collision between bricks and the main paddle.
     * If there is no existing ExtraPaddle in the game, it adds a new one.
     * @param thisObj  The game object associated with this collision strategy (brick).
     * @param otherObj The other game object involved in the collision (main paddle).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getTag().equals("ExtraPaddle")) {
                return;
            }
        }
        addPaddle();
    }

    /*
     * Adds a new ExtraPaddle to the game with the specified parameters.
     */
    private void addPaddle() {
        Renderable paddleImg = imageReader.readImage(Constants.PADDLE_IMAGE, true);

        extraPaddle = new ExtraPaddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT), paddleImg, inputListener, windowDimensions,
                new ExtraPaddleCollisionHandler(gameObjects));
        extraPaddle.setCenter(windowDimensions.mult(0.5f));
        extraPaddle.setTag("ExtraPaddle");
        gameObjects.addGameObject(extraPaddle);
    }
}
