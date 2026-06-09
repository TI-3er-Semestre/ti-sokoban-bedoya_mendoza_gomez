package Model;

import Model.structures.BinarySearchTree;
import Model.structures.Iterator;
import Model.structures.LinkedList;
import Model.structures.TreeNode;

/**
 * Tracks all game results for a player across sessions.
 * Uses a custom array structure (not ArrayList) as required by the project.
 */
public class PlayerStats {

    private transient  LinkedList<GameResult> results;
    private transient  BinarySearchTree<Achievement> achievements;
    private java.util.ArrayList<GameResult> resultsForSave = new java.util.ArrayList<>();



    public PlayerStats() {
        this.results = new LinkedList<>();
        this.achievements = new BinarySearchTree<>();
        this.resultsForSave = new java.util.ArrayList<>();
    }

    /**
     * Adds a new game result to the player's history.
     * Must grow the array if capacity is exceeded (no ArrayList allowed).
     * @param r result to add
     */
    public void addResult(GameResult r) {
        if (r != null) {
            results.add(r);
            resultsForSave.add(r);
        }
    }

    /**
     * Returns the best result (fewest moves) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByMoves(int levelId) {

        GameResult best = null;

        Iterator<GameResult> it = results.iterator();

        while (it.hasNext()) {
            GameResult r = it.next();

            if (r.getLevelId() == levelId) {
                if (best == null || r.getMoves() < best.getMoves()) {
                    best = r;
                }
            }
        }

        return best;
    }

    public GameResult binarySearchByMoves(int moves) {

        GameResult[] arr = getSortedResults();

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid = (left + right) / 2;

            if (arr[mid].getMoves() == moves) {
                return arr[mid];
            }

            if (arr[mid].getMoves() < moves) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Returns the best result (shortest time) for a given level.
     * @param levelId target level
     * @return best GameResult or null if no results for that level
     */
    public GameResult getBestByTime(int levelId) {
        GameResult best = null;

        Iterator<GameResult> it = results.iterator();

        while (it.hasNext()) {
            GameResult r = it.next();

            if (r.getLevelId() == levelId) {
                if (best == null || r.getTimeMillis() < best.getTimeMillis()) {
                    best = r;
                }
            }
        }

        return best;
    }

    public GameResult binarySearchByTime(long time) {

        GameResult[] arr = getSortedResults();

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid = (left + right) / 2;

            if (arr[mid].getTimeMillis() == time) {
                return arr[mid];
            }

            if (arr[mid].getTimeMillis() < time) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Returns all results sorted by compareTo (moves, then time).
     * @return sorted array of GameResult
     */
    public GameResult[] getSortedResults() {
        int size = results.getLength();
        GameResult[] copy = new GameResult[size];

        Iterator<GameResult> it = results.iterator();

        int i = 0;
        while (it.hasNext()) {
            copy[i++] = it.next();
        }

        for (int a = 0; a < size - 1; a++) {
            for (int b = 0; b < size - a - 1; b++) {
                if (copy[b].compareTo(copy[b + 1]) > 0) {
                    GameResult temp = copy[b];
                    copy[b] = copy[b + 1];
                    copy[b + 1] = temp;
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

        Iterator<GameResult> it = results.iterator();

        while (it.hasNext()) {
            if (it.next().getLevelId() == levelId) {
                count++;
            }
        }

        GameResult[] filtered = new GameResult[count];

        it = results.iterator();
        int index = 0;

        while (it.hasNext()) {
            GameResult r = it.next();
            if (r.getLevelId() == levelId) {
                filtered[index++] = r;
            }
        }

        return filtered;
    }

    public int getTotalGames() {
        return results.getLength();
    }

    public void addAchievement(Achievement a) {
        achievements.add(a);
    }

    public Achievement findAchievement(Achievement a) {
        TreeNode<Achievement> node = achievements.search(a);

        if (node != null) {
            return node.getData();
        }
        return null;
    }

    public void removeAchievement(Achievement a) {
        achievements.delete(a);
    }

    public void rehydrate() {
        this.results = new LinkedList<>();
        if (resultsForSave != null) {
            for (GameResult r : resultsForSave) {
                results.add(r);
            }
        }
        if (this.achievements == null) {
            this.achievements = new BinarySearchTree<>();
        }
    }

}
