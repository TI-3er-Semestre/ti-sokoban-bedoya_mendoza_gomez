package Model;

/** Represents an impassable wall cell. */
public class WallCell extends Cell {

    public WallCell() {
        super(false);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}
 