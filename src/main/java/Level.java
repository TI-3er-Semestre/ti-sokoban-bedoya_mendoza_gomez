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
        // TODO: implement
    }

    /**
     * Creates the initial GameState for this level.
     * @return a fresh GameState ready to play
     */
    public GameState getInitialState() {
        // TODO: implement — clone the board, find player position, return new GameState
        return null;
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
