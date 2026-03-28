/** Represents an empty, walkable cell with no special content. */
public class EmptyCell extends Cell {

    public EmptyCell() {
        super(false);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — always true
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return ' '
        return ' ';
    }
}
