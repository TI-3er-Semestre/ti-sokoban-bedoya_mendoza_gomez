package controller;

import Model.*;
import Model.structures.Queue;
import Model.structures.Stack;
import Model.structures.StateKey;
import Model.structures.TranspositionTable;

/**
 * Central controller of the Sokoban game (MVC - Controller layer).
 * Manages game flow, player input, state transitions, and persistence.
 * All game logic lives here — the view only calls methods on this class.
 */
public class GameController {

    private GameState currentState;
    private Level currentLevel;
    private Player currentPlayer;
    private TranspositionTable visitedStates = new TranspositionTable(1000);
    private Stack<GameState> undoStack;
    private Queue<String> moveQueue;

    private int moveCount;
    private int pushCount;
    private long startTime;
    private boolean gameOver;

    private LevelRepository levelRepo;
    private GameStateRepository stateRepo;
    private PlayerRepository playerRepo;
    private boolean resumingGame = false;

    public GameController(String levelsPath, String savePath, String playersPath) {
    this.moveCount = 0;
    this.pushCount = 0;
    this.gameOver = false;
    this.startTime = 0;
    this.undoStack = new Stack<>();
    this.moveQueue = new Queue<>();
    if (levelsPath != null) {
        this.levelRepo = new LevelRepository(levelsPath);
    }
    if (savePath != null) {
        this.stateRepo = new GameStateRepository(savePath);
    }
    if (playersPath != null) {
        this.playerRepo = new PlayerRepository(playersPath);
    }
    }

    public boolean move(Direction direction) {

        if (currentState == null || currentState.getPlayerPosition() == null) {
            return false;
        }

        if (this.currentPlayer != null) {
            this.currentPlayer.setCurrentDirection(direction);
        }

        Board board = currentState.getBoard();
        Position currentPos = currentState.getPlayerPosition();

        if (!isValidMove(direction)) return false;

        // Save current state before modifying it for the undo stack
        undoStack.push(cloneState(currentState));

        Position nextPos = getNextPosition(currentPos, direction);
        Cell targetCell = board.getCell(nextPos.getRow(), nextPos.getCol());

        // PUSH BOX LOGIC
        if (targetCell instanceof BoxCell || targetCell instanceof BoxOnGoalCell) {

            Position boxDest = getNextPosition(nextPos, direction);
            Cell destCell = board.getCell(boxDest.getRow(), boxDest.getCol());

            board.setCell(
                    boxDest.getRow(),
                    boxDest.getCol(),
                    (destCell instanceof GoalCell) ? new BoxOnGoalCell() : new BoxCell()
            );

            board.setCell(
                    nextPos.getRow(),
                    nextPos.getCol(),
                    (targetCell instanceof BoxOnGoalCell) ? new GoalCell() : new EmptyCell()
            );

            pushCount++;
        }

        // CLEAR PREVIOUS PLAYER POSITION
        Cell currentCell = board.getCell(currentPos.getRow(), currentPos.getCol());

        board.setCell(
                currentPos.getRow(),
                currentPos.getCol(),
                (currentCell instanceof PlayerOnGoalCell) ? new GoalCell() : new EmptyCell()
        );

        // PLACE PLAYER IN NEW POSITION
        Cell destCell = board.getCell(nextPos.getRow(), nextPos.getCol());

        board.setCell(
                nextPos.getRow(),
                nextPos.getCol(),
                (destCell instanceof GoalCell) ? new PlayerOnGoalCell() : new PlayerCell()
        );

        currentState.setPlayerPosition(nextPos);
        moveCount++;

        currentState.setMoveCount(this.moveCount);
        currentState.setPushCount(this.pushCount);

        return true;
    }

    private GameState cloneState(GameState state) {

        if (state == null) return null;

        Board originalBoard = state.getBoard();
        Board newBoard = originalBoard.clone(); // debes tener clone en Board

        Position pos = state.getPlayerPosition();
        Position newPos = new Position(pos.getRow(), pos.getCol());

        GameState newState = new GameState(newBoard, newPos, state.getCurrentLevel());
        newState.setMoveCount(state.getMoveCount());
        newState.setPushCount(state.getPushCount());

        return newState;
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

        if (!board.isValidPosition(nextPos.getRow(), nextPos.getCol())) {
            return false;
        }

        Cell targetCell = board.getCell(nextPos.getRow(), nextPos.getCol());

        if (targetCell instanceof WallCell) {
            return false;
        }

        if (targetCell instanceof BoxCell || targetCell instanceof BoxOnGoalCell) {

            Position afterNext = getNextPosition(nextPos, direction);

            if (!board.isValidPosition(afterNext.getRow(), afterNext.getCol())) {
                return false;
            }

            Cell behindCell = board.getCell(afterNext.getRow(), afterNext.getCol());

            if (behindCell instanceof WallCell ||
                    behindCell instanceof BoxCell ||
                    behindCell instanceof BoxOnGoalCell) {
                return false;
            }

            return true;
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
     * Loads a level by ID and initializes a fresh game state.
     * * @param id The level identifier.
     * @return true if the level was loaded successfully, false otherwise.
     */
    public boolean loadLevel(int id) {
        if (levelRepo == null) return false;

        Level level = levelRepo.loadLevel(id);

        if (level == null) {
            System.err.println("Could not load level " + id + ". Verify that the file exists.");
            return false;
        }

        this.currentLevel = level;
        this.currentState = level.getInitialState();

        this.moveCount = 0;
        this.pushCount = 0;
        this.gameOver = false;

        startTimer();
        return true;
    }

    /**
     * Resets the current level to its initial state.
     */
    public void resetLevel() {

        if (currentLevel == null) return;

        this.currentState = currentLevel.getInitialState();
        this.moveCount = 0;
        this.pushCount = 0;
        this.gameOver = false;

        startTimer();
    }

    /**
     * Persists the current game state to disk.
     */
    public void saveGame(String username) {

        if (stateRepo != null && currentState != null) {
            // Actualizamos el nivel en el estado antes de guardar si es necesario
            // currentState.setCurrentLevel(...);

            stateRepo.save(currentState, username);
            System.out.println("Partida guardada exitosamente para el jugador: " + username);
        }
    }

    /**
     * Loads the game state specifically for the given player ID.
     * @param username The unique ID of the player to load the game for.
     */
    public void loadGame(String username) {
        if (stateRepo == null) return;

        // Pasamos el ID al repositorio para que busque el archivo correcto
        GameState loaded = stateRepo.load(username);

        if (loaded != null) {
            this.currentState = loaded.clone();
            this.moveCount = loaded.getMoveCount();
            this.pushCount = loaded.getPushCount();
            System.out.println("Game loaded successfully for player ID: " + username);
        } else {
            System.err.println("No saved game found for player ID: " + username);
        }
    }

    /**
     * Registers a new player in the system.
     * @param player player to register
     */
    public void registerPlayer(Player player) {

        if (player != null) {
            this.currentPlayer = player;
        }
    }

    /**
     * Loads an existing player profile by username.
     * @param username player's username
     */
    public void loadPlayer(String username) {
        this.currentPlayer = new Player(username);
    }

    /**
     * Saves the current player's updated statistics.
     */
    public void savePlayerStats() {

        if (playerRepo != null && currentPlayer != null) {
            playerRepo.saveStats(currentPlayer);
        }
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

        return new Leaderboard(levelId, 10);
    }

    /**
     * Checks if the current state has been visited before.
     *
     * @return true if already visited
     */
    public boolean isRepeatedState() {

        StateKey key = new StateKey(
                currentState.getPlayerPosition(),
                currentState.getBoard().getBoxPositions()
        );

        if (visitedStates.contains(key)) {
            return true;
        }

        visitedStates.add(key);
        return false;
    }

    /**
     * Checks if a save file exists for a specific player.
     * @param username The unique ID of the player to check.
     */
    public boolean hasSavedGame(String username) {
        return stateRepo != null && stateRepo.hasSavedGame(username);
    }

    public boolean usernameExists(String username) {
        return playerRepo != null && playerRepo.exists(username);
    }

    /**
     * Checks if a cell contains a box.
     *
     * @param cell cell to evaluate
     * @return true if the cell has a box
     */
    private boolean isBox(Cell cell) {
        return cell instanceof BoxCell || cell instanceof BoxOnGoalCell;
    }

    /**
     * Moves a box in the given direction.
     *
     * @param direction movement direction
     * @param boxPos position of the box
     */
    private void moveBox(Direction direction, Position boxPos) {

        Board board = currentState.getBoard();
        Position dest = getNextPosition(boxPos, direction);

        Cell destinationCell = board.getCell(dest.getRow(), dest.getCol());
        Cell boxCell = board.getCell(boxPos.getRow(), boxPos.getCol());

        // place box
        if (destinationCell instanceof GoalCell) {
            board.setCell(dest.getRow(), dest.getCol(), new BoxOnGoalCell());
        } else {
            board.setCell(dest.getRow(), dest.getCol(), new BoxCell());
        }

        // clear previous position
        if (boxCell instanceof BoxOnGoalCell) {
            board.setCell(boxPos.getRow(), boxPos.getCol(), new GoalCell());
        } else {
            board.setCell(boxPos.getRow(), boxPos.getCol(), new EmptyCell());
        }
    }

    /**
     * Clears the player's previous position.
     *
     * @param pos player position
     * @param cell current cell
     */
    private void clearPlayerCell(Position pos, Cell cell) {

        Board board = currentState.getBoard();

        if (cell instanceof PlayerOnGoalCell) {
            board.setCell(pos.getRow(), pos.getCol(), new GoalCell());
        } else {
            board.setCell(pos.getRow(), pos.getCol(), new EmptyCell());
        }
    }

    /**
     * Places the player in a new position.
     *
     * @param pos destination position
     * @param targetCell cell at destination
     */
    private void placePlayer(Position pos, Cell targetCell) {

        Board board = currentState.getBoard();

        if (targetCell instanceof GoalCell) {
            board.setCell(pos.getRow(), pos.getCol(), new PlayerOnGoalCell());
        } else {
            board.setCell(pos.getRow(), pos.getCol(), new PlayerCell());
        }
    }

    public void undo() {

        if (undoStack.isEmpty()) return;

        GameState prev = undoStack.pop();

        if (prev != null) {
            this.currentState = prev;
            this.moveCount = prev.getMoveCount();
            this.pushCount = prev.getPushCount();
        }
    }

    public void enqueueMove(String move) {
        moveQueue.enqueue(move);
    }

    public void processMoves() {

        while (!moveQueue.isEmpty()) {

            String move = moveQueue.dequeue();

            Direction dir = parseDirection(move);

            if (dir != null) {
                move(dir);
            }
        }
    }

    private Direction parseDirection(String move) {

        if (move == null) return null;

        switch (move.toUpperCase()) {
            case "W": return Direction.UP;
            case "S": return Direction.DOWN;
            case "A": return Direction.LEFT;
            case "D": return Direction.RIGHT;
            default: return null;
        }
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

    public void setStateForTesting(GameState state) {
        this.currentState = state;
        if (state != null) {
            this.moveCount = state.getMoveCount();
            this.pushCount = state.getPushCount();
        }
    }

    public void setResumingGame(boolean resuming) {
        this.resumingGame = resuming;
    }

    public boolean isResumingGame() {
        return resumingGame;
    }

    public void setCurrentPlayer(Player selectedPlayer) {
    }
}

