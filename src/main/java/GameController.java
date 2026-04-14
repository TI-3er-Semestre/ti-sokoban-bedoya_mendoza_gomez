/**
 * Central controller of the Sokoban game (MVC - Controller layer).
 * Manages game flow, player input, state transitions, and persistence.
 * All game logic lives here — the view only calls methods on this class.
 */
public class GameController {

    private GameState currentState;
    private Level currentLevel;
    private Player currentPlayer;

    private int moveCount;
    private int pushCount;
    private long startTime;
    private boolean gameOver;

    private LevelRepository levelRepo;
    private GameStateRepository stateRepo;
    private PlayerRepository playerRepo;

    public GameController(String levelsPath, String savePath, String playersPath) {
        // TODO: implement — initialize repositories
    }

    /**
     * Attempts to move the player in the given direction.
     * Validates the move, updates the board, increments counters,
     * and checks for victory.
     * @param direction direction to move
     * @return true if the move was executed, false if blocked
     */
    public boolean move(Direction direction) {
        if (currentState == null || currentState.getPlayerPosition() == null) {
            return false;
        }

        if (!isValidMove(direction)) return false;

        Position currentPos = currentState.getPlayerPosition();
        Position nextPos = getNextPosition(currentPos, direction);
        Cell targetCell = currentState.getBoard().getCell(nextPos.getRow(), nextPos.getCol());

        if (targetCell instanceof BoxCell || targetCell instanceof BoxOnGoalCell) {
            Position boxDest = getNextPosition(nextPos, direction);

            this.pushCount++;
        }

        currentState.setPlayerPosition(nextPos);
        this.moveCount++;

        return true;
    }

    /**
     * Validates whether the player can move in the given direction.
     * Checks for walls, box push validity, and board boundaries.
     * @param direction direction to validate
     * @return true if the move is allowed
     */
    private boolean isValidMove(Direction direction) {
        if (currentState == null) return false;

        Position nextPos = getNextPosition(currentState.getPlayerPosition(), direction);

        if (!currentState.getBoard().isValidPosition(nextPos.getRow(), nextPos.getCol())) {
            return false;
        }

        Cell targetCell = currentState.getBoard().getCell(nextPos.getRow(), nextPos.getCol());

        if (targetCell instanceof WallCell) {
            return false;
        }

        if (targetCell instanceof BoxCell || targetCell instanceof BoxOnGoalCell) {
            return isPushingBox(direction);
        }

        return targetCell.isWalkable();
    }

    /**
     * Returns true if moving in the given direction would push a box.
     * @param direction direction to check
     * @return true if a box is in the target cell
     */
    private boolean isPushingBox(Direction direction) {
        Position currentPos = currentState.getPlayerPosition();
        Position nextPos = getNextPosition(currentPos, direction);

        Cell targetCell = currentState.getBoard().getCell(nextPos.getRow(), nextPos.getCol());

        if (targetCell instanceof BoxCell || targetCell instanceof BoxOnGoalCell) {
            Position afterNextPos = getNextPosition(nextPos, direction);

            if (currentState.getBoard().isValidPosition(afterNextPos.getRow(), afterNextPos.getCol())) {
                Cell cellBehindBox = currentState.getBoard().getCell(afterNextPos.getRow(), afterNextPos.getCol());

                return cellBehindBox.isWalkable() &&
                        !(cellBehindBox instanceof BoxCell) &&
                        !(cellBehindBox instanceof BoxOnGoalCell);
            }
        }
        return false;
    }

    /**
     * Calculates the next position based on a starting point and a direction.
     * @param current Current position.
     * @param direction Direction to move.
     * @return The new calculated position.
     */
    private Position getNextPosition(Position current, Direction direction) {
        if (current == null) {
            return null;
        }
        int row = current.getRow();
        int col = current.getCol();

        switch (direction) {
            case UP:    row--; break;
            case DOWN:  row++; break;
            case LEFT:  col--; break;
            case RIGHT: col++; break;
        }
        return new Position(row, col);
    }

    /**
     * Checks whether the current state satisfies the victory condition.
     * @return true if all boxes are on goal positions
     */
    public boolean checkVictory() {
        return currentState != null
                && currentState.getBoard() != null
                && currentState.getBoard().isCompleted();
    }

    /**
     * Loads a level by id and initializes a fresh game state.
     * @param id level identifier
     */
    public void loadLevel(int id) {
        // TODO: implement
    }

    /**
     * Resets the current level to its initial state.
     */
    public void resetLevel() {
        // TODO: implement
    }

    /**
     * Persists the current game state to disk.
     */
    public void saveGame() {
        // TODO: implement
    }

    /**
     * Loads a previously saved game state from disk.
     */
    public void loadGame() {
        // TODO: implement
    }

    /**
     * Registers a new player in the system.
     * @param player player to register
     */
    public void registerPlayer(Player player) {
        // TODO: implement — validate username uniqueness before saving
    }

    /**
     * Loads an existing player profile by username.
     * @param username player's username
     */
    public void loadPlayer(String username) {
        // TODO: implement
    }

    /**
     * Saves the current player's updated statistics.
     */
    public void savePlayerStats() {
        // TODO: implement
    }

    /**
     * Records the start time of the current game session.
     */
    public void startTimer() {
        // TODO: implement
    }

    /**
     * Returns elapsed time in milliseconds since the timer started.
     * @return elapsed milliseconds
     */
    public long getElapsedTime() {
        // TODO: implement
        return 0;
    }

    /**
     * Returns the leaderboard for a specific level.
     * @param levelId level identifier
     * @return Leaderboard with top results
     */
    public Leaderboard getLeaderboard(int levelId) {
        // TODO: implement
        return null;
    }

    /**
     * Returns true if the game is in a game-over state.
     * @return true if game over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getPushCount() {
        return pushCount;
    }

    void setStateForTesting(GameState state) {
        this.currentState = state;
    }
}