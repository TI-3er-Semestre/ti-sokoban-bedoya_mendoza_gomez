package Model;

import java.io.Serializable;

/**
 * Represents a (row, col) position on the board grid.
 * Commit 1 (3 abril): implementación completa — base para todos los tests.
 */
public class Position implements Serializable, Comparable<Position> {

    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public int compareTo(Position other) {
        if (this.row != other.row) {
            return Integer.compare(this.row, other.row);
        }
        return Integer.compare(this.col, other.col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
