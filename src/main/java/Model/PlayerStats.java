package Model;

/**
 * Tracks all game results for a player across sessions.
 * Uses a custom array structure (not ArrayList) as required by the project.
 */
public class PlayerStats {

    private GameResult[] results;
    private int totalGames;

    public PlayerStats() {

        this.results = new GameResult[10]; // capacidad inicial
        this.totalGames = 0;
    }

    /**
     * Adds a new game result to the player's history.
     * Must grow the array if capacity is exceeded (no ArrayList allowed).
     * @param r result to add
     */
    public void addResult(GameResult r) {

        if (totalGames == results.length) {
        GameResult[] newArray = new GameResult[results.length * 2];

        for (int i = 0; i < results.length; i++) {
            newArray[i] = results[i];
        }

        results = newArray;
    }

        results[totalGames] = r;
        totalGames++;

    }

    /**
     * Returns the best result (fewest moves) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByMoves(int levelId) {

        GameResult best = null;

        for (int i = 0; i < totalGames; i++) {
            GameResult r = results[i];

            if (r.getLevelId() == levelId) {
                if (best == null || r.getMoves() < best.getMoves()) {
                    best = r;
                }
            }
        }

        return best;

    }

    /**
     * Returns the best result (shortest time) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByTime(int levelId) {
        GameResult best = null;

        for (int i = 0; i < totalGames; i++) {
            GameResult r = results[i];

            if (r.getLevelId() == levelId) {
                if (best == null || r.getTimeMillis() < best.getTimeMillis()) {
                    best = r;
                }
            }
        }

        return best;
    }

    /**
     * Returns all results sorted by compareTo (moves, then time).
     * @return sorted array of GameResult
     */
    public GameResult[] getSortedResults() {

        GameResult[] copy = new GameResult[totalGames];

        for (int i = 0; i < totalGames; i++) {
            copy[i] = results[i];
        }

        for (int i = 0; i < totalGames - 1; i++) {
            for (int j = 0; j < totalGames - i - 1; j++) {

                if (copy[j].compareTo(copy[j + 1]) > 0) {
                    GameResult temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }
        }

        return copy;
    }

    /**
     * Returns all results for a specific level.
     * @param levelId target level
     * @return array of matching GameResult
     */
    public GameResult[] getResultsByLevel(int levelId) {

        int count = 0;

        for (int i = 0; i < totalGames; i++) {
            if (results[i].getLevelId() == levelId) {
                count++;
            }
        }

        GameResult[] filtered = new GameResult[count];
        int index = 0;

        for (int i = 0; i < totalGames; i++) {
            if (results[i].getLevelId() == levelId) {
                filtered[index++] = results[i];
            }
        }

        return filtered;
    }

    public int getTotalGames() {
        return totalGames;
    }
}
