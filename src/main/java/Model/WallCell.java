package Model;

/** Represents an impassable wall cell. */
public class WallCell extends Cell {

    public WallCell() {
        super(false);
    }

    @Override
    public boolean isWalkable() {
        // TODO: implement — always false
        return false;
    }

    @Override
    public char getSymbol() {
        // TODO: implement — return '#'
        return '#';
    }
}
