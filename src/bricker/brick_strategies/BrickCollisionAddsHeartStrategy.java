package bricker.brick_strategies;

import bricker.gameobjects.DroppingHeart;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * BrickCollisionAddsHeartStrategy represents a collision strategy for handling collisions
 * between bricks and other game objects. In this strategy, when a collision occurs,
 * a DroppingHeart object is created and added to the game, representing an additional life.
 * @author Idan Hippach, Noam Barzilay
 */
public class BrickCollisionAddsHeartStrategy extends BasicCollisionStrategy {
    private Counter livesCounter;
    private Renderable renderable;
    private Vector2 windowDimensions;

    /**
     * Constructs a BrickCollisionAddsHeartStrategy with the specified parameters.
     * @param livesCounter     The Counter object tracking the number of lives in the game.
     * @param gameObjects      The collection of game objects to be used for collision handling.
     * @param imageReader      The ImageReader for reading the heart image.
     * @param windowDimensions The dimensions of the game window.
     */
    public BrickCollisionAddsHeartStrategy(Counter livesCounter, GameObjectCollection gameObjects,
                                           ImageReader imageReader, Vector2 windowDimensions) {
        super(gameObjects);
        this.livesCounter = livesCounter;
        this.renderable = imageReader.readImage(Constants.HEART_IMAGE, true);
        this.windowDimensions = windowDimensions;
    }

    /**
     * Handles the collision between two game objects.
     * Adds a DroppingHeart to the game when a collision occurs.
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        DroppingHeart heart = new DroppingHeart(thisObj.getCenter(), new Vector2(Constants.HEART_SIZE,
                Constants. HEART_SIZE), renderable, windowDimensions, gameObjects,
                new DroppingHeartCollisionHandler(livesCounter, gameObjects));
        gameObjects.addGameObject(heart);
    }
}
