package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Brick is a GameObject representing a brick in the game. It extends the basic GameObject class
 * and includes additional functionality such as collision handling, a counter to represent the
 * number of current bricks in the game, and a collision strategy to execute custom behavior on collisions.
 * @author Idan Hippach, Noam Barzilay
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private final Counter bricksCounter;
    private boolean isDestroyed = false;

    /**
     * Constructs a Brick object with the specified parameters.
     * @param topLeftCorner     Position of the brick, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the brick. Can be null, in which case
     *                          the Brick will not be rendered.
     * @param collisionStrategy The collision strategy to execute custom behavior on collisions with this
     *                          brick.
     * @param counter           Counter representing the number of current bricks in the game.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksCounter = counter;
    }

    /**
     * Called on the first frame of a collision. Destroys the brick, decrements the number
     * of active bricks on the screen, and executes a custom collision strategy if provided.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        if (!isDestroyed) {
            isDestroyed = true;
            // Decrements the number of active bricks on the screen.
            bricksCounter.decrement();
            if (collisionStrategy != null) {
                collisionStrategy.onCollision(this, other);
            }
        }

    }
}
