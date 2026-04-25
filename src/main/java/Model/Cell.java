package Model;

import java.io.Serializable;

/**
 * Abstract base class for all cell types on the board.
 * Each cell knows whether it is a goal position, whether it can be walked on,
 * and how to represent itself as a character symbol.
 */
public abstract class Cell implements Serializable {

    private boolean isGoal;

    public Cell(boolean isGoal) {
        this.isGoal = isGoal;
    }

    /**
     * Returns true if this cell is a goal position.
     */
    public boolean isGoal() {
        return isGoal;
    }

    /**
     * Returns true if the player or a box can occupy this cell.
     */
    public abstract boolean isWalkable();

    /**
     * Returns the character symbol that represents this cell.
     * Convention: ' ' empty, '#' wall, '$' box, '.' goal,
     *             '*' box-on-goal, '@' player, '+' player-on-goal
     */
    public abstract char getSymbol();
}
