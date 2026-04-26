
package Model;

/**
 * Represents an achievement unlocked by the player.
 */
public class Achievement implements Comparable<Achievement> {

    private String name;
    private int score;

    public Achievement(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Achievement other) {
        return this.score - other.score; // orden por puntaje
    }

    @Override
    public String toString() {
        return name + " (" + score + ")";
    }
}
