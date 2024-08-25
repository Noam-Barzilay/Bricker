package bricker.brick_strategies;

import danogl.GameObject;

/**
 * DoubleBehaviorStrategy represents a collision strategy that combines the behaviors of two
 * individual collision strategies. When a collision occurs, both specified strategies are applied.
 * @author Idan Hippach, Noam Barzilay
 */
public class DoubleBehaviorStrategy implements CollisionStrategyDecorator {
    private CollisionStrategy collisionStrategy1;
    private CollisionStrategy collisionStrategy2;

    /**
     * Constructs a DoubleBehaviorStrategy with the specified collision strategies.
     * @param collisionStrategy1 The first collision strategy to be combined.
     * @param collisionStrategy2 The second collision strategy to be combined.
     */
    public DoubleBehaviorStrategy(CollisionStrategy collisionStrategy1,
                                  CollisionStrategy collisionStrategy2) {
        this.collisionStrategy1 = collisionStrategy1;
        this.collisionStrategy2 = collisionStrategy2;
    }

    /**
     * Handles the collision between two game objects by applying the behaviors of both specified collision
     * strategies.
     * @param thisObj  The game object associated with this collision strategy.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        collisionStrategy1.onCollision(thisObj, otherObj);
        collisionStrategy2.onCollision(thisObj, otherObj);
    }
}
