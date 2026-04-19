
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
        this.moveCount = 0;
        this.pushCount = 0;
        this.elapsedTime = 0;
    }

    /**
     * Returns true if all boxes are on goal positions (victory condition).
     * Delegates to Board.isCompleted().
     * @return true if the level is won
     */
    public boolean isVictory() {
        if (board == null) return false;
        return board.isCompleted();
    }

    /**
     * Returns a deep copy of this game state.
     * Required for undo stack and transposition table.
     * @return cloned GameState
     */
    public GameState clone() {

        if (this.board == null || this.playerPosition == null) {
            return null;
        }

        Board boardCopy = this.board.clone();

        Position positionCopy = new Position(
                this.playerPosition.getRow(),
                this.playerPosition.getCol()
        );

        GameState copy = new GameState(boardCopy, positionCopy, this.currentLevel);

        copy.setMoveCount(this.moveCount);
        copy.setPushCount(this.pushCount);
        copy.setElapsedTime(this.elapsedTime);

        return copy;
    }

    public Board getBoard() {
        return board;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int getPushCount() {
        return pushCount;
    }

    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
