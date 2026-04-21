package Model;

/**
 * Handles saving and loading the current game state to disk.
 * Uses Java serialization to persist the GameState object.
 */
public class GameStateRepository {

    private String savePath;

    public GameStateRepository(String savePath) {
        // TODO: implement
    }

    /**
     * Serializes and saves the current game state to disk.
     * @param state current GameState to persist
     */
    public void save(GameState state) {
        // TODO: implement using Java Serializable
    }

    /**
     * Loads and deserializes the saved game state from disk.
     * @return saved GameState, or null if no save exists
     */
    public GameState load() {
        // TODO: implement
        return null;
    }

    /**
     * Returns true if a saved game file exists.
     * @return true if save file is present
     */
    public boolean hasSavedGame() {
        // TODO: implement
        return false;
    }

    /**
     * Deletes the saved game file from disk.
     */
    public void deleteSave() {
        // TODO: implement
    }
}
