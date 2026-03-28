/**
 * Abstract base class for all cell types on the board.
 * Each cell knows whether it is a goal position, whether it can be walked on,
 * and how to represent itself as a character symbol.
 */
public abstract class Cell {

    private boolean isGoal;

    public Cell(boolean isGoal) {
        // TODO: implement
    }

    /**
     * Returns true if this cell is a goal position.
     */
    public boolean isGoal() {
        // TODO: implement
        return false;
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
