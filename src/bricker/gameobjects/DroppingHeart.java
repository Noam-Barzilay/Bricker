package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * DroppingHeart is a GameObject representing a heart that drops from the screen.
 * It extends the basic GameObject class and includes additional functionality
 * such as collision handling, movement, and removal when reaching the bottom of the screen.
 * @author Idan Hippach, Noam Barzilay
 */
public class DroppingHeart extends GameObject {
    private static final int MOVEMENT_SPEED = 100;
    private CollisionStrategy collisionStrategy;
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjects;

    /**
     * Constructs a DroppingHeart object with the specified parameters.
     *
     * @param topLeftCorner      Position of the heart, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the heart. Can be null, in which case
     *                           the DroppingHeart will not be rendered.
     * @param windowDimensions   The window dimensions representing the size of the game window.
     * @param gameObjects        Collection of game objects to interact with during updates.
     * @param collisionStrategy  The collision strategy to execute custom behavior on collisions with this
     *                          heart.
     */
    public DroppingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         Vector2 windowDimensions, GameObjectCollection gameObjects,
                         CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.setVelocity(Vector2.DOWN.mult(MOVEMENT_SPEED));
        this.collisionStrategy = collisionStrategy;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
    }

    /**
     * Determines whether the dropping heart should collide with a specific game object.
     * @param other The GameObject to check for collision eligibility.
     * @return True if the heart should collide with the specified game object (Main Paddle), false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals("Main Paddle");
    }

    /**
     * Called on the first frame of a collision. Executes the collision strategy.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }

    /**
     * Updates the position of the dropping heart and removes it when it reaches the bottom of the screen.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() == windowDimensions.y()) {
            gameObjects.removeGameObject(this);
        }
    }
}

