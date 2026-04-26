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
        if (d == null) return 0;

        switch (d) {
            case UP: return -1;
            case DOWN: return 1;
            default: return 0;
        }
    }

    /**
     * Returns the column delta for a given direction.
     * LEFT = -1, RIGHT = +1, UP/DOWN = 0.
     * @param d direction
     * @return column offset
     */
    public static int getDCol(Direction d) {
        if (d == null) return 0;

        switch (d) {
            case LEFT: return -1;
            case RIGHT: return 1;
            default: return 0;
        }
    }

    /**
     * Maps a keyboard key string to a Direction.
     * Accepts arrow key names ("UP", "DOWN", "LEFT", "RIGHT")
     * and WASD equivalents ("W", "A", "S", "D").
     * @param key key string from keyboard event
     * @return corresponding Direction, or null if unmapped
     */
    public static Direction fromKey(String key) {
        if (key == null) return null;

        key = key.toUpperCase();

        switch (key) {
            case "W":
            case "UP":
                return Direction.UP;

            case "S":
            case "DOWN":
                return Direction.DOWN;

            case "A":
            case "LEFT":
                return Direction.LEFT;

            case "D":
            case "RIGHT":
                return Direction.RIGHT;
        }

        return null;
    }

    /**
     * Returns the opposite of a given direction.
     * @param d direction
     * @return opposite Direction
     */
    public static Direction opposite(Direction d) {
        if (d == null) return null;

        switch (d) {
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
        }

        return null;
    }

    /**
     * Returns a [dRow, dCol] vector for the given direction.
     * @param d direction
     * @return int array {dRow, dCol}
     */
    public static int[] toVector(Direction d) {
        return new int[]{ getDRow(d), getDCol(d) };
    }
}
