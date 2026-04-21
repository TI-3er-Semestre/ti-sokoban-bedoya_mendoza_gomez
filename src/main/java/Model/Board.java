package Model;

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
        if (goalPositions == null || goalPositions.length == 0) {
            return false;
        }

        for (Position pos : goalPositions) {
            Cell cell = getCell(pos.getRow(), pos.getCol());

            if (!(cell instanceof BoxOnGoalCell)) {
                return false;
            }
        }

        return true;

    }

    /**
     * Returns a deep copy of this board.
     * Required for saving states (undo, transposition table).
     * @return cloned Model.Board
     */
    public Board clone() {
        Board copy = new Board(this.rows, this.cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                Cell cell = this.grid[i][j];

                if (cell instanceof WallCell) {
                    copy.setCell(i, j, new WallCell());
                } else if (cell instanceof BoxCell) {
                    copy.setCell(i, j, new BoxCell());
                } else if (cell instanceof BoxOnGoalCell) {
                    copy.setCell(i, j, new BoxOnGoalCell());
                } else if (cell instanceof GoalCell) {
                    copy.setCell(i, j, new GoalCell());
                } else if (cell instanceof PlayerCell) {
                    copy.setCell(i, j, new PlayerCell());
                } else if (cell instanceof PlayerOnGoalCell) {
                    copy.setCell(i, j, new PlayerOnGoalCell());
                } else {
                    copy.setCell(i, j, new EmptyCell());
                }
            }
        }

        //copiar goals
        if (this.goalPositions != null) {
            Position[] goalsCopy = new Position[this.goalPositions.length];
            for (int i = 0; i < this.goalPositions.length; i++) {
                Position p = this.goalPositions[i];
                goalsCopy[i] = new Position(p.getRow(), p.getCol());
            }
            copy.setGoalPositions(goalsCopy);
        }

        return copy;
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