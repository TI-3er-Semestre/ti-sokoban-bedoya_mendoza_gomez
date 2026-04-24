package Model;

/**
 * Maintains the top-K leaderboard for a specific level.
 * Uses a priority queue structure (implemented manually, no PriorityQueue from Java).
 */
public class Leaderboard {

    private int levelId;
    private int topK;
    private GameResult[] entries;
    private int size;

    public Leaderboard(int levelId, int topK) {
        this.levelId = levelId;
        this.topK = topK;
        this.entries = new GameResult[topK];
        this.size = 0;    }

    /**
     * Adds a new entry if it qualifies for the top-K.
     * If the leaderboard is full and the new result is better than the worst entry,
     * the worst entry is replaced.
     * @param r new GameResult to evaluate
     */
    public void addEntry(GameResult r) {

        if (r == null) return;

        // si hay espacio
        if (size < topK) {
            entries[size++] = r;
        } else {

            // buscar el peor
            int worstIndex = 0;

            for (int i = 1; i < size; i++) {
                if (entries[i].compareTo(entries[worstIndex]) > 0) {
                    worstIndex = i;
                }
            }

            // si el nuevo es mejor lo reemplaza
            if (r.compareTo(entries[worstIndex]) < 0) {
                entries[worstIndex] = r;
            }
        }
    }

    /**
     * Returns the current top-K entries sorted best to worst.
     * @return sorted array of top entries
     */
    public GameResult[] getTopK() {

        GameResult[] copy = new GameResult[size];

        for (int i = 0; i < size; i++) {
            copy[i] = entries[i];
        }

        // ordenar (burbuja simple)
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {

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
     * Returns true if the result qualifies for the top-K.
     * @param r result to evaluate
     * @return true if it should be included
     */
    public boolean isTopK(GameResult r) {

        if (size < topK) return true;

        for (int i = 0; i < size; i++) {
            if (r.compareTo(entries[i]) < 0) {
                return true;
            }
        }

        return false;
    }

    public int getTopK_size() {
        return topK;
    }

    public int getCurrentSize() {

        return size;
    }
}
