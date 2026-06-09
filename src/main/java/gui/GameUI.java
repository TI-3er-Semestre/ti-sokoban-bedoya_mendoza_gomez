package gui;


import Model.Player;
import Model.PlayerStats;
import controller.GameController;
import Model.Player;
import Model.PlayerStats;
import javafx.application.Platform;

/**
 * View layer of the Sokoban application (MVC - View).
 * Responsible for all user interaction and rendering.
 * In Entrega 3 this will be implemented with JavaFX.
 * For now it serves as a stub so the project compiles.
 */
public class GameUI {

    private controller.GameController controller;

    public GameUI(controller.GameController controller) {
        this.controller = controller;
    }

    /**
     * Starts the JavaFX application.
     */
    public void start() {
        SokobanApplication.main(new String[]{});
    }

    /**
     * Stops the application and saves the current game state.
     */
    public void stop() {
        controller.saveGame("savegame.dat");
        Platform.exit();
    }

    /**
     * Navigates to the main menu screen.
     */
    public void showMainMenu() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            System.err.println("Error showing main menu: " + e.getMessage());
        }
    }

    /**
     * Navigates to the game screen and renders the current board.
     */
    public void printBoard() {
        try {
            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            System.err.println("Error showing game board: " + e.getMessage());
        }
    }

    /**
     * Handles a keyboard input and delegates to the controller.
     * @param key key string from keyboard event (e.g. "UP", "W")
     */
    public void handleInput(String key) {
        controller.enqueueMove(key);
        controller.processMoves();
    }

    /**
     * Displays the current move, push, and time counters.
     * Delegates to the GameScreenController via the Game screen.
     */
    public void printCounters() {
        try {
            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            System.err.println("Error showing counters: " + e.getMessage());
        }
    }

    /**
     * Navigates to the victory screen.
     */
    public void showVictoryScreen() {
        try {
            SokobanApplication.navigateTo("Victory");
        } catch (Exception e) {
            System.err.println("Error showing victory screen: " + e.getMessage());
        }
    }

    /**
     * Navigates to the game over screen.
     */
    public void showGameOverScreen() {
        try {
            SokobanApplication.navigateTo("GameOver");
        } catch (Exception e) {
            System.err.println("Error showing game over screen: " + e.getMessage());
        }
    }

    /**
     * Navigates to the register screen and returns the registered player.
     * Note: in JavaFX the result is handled asynchronously via the controller.
     * @return null — registration result is handled by RegisterController
     */
    public Player promptRegister() {
        try {
            SokobanApplication.navigateTo("Register");
        } catch (Exception e) {
            System.err.println("Error showing register screen: " + e.getMessage());
        }
        return null;
    }

    /**
     * Navigates to the statistics screen.
     * @param stats player statistics to display
     */
    public void showStats(PlayerStats stats) {
        try {
            SokobanApplication.navigateTo("Stats");
        } catch (Exception e) {
            System.err.println("Error showing stats screen: " + e.getMessage());
        }
    }

    /**
     * Navigates to the help screen.
     */
    public void showHelp() {
        try {
            SokobanApplication.navigateTo("Help");
        } catch (Exception e) {
            System.err.println("Error showing help screen: " + e.getMessage());
        }
    }
}
