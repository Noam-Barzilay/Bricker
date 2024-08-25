package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

/**
 * BasicCollisionStrategy represents a simple collision strategy for handling collisions
 * between game objects. In this strategy, when a collision occurs, the current brick
 * is removed from the screen.
 * @author Idan Hippach, Noam Barzilay
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    /** Collection of game objects used to manage and update the game state. */
    protected GameObjectCollection gameObjects;

    /**
     * Constructs a BasicCollisionStrategy with the specified GameObjectCollection.
     * @param gameObjects The collection of game objects to be used for collision handling.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision between two game objects.
     * In this implementation, the current brick is removed from the screen.
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        // removes the current brick from the screen
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }

}
