/**
 * Represents the four possible movement directions in the game.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Returns the row delta for this direction.
     * @return row offset (-1, 1, or 0)
     */
    public int getDRow() {
        // TODO: implement
        return 0;
    }

    /**
     * Returns the column delta for this direction.
     * @return column offset (-1, 1, or 0)
     */
    public int getDCol() {
        // TODO: implement
        return 0;
    }

    /**
     * Maps a keyboard key string to a Direction.
     * @param k key string (e.g. "UP", "W")
     * @return corresponding Direction, or null if not mapped
     */
    public static Direction fromKey(String k) {
        // TODO: implement
        return null;
    }

    /**
     * Returns the opposite direction.
     * @return opposite Direction
     */
    public Direction opposite() {
        // TODO: implement
        return null;
    }
}
