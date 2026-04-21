package Model;

/** Represents a cell where the player is standing on a goal position. */
public class PlayerOnGoalCell extends Cell {

    public PlayerOnGoalCell() {
        super(true);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public char getSymbol() {
        return '+';
    }
}
 