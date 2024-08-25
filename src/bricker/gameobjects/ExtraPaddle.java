package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * ExtraPaddle is a specialized Paddle GameObject that extends the basic Paddle class.
 * It includes additional functionality such as collision handling and the ability to execute
 * a custom collision strategy on collisions with other game objects.
 * @author Idan Hippach, Noam Barzilay
 */
public class ExtraPaddle extends Paddle {
    private CollisionStrategy collisionStrategy;

    /**
     * Constructs an ExtraPaddle Object with the specified parameters.
     * @param topLeftCorner      Position of the extra paddle, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the extra paddle. Can be null, in which case
     *                           the ExtraPaddle will not be rendered.
     * @param inputListener      The user's input listener.
     * @param windowDimensions   The game window's dimensions.
     * @param collisionStrategy  The collision strategy to execute custom behavior on collisions with this
     *                           extra paddle.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions,
                       CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Called on the first frame of a collision. Executes the custom collision strategy if provided.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        super.onCollisionEnter(other, collision);
        if (collisionStrategy != null) {
            collisionStrategy.onCollision(this, other);
        }
    }
}
