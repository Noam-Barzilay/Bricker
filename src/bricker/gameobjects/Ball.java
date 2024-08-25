package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Ball is a GameObject representing a ball in the game. It extends the basic GameObject class
 * and includes additional functionality such as collision handling, sound playback on collision,
 * and a collision strategy to execute custom behavior on collisions.
 * @author Idan Hippach, Noam Barzilay
 */
public class Ball extends GameObject {
    private Sound collisionSound;
    private int collisionCounter = 0;
    private CollisionStrategy collisionStrategy;

    /**
     * Constructs a Ball object with the specified parameters.
     * @param topLeftCorner     Position of the ball, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the ball. Can be null, in which case
     *                          the Ball will not be rendered.
     * @param collisionSound    The sound to be played on collision.
     * @param collisionStrategy The collision strategy to execute custom behavior on collisions.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Called on the first frame of a collision. Handles collision-related logic such as
     * updating the velocity, playing collision sound, and executing custom collision strategy.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionCounter++;
        collisionSound.play();
        if (collisionStrategy != null) {
            collisionStrategy.onCollision(this, other);
        }
    }

    /**
     * Gets the number of collisions the ball has had.
     * @return The number of collisions the ball has had.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
