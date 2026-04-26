package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.*;

/**
 * Handles persistence of player profiles and statistics using Gson.
 */
public class PlayerRepository {

    private String playersPath;
    private Gson gson;

    public PlayerRepository(String playersPath) {
        this.playersPath = playersPath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        new File(playersPath).mkdirs();
    }

    /**
     * Saves a player's profile to disk as a JSON file named by username.
     * @param player player to persist
     */
    public void save(Player player) {
        String filePath = playersPath + File.separator + player.getUsername() + ".json";
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(player, writer);
        } catch (IOException e) {
            System.err.println("Error saving player: " + e.getMessage());
        }
    }

    /**
     * Loads a player profile by username.
     * @param username unique username to look up
     * @return Player object, or null if not found
     */
    public Player load(String username) {
        String filePath = playersPath + File.separator + username + ".json";
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, Player.class);
        } catch (IOException e) {
            System.err.println("Error loading player: " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads all registered players from the players directory.
     * @return array of all Player objects
     */
    public Player[] loadAll() {
        File dir = new File(playersPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            return new Player[0];
        }
        Player[] players = new Player[files.length];
        for (int i = 0; i < files.length; i++) {
            try (Reader reader = new FileReader(files[i])) {
                players[i] = gson.fromJson(reader, Player.class);
            } catch (IOException e) {
                System.err.println("Error loading player file: " + files[i].getName());
                players[i] = null;
            }
        }
        return players;
    }

    /**
     * Returns true if a player with the given username already exists.
     * @param username username to check
     * @return true if username is taken
     */
    public boolean exists(String username) {
        String filePath = playersPath + File.separator + username + ".json";
        return new File(filePath).exists();
    }

    /**
     * Saves updated statistics for an existing player.
     * Overwrites the existing file with updated stats.
     * @param player player whose stats should be updated
     */
    public void saveStats(Player player) {
        save(player);
    }
}