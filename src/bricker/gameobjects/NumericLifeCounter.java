package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.Color;

/**
 * NumericLifeCounter is a GameObject that displays the remaining lives as a numeric counter.
 * It extends the basic GameObject class and includes functionality for updating and rendering
 * the numeric representation of remaining lives on the screen. The displayed number changes color
 * based on the remaining lives.
 * @author Idan Hippach, Noam Barzilay
 */
public class NumericLifeCounter extends GameObject {
    private static final String FOUR_LIVES_STRING = "4";
    private static final String THREE_LIVES_STRING = "3";
    private static final String TWO_LIVES_STRING = "2";
    private static final String ONE_LIFE_STRING = "1";
    private static final int FOUR_LIVES_REMAINING = 4;
    private static final int THREE_LIVES_REMAINING = 3;
    private static final int TWO_LIVES_REMAINING = 2;
    private static final int ONE_LIVE_REMAINING = 1;
    private TextRenderable textRenderable;
    private Counter livesCounter;

    /**
     * Constructs a NumericLifeCounter Object with the specified parameters.
     * @param topLeftCorner            The top-left corner position of the counter in window coordinates.
     * @param dimensions               The dimensions of the counter in window coordinates.
     * @param livesCounter             The Counter tracking the remaining lives.
     * @param gameObjectCollection     The collection of game objects to which the counter is added.
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter livesCounter,
                              GameObjectCollection gameObjectCollection){
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        // initialize text Renderable variable
        textRenderable = new TextRenderable(THREE_LIVES_STRING);
        // create the text Renderable object and add it to the game
        GameObject digit = new GameObject(topLeftCorner, dimensions, textRenderable);
        gameObjectCollection.addGameObject(digit, Layer.UI);
    }

    /**
     * Updates the NumericLifeCounter based on the remaining lives and adjusts the displayed number's color.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    public void update(float deltaTime){
        super.update(deltaTime);
        if (livesCounter.value() == FOUR_LIVES_REMAINING){
            textRenderable.setString(FOUR_LIVES_STRING);
            textRenderable.setColor(Color.green);
        }
        if (livesCounter.value() == THREE_LIVES_REMAINING){
            textRenderable.setString(THREE_LIVES_STRING);
            textRenderable.setColor(Color.green);
        }
        else if (livesCounter.value() == TWO_LIVES_REMAINING){
            textRenderable.setString(TWO_LIVES_STRING);
            textRenderable.setColor(Color.yellow);
        }
        else if (livesCounter.value() == ONE_LIVE_REMAINING){
            textRenderable.setString(ONE_LIFE_STRING);
            textRenderable.setColor(Color.red);
        }
    }
}
