/**
 * Represents the 2D grid board of a Sokoban level.
 * Manages cell access, validity checks, and victory detection.
 */
public class Board {

    private Cell[][] grid;
    private int rows;
    private int cols;
    private Position[] goalPositions;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        grid = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new EmptyCell();
            }
        }
    }

    /**
     * Returns the cell at the given position.
     * @param r row index
     * @param c column index
     * @return cell at (r, c)
     */
    public Cell getCell(int r, int c) {
        if (this.isValidPosition(r, c)) {
            return this.grid[r][c];
        } else {
            return new WallCell();
        }
    }

    /**
     * Sets the cell at the given position.
     * @param r row index
     * @param c column index
     * @param cell new cell to place
     */
    public void setCell(int r, int c, Cell cell) {
        if (isValidPosition(r, c)) {
            grid[r][c] = cell;
        }
    }

    /**
     * Returns true if (r, c) is within the board boundaries.
     * @param r row index
     * @param c column index
     * @return true if position is valid
     */
    public boolean isValidPosition(int r, int c) {
        if (r < 0 || r >= this.rows) {
            return false;
        }
        if (c < 0 || c >= this.cols) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if all goal positions are covered by a box.
     * Used to determine if the level is complete.
     * @return true if level is completed
     */
    public boolean isCompleted() {
        // TODO: implement — check each goalPosition for BoxOnGoalCell
        return false;
    }

    /**
     * Returns a deep copy of this board.
     * Required for saving states (undo, transposition table).
     * @return cloned Board
     */
    public Board clone() {
        // TODO: implement
        return null;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Position[] getGoalPositions() {
        return goalPositions;
    }

    public void setGoalPositions(Position[] goalPositions) {
        this.goalPositions = goalPositions;
    }
}