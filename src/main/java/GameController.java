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
        if (currentState == null || !isValidMove(direction)) return false;

        if (currentState.getBoard() == null) return false;

        Position oldPos = currentState.getPlayerPosition();
        Position nextPos = getNextPosition(oldPos, direction);
        Board board = currentState.getBoard();

        Cell target = board.getCell(nextPos.getRow(), nextPos.getCol());

        if (target instanceof BoxCell || target instanceof BoxOnGoalCell) {
            Position boxDest = getNextPosition(nextPos, direction);
            board.moveBox(nextPos, boxDest);
            this.pushCount++;
            currentState.setPushCount(this.pushCount);
        }

        Cell oldCell = board.getCell(oldPos.getRow(), oldPos.getCol());
        board.setCell(oldPos.getRow(), oldPos.getCol(),
                (oldCell instanceof PlayerOnGoalCell) ? new GoalCell() : new EmptyCell());

        Cell destCell = board.getCell(nextPos.getRow(), nextPos.getCol());
        board.setCell(nextPos.getRow(), nextPos.getCol(),
                (destCell instanceof GoalCell) ? new PlayerOnGoalCell() : new PlayerCell());

        currentState.setPlayerPosition(nextPos);
        this.moveCount++;
        currentState.setMoveCount(this.moveCount);

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

        Position currentPos = currentState.getPlayerPosition();
        Position nextPos = getNextPosition(currentPos, direction);
        Board board = currentState.getBoard();

        if (!board.isValidPosition(nextPos.getRow(), nextPos.getCol())) return false;

        Cell target = board.getCell(nextPos.getRow(), nextPos.getCol());

        if (target instanceof WallCell) return false;

        if (target instanceof BoxCell || target instanceof BoxOnGoalCell) {
            Position boxDest = getNextPosition(nextPos, direction);
            if (!board.isValidPosition(boxDest.getRow(), boxDest.getCol())) return false;

            Cell cellBehind = board.getCell(boxDest.getRow(), boxDest.getCol());
            return (cellBehind instanceof EmptyCell || cellBehind instanceof GoalCell);
        }

        return true;
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
        if (current == null || direction == null) return null;

        return new Position(
                current.getRow() + direction.getDRow(),
                current.getCol() + direction.getDCol()
        );
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
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Returns elapsed time in milliseconds since the timer started.
     * @return elapsed milliseconds
     */
    public long getElapsedTime() {

        if (startTime == 0) return 0;
        return System.currentTimeMillis() - startTime;

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
        if (state != null) {
            this.moveCount = state.getMoveCount();
            this.pushCount = state.getPushCount();
        }
    }
}