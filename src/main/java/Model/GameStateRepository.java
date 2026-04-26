package Model;

import java.io.*;


/**
 * Handles saving and loading the current game state to disk.
 * Uses Java serialization to persist the GameState object.
 */
public class GameStateRepository {

    private String savePath;

    public GameStateRepository(String savePath) {
        this.savePath = savePath;
    }

    /**
     * Serializes and saves the current game state to disk.
     * @param state current GameState to persist
     */
    public void save(GameState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(savePath))) {

            out.writeObject(state);

        } catch (IOException e) {
            System.err.println("Error al guardad el juego: " + e.getMessage());
        }
    }

    /**
     * Loads and deserializes the saved game state from disk.
     * @return saved GameState, or null if no save exists
     */
    public GameState load() {
        File file = new File(savePath);

        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {

            return (GameState) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        return null;
        }
    }

    /**
     * Returns true if a saved game file exists.
     * @return true if save file is present
     */
    public boolean hasSavedGame() {
        return new File(savePath).exists();
    }

    /**
     * Deletes the saved game file from disk.
     */
    public void deleteSave() {
        File file = new File(savePath);

        if (file.exists()) {
            file.delete();
        }
    }
}
