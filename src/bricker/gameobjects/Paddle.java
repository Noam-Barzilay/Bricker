package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Paddle represents a movable object in the game, controlled by the user's input.
 * It extends the basic GameObject class and includes functionality for handling input,
 * updating its position based on user commands, and ensuring it stays within the game window's bounds.
 * @author Idan Hippach, Noam Barzilay
 */
public class Paddle extends GameObject {
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Constructs a new Paddle Instance with the specified parameters.
     * @param topLeftCorner    The top-left corner position of the paddle in window coordinates.
     * @param dimensions       The dimensions of the paddle in window coordinates.
     * @param renderable       The renderable representing the paddle. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener    The user's input listener for detecting key presses.
     * @param windowDimensions The dimensions of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions){
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Should be called once per frame.
     * Updates the Paddle's position based on user input and ensures it stays within the game window's bounds.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        // initialize velocity vector to zero, so that in case no key is pressed, paddle will stay in place
        Vector2 paddleVelocity = Vector2.ZERO;
        // if left key is pressed
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            paddleVelocity = paddleVelocity.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            paddleVelocity = paddleVelocity.add(Vector2.RIGHT);
        }
        // set velocity
        setVelocity(paddleVelocity.mult(Constants.PADDLE_SPEED));

        // if the distance from window's edge is less than minDistFromBorder, we set paddle to be on edge
        if (getTopLeftCorner().x() < Constants.BORDER_SIZE) {
            setTopLeftCorner(new Vector2(Constants.BORDER_SIZE, getTopLeftCorner().y()));
        }
        if (windowDimensions.x() - Constants.BORDER_SIZE - getDimensions().x() < getTopLeftCorner().x()) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - Constants.BORDER_SIZE -
                    getDimensions().x(), getTopLeftCorner().y()));
        }
    }

}
