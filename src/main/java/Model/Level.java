package Model;

/**
 * Represents a Sokoban level loaded from a JSON file.
 * Stores the level metadata and the initial board state.
 */
public class Level {

    private int levelId;
    private String name;
    private ExperienceLevel difficulty;
    private Board initialBoard;

    public Level(int levelId, String name, ExperienceLevel difficulty, Board initialBoard) {
        this.levelId = levelId;
        this.name = name;
        this.difficulty = difficulty;
        this.initialBoard = initialBoard;
    }

    /**
     * Creates the initial GameState for this level.
     * @return a fresh GameState ready to play
     */
    public GameState getInitialState() {

        if (initialBoard == null) return null;

        Board boardCopy = initialBoard.clone();

        Position playerPos = null;

        for (int i = 0; i < boardCopy.getRows(); i++) {
            for (int j = 0; j < boardCopy.getCols(); j++) {

                Cell c = boardCopy.getCell(i, j);

                if (c instanceof PlayerCell || c instanceof PlayerOnGoalCell) {
                    playerPos = new Position(i, j);
                }
            }
        }

        return new GameState(boardCopy, playerPos, levelId);
    }

    public int getId() {
        return levelId;
    }

    public String getName() {
        return name;
    }

    public ExperienceLevel getDifficulty() {
        return difficulty;
    }

    public Board getInitialBoard() {
        return initialBoard;
    }
}
