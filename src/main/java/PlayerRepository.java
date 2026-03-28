/**
 * Handles persistence of player profiles and statistics using Gson.
 */
public class PlayerRepository {

    private String playersPath;

    public PlayerRepository(String playersPath) {
        // TODO: implement
    }

    /**
     * Saves a player's profile to disk.
     * @param player player to persist
     */
    public void save(Player player) {
        // TODO: implement using Gson
    }

    /**
     * Loads a player profile by username.
     * @param username unique username to look up
     * @return Player object, or null if not found
     */
    public Player load(String username) {
        // TODO: implement
        return null;
    }

    /**
     * Loads all registered players.
     * @return array of all Player objects
     */
    public Player[] loadAll() {
        // TODO: implement
        return null;
    }

    /**
     * Returns true if a player with the given username already exists.
     * @param username username to check
     * @return true if username is taken
     */
    public boolean exists(String username) {
        // TODO: implement
        return false;
    }

    /**
     * Saves updated statistics for an existing player.
     * @param player player whose stats should be updated
     */
    public void saveStats(Player player) {
        // TODO: implement
    }
}
