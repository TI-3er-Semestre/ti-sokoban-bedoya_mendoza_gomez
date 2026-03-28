/** Represents a goal position (target) with no box on it. */
public class GoalCell extends Cell {

    public GoalCell() {
        super(true);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — true, player can walk onto a goal
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '.'
        return '.';
    }
}
