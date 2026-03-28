/** Represents a cell occupied by the player (not on a goal). */
public class PlayerCell extends Cell {

    public PlayerCell() {
        super(false);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — false (occupied by player)
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '@'
        return '@';
    }
}
