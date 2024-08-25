package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The game manager class for the Bricker game.
 * This class is responsible for initializing and managing the game's components,
 * handling user input, updating the game state, and checking for game end conditions.
 * @author Idan Hippach, Noam Barzilay
 */
public class BrickerGameManager extends GameManager {
    private Ball ball;
    private Counter bricksCounter;
    private Counter livesCounter;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private WindowController windowController;
    private Vector2 windowDimensions;

    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));

    private int brickRows;
    private int bricksPerRow;
    private int bricksNum;

    /**
     * Constructor for BrickerGameManager.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param bricksPerRow     The number of bricks per row in the game.
     * @param brickRows        The number of rows of bricks in the game.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int bricksPerRow, int brickRows){
        super(windowTitle, windowDimensions);
        this.brickRows = brickRows;
        this.bricksPerRow = bricksPerRow;
        this.bricksNum = bricksPerRow * brickRows;
    }

    /**
     * Initializes the game components, including background, borders, ball, paddle, bricks, and counters.
     * @param imageReader      The image reader for loading game assets.
     * @param soundReader      The sound reader for loading game sounds.
     * @param inputListener    The user input listener for handling player input.
     * @param windowController The window controller for managing game window operations.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController){
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.bricksCounter = new Counter(bricksNum);
        this.livesCounter = new Counter(Constants.INITIAL_NUMBER_OF_LIVES);
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.soundReader = soundReader;

        initializeBackground();
        initializeBorders();
        initializeBall();
        initializePaddle();
        initializeBricks();
        initializeGraphicLifeCounter();
        initializeNumericLifeCounter();

    }

    /**
     * Updates the game state and checks for game end conditions.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    public void update(float deltaTime){
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * Main method to run the Bricker game.
     * @param args Command-line arguments (optional): [bricksPerRow, brickRows].
     */
    public static void main(String[] args) {
        int bricksPerRow = Constants.DEFAULT_BRICKS_PER_ROW;
        int brickRowsNum = Constants.DEFAULT_BRICK_ROWS;

        if (args.length == 2){
            bricksPerRow = Integer.parseInt(args[0]);
            brickRowsNum = Integer.parseInt(args[1]);
        }
        Vector2 windowDimension = new Vector2(Constants.DEFAULT_WINDOW_WIDTH,
                Constants.DEFAULT_WINDOW_HEIGHT);
        BrickerGameManager brickerGameManager =
                new BrickerGameManager(Constants.WINDOWS_TITLE, windowDimension, bricksPerRow, brickRowsNum);
        brickerGameManager.run();
    }

    /*
     * Checks for game end conditions, such as running out of lives or destroying all bricks.
     * Displays a popup message accordingly and resets the game if the player chooses to continue.
     */
    private void checkForGameEnd() {
        // check for ball state
        // if ball reached bottom of the window, we decrement one life, and re-spawn the ball
        float ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()){
            livesCounter.decrement();
            spawnBall();
        }
        String message = "";
        // if lives is zero, meaning game is lost
        if (livesCounter.value() == 0){
            message = Constants.LOST_POPUP_MESSAGE;
        }
        // if no bricks are left, meaning game is won.
        // if 'w' is pressed, game is over and winning message pops up
        if (bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            message = Constants.WIN_POPUP_MESSAGE;
        }
        if (!message.isEmpty()) {
            // if "yes" was entered, we start a new game
            if (windowController.openYesNoDialog(message)){
                windowController.resetGame();
            }
            else {
                // else, we end it
                windowController.closeWindow();
            }
        }
    }

    /*
     * Initializes the main ball object in the game.
     */
    private void initializeBall(){
        Renderable ballImage = imageReader.readImage(Constants.BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND);
        ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_RADIUS, Constants.BALL_RADIUS), ballImage,
                collisionSound, new MainBallCollisionHandler(this));
        spawnBall();
        gameObjects().addGameObject(ball);
    }

    /*
     * Spawns the ball at the center of the window with a random initial velocity.
     */
    private void spawnBall() {
        Vector2 windowCenter = windowDimensions.mult(0.5f);;
        // set ball to spawn at the center of the window
        ball.setCenter(windowCenter);
        Random random = new Random();
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        // by 50% chance we invert the direction of each axis' velocity
        if (random.nextBoolean()){
            ballVelX *= -1;
        }
        if (random.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /*
     * Initializes the main paddle object in the game.
     */
    private void initializePaddle() {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE, true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        Vector2 paddle_center = new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - Constants.MIN_DIST - (Constants.PADDLE_HEIGHT / 2f));
        paddle.setCenter(paddle_center);
        paddle.setTag("Main Paddle");
        gameObjects().addGameObject(paddle);
    }

    /*
     * Initializes the bricks in the game using a loop based on the number of rows and columns.
     */
    private void initializeBricks() {

        Renderable brickImage = imageReader.readImage(Constants.BRICK_IMAGE, false);
        float brickWidth =
                (windowDimensions.x() - (2 * Constants.BORDER_SIZE) - (Constants.BRICKS_SPACE *
                        (bricksPerRow - 1))) / bricksPerRow;
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory(this,
                gameObjects(), ball, windowDimensions, soundReader, imageReader, inputListener,
                windowController, livesCounter);
        for (int row = 0; row < brickRows; row++) {
            for (int col = 0; col < bricksPerRow; col++) {
                Vector2 top_left_corner = new Vector2(Constants.BORDER_SIZE + col *
                        (brickWidth + Constants.BRICKS_SPACE),
                        Constants.BORDER_SIZE + row * (Constants.BRICK_HEIGHT + Constants.BRICKS_SPACE));
                Vector2 brick_dimension = new Vector2(brickWidth, Constants.BRICK_HEIGHT);

                Brick brick = new Brick(top_left_corner, brick_dimension, brickImage,
                        collisionStrategyFactory.buildStrategy(), bricksCounter);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /*
     * Initializes the graphic life counter at the bottom left of the screen.
     */
    private void initializeGraphicLifeCounter(){
        Renderable graphicLifeCounterImage = imageReader.readImage(Constants.HEART_IMAGE, true);
        // top left corner should be at the bottom left of the screen
        Vector2 topLeftCorner = new Vector2(Constants.BORDER_SIZE, windowDimensions.y() -
                Constants.MIN_DIST - Constants.HEART_SIZE);
        Vector2 dimensions = new Vector2(Constants.HEART_SIZE , Constants.HEART_SIZE);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(topLeftCorner, dimensions,
                graphicLifeCounterImage, Constants.INITIAL_NUMBER_OF_LIVES, livesCounter, gameObjects());
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
    }

    /*
     * Initializes the numeric life counter at the bottom left of the screen.
     */
    private void initializeNumericLifeCounter(){
        // top left corner should be at the bottom left of the screen, after the hearts
        Vector2 topLeftCorner = new Vector2(Constants.BORDER_SIZE + Constants.HEART_SIZE *
                Constants.MAX_NUM_OF_HEARTS,
                windowDimensions.y() - Constants.MIN_DIST);
        Vector2 dimensions = new Vector2(Constants.DIGIT_TEXT_SIZE, Constants.DIGIT_TEXT_SIZE);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(topLeftCorner, dimensions,
                livesCounter, gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /*
     * Initializes the game's background.
     */
    private void initializeBackground(){
        Renderable backgroundImage = imageReader.readImage(Constants.GAME_BACKGROUND, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /*
     * Initializes the game borders.
     */
    private void initializeBorders() {
        // left border
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(Constants.BORDER_SIZE, windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        // right border
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x() - Constants.BORDER_SIZE, 0),
                        new Vector2(Constants.BORDER_SIZE, windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        // upper border
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), Constants.BORDER_SIZE),
                        BORDER_RENDERABLE)
        );
    }

}

