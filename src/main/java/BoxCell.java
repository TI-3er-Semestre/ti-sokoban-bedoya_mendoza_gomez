/** Represents a cell occupied by a box (not on a goal). */
public class BoxCell extends Cell {

    public BoxCell() {
        super(false);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — false, boxes block movement unless pushed
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '$'
        return '$';
    }
}
