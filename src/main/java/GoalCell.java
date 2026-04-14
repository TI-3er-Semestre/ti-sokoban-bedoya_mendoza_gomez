/** Represents a goal position (target) with no box on it. */
public class GoalCell extends Cell {

    public GoalCell() {
        super(true);
    }

    @Override
    public boolean isWalkable() {

        return true;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '.'
        return '.';
    }
}
