/** Represents a cell where the player is standing on a goal position. */
public class PlayerOnGoalCell extends Cell {

    public PlayerOnGoalCell() {
        super(true);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — false (occupied by player)
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '+'
        return '+';
    }
}
