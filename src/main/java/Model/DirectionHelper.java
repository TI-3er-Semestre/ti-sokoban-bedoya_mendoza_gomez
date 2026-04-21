package Model;

/**
 * Utility class providing helper methods for Direction calculations.
 * Avoids polluting the Direction enum with too many responsibilities.
 */
public class DirectionHelper {

    private DirectionHelper() {
        // Utility class — no instantiation
    }

    /**
     * Returns the row delta for a given direction.
     * UP = -1, DOWN = +1, LEFT/RIGHT = 0.
     * @param d direction
     * @return row offset
     */
    public static int getDRow(Direction d) {
        // TODO: implement
        return 0;
    }

    /**
     * Returns the column delta for a given direction.
     * LEFT = -1, RIGHT = +1, UP/DOWN = 0.
     * @param d direction
     * @return column offset
     */
    public static int getDCol(Direction d) {
        // TODO: implement
        return 0;
    }

    /**
     * Maps a keyboard key string to a Direction.
     * Accepts arrow key names ("UP", "DOWN", "LEFT", "RIGHT")
     * and WASD equivalents ("W", "A", "S", "D").
     * @param key key string from keyboard event
     * @return corresponding Direction, or null if unmapped
     */
    public static Direction fromKey(String key) {
        // TODO: implement
        return null;
    }

    /**
     * Returns the opposite of a given direction.
     * @param d direction
     * @return opposite Direction
     */
    public static Direction opposite(Direction d) {
        // TODO: implement
        return null;
    }

    /**
     * Returns a [dRow, dCol] vector for the given direction.
     * @param d direction
     * @return int array {dRow, dCol}
     */
    public static int[] toVector(Direction d) {
        // TODO: implement
        return new int[]{0, 0};
    }
}
