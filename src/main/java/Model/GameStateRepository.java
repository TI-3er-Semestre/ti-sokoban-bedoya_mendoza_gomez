package Model;

import java.io.*;


/**
 * Handles saving and loading the current game state to disk.
 * Uses Java serialization to persist the GameState object.
 */
public class GameStateRepository {

    private final String saveFolder = "saves";
    private String savePath;

    public GameStateRepository(String savePath) {
        new File(saveFolder).mkdirs();
    }

    /**
     * Serializes and saves the current game state to disk.
     * @param state current GameState to persist
     */
    public void save(GameState state, String username) {
        File folder = new File(saveFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String playerSavePath = saveFolder + File.separator + username + ".ser";

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(playerSavePath))) {
            out.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a saved game state for a specific player from disk.
     * @param username The unique ID of the player.
     * @return saved GameState, or null if no save exists or error occurs.
     */
    public GameState load(String username) {
        String playerSavePath = saveFolder + File.separator + username + ".ser";
        File file = new File(playerSavePath);

        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {

            return (GameState) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game for player " + username + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a saved game file exists for a specific player.
     * @param username The unique ID of the player.
     * @return true if save file is present
     */
    public boolean hasSavedGame(String username) {
        String path = saveFolder + File.separator + username + ".ser";
        return new File(path).exists();
    }

    /**
     * Deletes the saved game file for a specific player.
     * @param playerId The unique ID of the player.
     */
    public void deleteSave(int playerId) {
        String playerSavePath = "save_player_" + playerId + ".ser";
        File file = new File(playerSavePath);

        if (file.exists()) {
            file.delete();
        }
    }
}
