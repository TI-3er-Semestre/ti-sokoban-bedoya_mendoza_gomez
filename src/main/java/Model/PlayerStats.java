package Model;

/**
 * Tracks all game results for a player across sessions.
 * Uses a custom array structure (not ArrayList) as required by the project.
 */
public class PlayerStats {

    private GameResult[] results;
    private int totalGames;

    public PlayerStats() {
        // TODO: implement — initialize results array with an initial capacity
    }

    /**
     * Adds a new game result to the player's history.
     * Must grow the array if capacity is exceeded (no ArrayList allowed).
     * @param r result to add
     */
    public void addResult(GameResult r) {
        // TODO: implement
    }

    /**
     * Returns the best result (fewest moves) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByMoves(int levelId) {
        // TODO: implement
        return null;
    }

    /**
     * Returns the best result (shortest time) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByTime(int levelId) {
        // TODO: implement
        return null;
    }

    /**
     * Returns all results sorted by compareTo (moves, then time).
     * @return sorted array of GameResult
     */
    public GameResult[] getSortedResults() {
        // TODO: implement — use a sorting algorithm (no Arrays.sort)
        return null;
    }

    /**
     * Returns all results for a specific level.
     * @param levelId target level
     * @return array of matching GameResult
     */
    public GameResult[] getResultsByLevel(int levelId) {
        // TODO: implement
        return null;
    }

    public int getTotalGames() {
        return totalGames;
    }
}
