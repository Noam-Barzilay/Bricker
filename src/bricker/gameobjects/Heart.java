package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Heart is a GameObject that represents a heart object falling down the screen.
 * It extends the basic GameObject class and includes functionality for collision
 * handling with the main paddle. Hearts are typically used in games as power-ups or
 * bonus items.
 */
public class Heart extends GameObject {

    /**
     * The movement speed of the heart in pixels per second.
     */
    private static final int MOVEMENT_SPEED = 100;

    /**
     * The collection of game objects to check for collisions with the main paddle.
     */
    private GameObjectCollection gameObjectsCollection;

    /**
     * The collision strategy to be executed upon collision with the main paddle.
     */
    private CollisionStrategy collisionStrategy;

    /**
     * Constructs a Heart object with the specified parameters.
     *
     * @param topLeftCorner        The top-left corner position of the heart in window coordinates.
     * @param dimensions           The dimensions of the heart in window coordinates.
     * @param renderable           The renderable representing the heart. Can be null.
     * @param gameObjectsCollection The collection of game objects to check for collisions with the main paddle.
     * @param collisionStrategy     The collision strategy to be executed upon collision with the main paddle.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection gameObjectsCollection, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectsCollection = gameObjectsCollection;
        this.setVelocity(Vector2.DOWN.mult(MOVEMENT_SPEED));
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Determines whether the heart should collide with a specific game object.
     * Hearts should collide with the main paddle.
     *
     * @param other The GameObject to check for collision.
     * @return true if the heart should collide with the specified game object, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        for (GameObject gameObject : gameObjectsCollection) {
            if (gameObject.getTag().equals("Main Paddle")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Called on the first frame of a collision with the main paddle.
     *
     * @param other     The GameObject with which a collision occurred (main paddle).
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
