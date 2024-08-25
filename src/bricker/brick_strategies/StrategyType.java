package bricker.brick_strategies;

/**
 * StrategyType is an enumeration of different types of collision strategies that can be created by the
 * CollisionStrategyFactory. It includes strategies for extra pucks, extra paddle, camera change, dropping
 * heart, double behavior, and basic behavior.
 * @author Idan Hippach, Noam Barzilay
 */
public enum StrategyType {
    /** Extra Pucks Strategy. */
    EXTRA_PUCKS,
    /** Extra Paddle Strategy. */
    EXTRA_PADDLE,
    /** Camera Change Strategy. */
    CAMERA_CHANGE,
    /** Dropping Heart Strategy. */
    DROPPING_HEART,
    /** Double Behavior Strategy. */
    DOUBLE_BEHAVIOR, // Assuming this is meant to be a placeholder for future implementation
    /** Basic Behavior Strategy. */
    BASIC_BEHAVIOR
}
