package com.sokoban.model;

/**
 * Maintains the top-K leaderboard for a specific level.
 * Uses a priority queue structure (implemented manually, no PriorityQueue from Java).
 */
public class Leaderboard {

    private int levelId;
    private int topK;
    private GameResult[] entries;

    public Leaderboard(int levelId, int topK) {
        // TODO: implement — initialize entries array with capacity topK
    }

    /**
     * Adds a new entry if it qualifies for the top-K.
     * If the leaderboard is full and the new result is better than the worst entry,
     * the worst entry is replaced.
     * @param r new GameResult to evaluate
     */
    public void addEntry(GameResult r) {
        // TODO: implement
    }

    /**
     * Returns the current top-K entries sorted best to worst.
     * @return sorted array of top entries
     */
    public GameResult[] getTopK() {
        // TODO: implement
        return null;
    }

    /**
     * Returns true if the result qualifies for the top-K.
     * @param r result to evaluate
     * @return true if it should be included
     */
    public boolean isTopK(GameResult r) {
        // TODO: implement
        return false;
    }

    public int getTopK_size() {
        return topK;
    }

    public int getCurrentSize() {
        // TODO: implement — return actual number of entries stored
        return 0;
    }
}
