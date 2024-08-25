package bricker.main;

/**
 * Constants class contains static final fields that represent various parameters and assets used in the game.
 * These constants include dimensions, image paths, speeds, and other configuration values used throughout the
 * game.
 * Fields are organized into categories such as Ball, Paddle, Brick, GraphicLifeCounter, NumericLifeCounter,
 * and more.
 * This class serves as a central location for configuring and managing constants used in the game.
 *
 * @author Idan Hippach, Noam Barzilay
 */
public class Constants {
    /* Prevents instantiation of this class. */
    private Constants() {
    }

    /**
     * The size of the border in the game.
     */
    public static final int BORDER_SIZE = 10;
    /**
     * The radius of the ball.
     */
    public static final int BALL_RADIUS = 20;
    /**
     * The speed of the ball.
     */
    public static final int BALL_SPEED = 250;
    /**
     * The file path for the image of the ball.
     */
    public static final String BALL_IMAGE = "assets/ball.png";
    /**
     * The file path for the sound effect played when the ball hits an object.
     */
    public static final String BALL_SOUND = "assets/blop_cut_silenced.wav";
    /**
     * The radius of the puck.
     */
    public static final float PUCK_RADIUS = 0.75f * BALL_RADIUS;
    /**
     * The file path for the image of the puck.
     */
    public static final String PUCK_IMAGE = "assets/mockBall.png";
    /**
     * The movement speed of the paddle.
     */
    public static final float PADDLE_SPEED = 400;
    /**
     * The height of the paddle.
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The width of the paddle.
     */
    public static final int PADDLE_WIDTH = 100;
    /**
     * The file path for the image of the paddle.
     */
    public static final String PADDLE_IMAGE = "assets/paddle.png";
    /**
     * The number of hits a paddle can take before being destroyed.
     */
    public static final int PADDLE_HITS_TILL_DESTRUCTION = 4;
    /**
     * The height of a brick.
     */
    public static final int BRICK_HEIGHT = 15;
    /**
     * The default number of rows of bricks in the game.
     */
    public static final int DEFAULT_BRICK_ROWS = 7;
    /**
     * The number of bricks per row in the game.
     */
    public static final int DEFAULT_BRICKS_PER_ROW = 8;
    /**
     * The space between bricks.
     */
    public static final int BRICKS_SPACE = 5;
    /**
     * The file path for the image of the brick.
     */
    public static final String BRICK_IMAGE = "assets/brick.png";
    /**
     * The size of the heart image used in the graphic life counter.
     */
    public static final int HEART_SIZE = 15;
    /**
     * The file path for the image of the heart used in the graphic life counter and heart strategy.
     */
    public static final String HEART_IMAGE = "assets/heart.png";
    /**
     * The text size for the digits in the numeric life counter.
     */
    public static final int DIGIT_TEXT_SIZE = 15;
    /**
     * The initial number of lives for the player.
     */
    public static final int INITIAL_NUMBER_OF_LIVES = 3;
    /**
     * The maximum number of hearts displayed in the life counter.
     */
    public static final int MAX_NUM_OF_HEARTS = 4;
    /**
     * The number of collisions a camera can withstand before being destroyed.
     */
    public static final int CAMERA_COLLISIONS_TILL_DESTRUCTION = 4;
    /**
     * The default width of the game window.
     */
    public static final int DEFAULT_WINDOW_WIDTH = 700;
    /**
     * The default height of the game window.
     */
    public static final int DEFAULT_WINDOW_HEIGHT = 500;
    /**
     * Minimum distance from window borders for object placement.
     */
    public static final int MIN_DIST = 20;
    /**
     * The title of the game window.
     */
    public static final String WINDOWS_TITLE = "Bricker";
    /**
     * The file path for the background image used in the game.
     */
    public static final String GAME_BACKGROUND = "assets/DARK_BG2_small.jpeg";
    /**
     * The message displayed in the popup when the player wins.
     */
    public static final String WIN_POPUP_MESSAGE = "You Win! Play again?";
    /**
     * The message displayed in the popup when the player loses.
     */
    public static final String LOST_POPUP_MESSAGE = "You Lost! Play again?";
}
