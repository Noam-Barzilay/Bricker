package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * ExtraPaddleCollisionHandler is a collision strategy for handling collisions between
 * the main paddle and an ExtraPaddle object. This class keeps track of the number of hits
 * on the ExtraPaddle and removes it from the game when the hit count reaches a specified limit.
 * @author Idan Hippach, Noam Barzilay
 */
public class ExtraPaddleCollisionHandler implements CollisionStrategy {
    GameObjectCollection gameObjects;
    private Counter hitsCounter;

    /**
     * Constructs an ExtraPaddleCollisionHandler with the specified parameters.
     * @param gameObjects The collection of game objects to be used for collision handling.
     */
    public ExtraPaddleCollisionHandler(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
        this.hitsCounter = new Counter();
    }

    /**
     * Handles the collision between the main paddle and an ExtraPaddle object.
     * Increments the hits counter and removes the ExtraPaddle from the game when the hit count reaches the
     * specified limit.
     * @param thisObj  The game object associated with this collision strategy (ExtraPaddle).
     * @param otherObj The other game object involved in the collision (main paddle).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        hitsCounter.increment();
        if (hitsCounter.value() == Constants.PADDLE_HITS_TILL_DESTRUCTION) {
            hitsCounter.reset();
            gameObjects.removeGameObject(thisObj);
        }
    }
}
