package bricker.brick_strategies;

import danogl.GameObject;

/**
 * The CollisionStrategy interface defines a contract for classes that handle collisions between game objects.
 * Implementing classes must provide logic for the actions to be taken when a collision occurs.
 * @author Idan Hippach, Noam Barzilay
 */
public interface CollisionStrategy {
    /**
     * Defines the action to be taken when a collision occurs between two game objects.
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);

}
