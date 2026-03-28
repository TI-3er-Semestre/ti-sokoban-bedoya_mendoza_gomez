
/**
 * Represents a complete snapshot of the game at a given moment.
 * Used for undo (stack), transposition table (hash), and save/load.
 */
public class GameState {

    private Board board;
    private Position playerPosition;
    private int moveCount;
    private int pushCount;
    private long elapsedTime;
    private int currentLevel;

    public GameState(Board board, Position playerPosition, int currentLevel) {
        this.board = board;
        this.playerPosition = playerPosition;
        this.currentLevel = currentLevel;
    }

    /**
     * Returns true if all boxes are on goal positions (victory condition).
     * Delegates to Board.isCompleted().
     * @return true if the level is won
     */
    public boolean isVictory() {
        // TODO: implement
        return false;
    }

    /**
     * Returns a deep copy of this game state.
     * Required for undo stack and transposition table.
     * @return cloned GameState
     */
    public GameState clone() {
        // TODO: implement
        return null;
    }

    public Board getBoard() {
        return board;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Position playerPosition) {
        // TODO: implement
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        // TODO: implement
    }

    public int getPushCount() {
        return pushCount;
    }

    public void setPushCount(int pushCount) {
        // TODO: implement
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        // TODO: implement
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
