package Model;

import Model.structures.Graph.AdjacencyListGraph;

import java.io.Serializable;

/**<
 * Represents the 2D grid board of a Sokoban level.
 * Manages cell access, validity checks, and victory detection.
 */
public class Board implements Serializable {

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
        if (goalPositions == null || goalPositions.length == 0) return false;

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

        if (this.goalPositions != null) {
            Position[] goalsCopy = new Position[this.goalPositions.length];
            for (int i = 0; i < this.goalPositions.length; i++) {
                Position p = this.goalPositions[i];
                // Creamos un nuevo objeto Position para que sea una copia real
                goalsCopy[i] = new Position(p.getRow(), p.getCol());
            }
            copy.setGoalPositions(goalsCopy);
        }

        return copy;
    }

    /**
     * Actualiza las celdas en el tablero para reflejar el movimiento de una caja.
     * @param from posición actual de la caja
     * @param to posición destino de la caja
     */
    public void moveBox(Position from, Position to) {
        Cell boxCell = getCell(from.getRow(), from.getCol());
        Cell destCell = getCell(to.getRow(), to.getCol());

        if (destCell.isGoal()) {
            setCell(to.getRow(), to.getCol(), new BoxOnGoalCell());
        } else {
            setCell(to.getRow(), to.getCol(), new BoxCell());
        }

        if (boxCell instanceof BoxOnGoalCell) {
            setCell(from.getRow(), from.getCol(), new GoalCell());
        } else {
            setCell(from.getRow(), from.getCol(), new EmptyCell());
        }
    }

    /**
     * Returns all positions where boxes exist on the board.
     * @return array of box positions
     */
    public Position[] getBoxPositions() {
        int count = 0;

        // contar cajas
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = grid[i][j];
                if (c instanceof BoxCell || c instanceof BoxOnGoalCell) {
                    count++;
                }
            }
        }

        Position[] result = new Position[count];
        int index = 0;

        // guardar posiciones
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = grid[i][j];
                if (c instanceof BoxCell || c instanceof BoxOnGoalCell) {
                    result[index++] = new Position(i, j);
                }
            }
        }

        return result;
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

    /**
     * Converts the current 2D board grid into an Adjacency List Graph.
     * Vertices are walkable positions, and Edges connect adjacent valid cells.
     * @return AdjacencyListGraph of Position
     */
    public AdjacencyListGraph<Position> toGraph() {
        AdjacencyListGraph<Position> graph = new AdjacencyListGraph<>();

        // 1. Add all non-wall cells as vertices
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (!(grid[i][j] instanceof WallCell)) {
                    graph.addVertex(new Position(i, j));
                }
            }
        }

        // 2. Connect adjacent vertices with edges (Weight = 1)
        int[] dRow = {-1, 1, 0, 0}; // UP, DOWN, LEFT, RIGHT
        int[] dCol = {0, 0, -1, 1};

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (!(grid[i][j] instanceof WallCell)) {
                    Position currentPos = new Position(i, j);

                    // Check all 4 possible directions
                    for (int d = 0; d < 4; d++) {
                        int nextRow = i + dRow[d];
                        int nextCol = j + dCol[d];

                        // If neighbor is within bounds and is not a wall, add edge
                        if (isValidPosition(nextRow, nextCol) && !(grid[nextRow][nextCol] instanceof WallCell)) {
                            Position neighborPos = new Position(nextRow, nextCol);
                            graph.addEdge(currentPos, neighborPos, 1); // Costo normal de 1
                        }
                    }
                }
            }
        }
        return graph;
    }
}