/**
 * View layer of the Sokoban application (MVC - View).
 * Responsible for all user interaction and rendering.
 * In Entrega 3 this will be implemented with JavaFX.
 * For now it serves as a stub so the project compiles.
 */
public class GameUI {

    private GameController controller;

    public GameUI(GameController controller) {
        // TODO: implement
    }

    /** Starts the application UI loop. */
    public void start() {
        // TODO: implement with JavaFX in Entrega 3
    }

    /** Stops the application and saves state. */
    public void stop() {
        // TODO: implement
    }

    /** Renders the current board to the screen. */
    public void printBoard() {
        // TODO: implement
    }

    /**
     * Handles a keyboard input event and delegates to the controller.
     * @param key key string from keyboard event
     */
    public void handleInput(String key) {
        // TODO: implement — parse key, call controller.move(direction)
    }

    /** Displays the current move, push, and time counters. */
    public void printCounters() {
        // TODO: implement
    }

    /** Shows the victory screen when the level is completed. */
    public void showVictoryScreen() {
        // TODO: implement
    }

    /** Shows the game over screen. */
    public void showGameOverScreen() {
        // TODO: implement
    }

    /**
     * Shows and processes the player registration form.
     * @return registered Player object
     */
    public Player promptRegister() {
        // TODO: implement
        return null;
    }

    /** Shows the main menu screen. */
    public void showMainMenu() {
        // TODO: implement
    }

    /**
     * Shows the statistics screen for a player.
     * @param stats player statistics to display
     */
    public void showStats(PlayerStats stats) {
        // TODO: implement
    }

    /** Shows the user manual / help screen. */
    public void showHelp() {
        // TODO: implement
    }
}
