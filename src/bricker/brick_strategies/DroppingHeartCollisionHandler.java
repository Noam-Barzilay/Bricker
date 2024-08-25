package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * DroppingHeartCollisionHandler is a collision strategy for handling collisions
 * between DroppingHeart objects and the main paddle. When a collision occurs,
 * this strategy increments the lives counter and removes the DroppingHeart from the game.
 * @author Idan Hippach, Noam Barzilay
 */
public class DroppingHeartCollisionHandler implements CollisionStrategy {
    private Counter livesCounter;
    private GameObjectCollection gameObjects;

    /**
     * Constructs a DroppingHeartCollisionHandler with the specified parameters.
     *
     * @param livesCounter The Counter object tracking the number of lives in the game.
     * @param gameObjects  The collection of game objects to be used for collision handling.
     */
    public DroppingHeartCollisionHandler(Counter livesCounter, GameObjectCollection gameObjects) {
        this.livesCounter = livesCounter;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision between a DroppingHeart object and the main paddle.
     * Increments the lives counter if the number of lives is below the maximum limit,
     * and removes the DroppingHeart from the game.
     * @param thisObj  The game object associated with this collision strategy (DroppingHeart).
     * @param otherObj The other game object involved in the collision (main paddle).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (thisObj.shouldCollideWith(otherObj)) {
            if (livesCounter.value() < Constants.MAX_NUM_OF_HEARTS) {
                livesCounter.increment();
            }
            gameObjects.removeGameObject(thisObj);
        }
    }
}
