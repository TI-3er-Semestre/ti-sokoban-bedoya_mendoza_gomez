/** Represents a cell where a box is sitting on a goal position. */
public class BoxOnGoalCell extends Cell {

    public BoxOnGoalCell() {
        super(true);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — false, still blocked by the box
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '*'
        return 0;
    }
}
