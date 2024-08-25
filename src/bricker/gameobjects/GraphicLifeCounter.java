package bricker.gameobjects;


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * GraphicLifeCounter is a GameObject that visually represents the player's remaining lives
 * in a graphical format using heart images. It extends the basic GameObject class and includes
 * functionality to update and manage the displayed hearts based on the player's current life count.
 * @author Idan Hippach, Noam Barzilay
 */
public class GraphicLifeCounter extends GameObject {
    private int numOfLives;
    private Counter livesCounter;
    private GameObjectCollection gameObjects;
    private GameObject[] hearts;

    /**
     * Constructs a GraphicLifeCounter Object with the specified parameters.
     * @param widgetTopLeftCorner      The top-left corner position of the life counter widget in window
     *                                 coordinates.
     * @param widgetDimensions         The dimensions of each heart in the life counter.
     * @param widgetRenderable         The renderable representing the heart image used in the life counter.
     * @param numOfLives               The initial number of lives to be displayed in the life counter.
     * @param livesCounter             The Counter instance representing the player's remaining lives.
     * @param gameObjectsCollection    The collection of game objects to interact with during updates.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Renderable widgetRenderable, int numOfLives, Counter livesCounter,
                              GameObjectCollection gameObjectsCollection) {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.numOfLives = numOfLives;
        this.livesCounter = livesCounter;
        this.gameObjects = gameObjectsCollection;

        hearts = new GameObject[numOfLives + 1];
        // initialize the array of hearts
        for (int i = 0; i < numOfLives; i++) {
            hearts[i] = new GameObject(widgetTopLeftCorner.add(new Vector2(i * widgetDimensions.x(),
                    widgetDimensions.y())), widgetDimensions, widgetRenderable);
            gameObjectsCollection.addGameObject(hearts[i], Layer.UI);
        }
        hearts[numOfLives] = new GameObject(widgetTopLeftCorner.add(new Vector2(numOfLives *
                widgetDimensions.x(), widgetDimensions.y())), widgetDimensions, widgetRenderable);
    }

    /**
     * Updates the GraphicLifeCounter, managing the displayed hearts based on the player's current life count.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        // if there are more hearts on the screen than there are lives left
        if (livesCounter.value() < numOfLives) {
            // remove hearts from the screen
            gameObjects.removeGameObject(hearts[livesCounter.value()], Layer.UI);
            numOfLives--;
        } else if (livesCounter.value() > numOfLives)
            // add hearts to the screen (up to 4 max)
            gameObjects.addGameObject(hearts[numOfLives++], Layer.UI);
    }
}
