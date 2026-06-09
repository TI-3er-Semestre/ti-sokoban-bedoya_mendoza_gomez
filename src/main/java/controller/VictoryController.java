package controller;

import Model.GameResult;
import Model.Player;
import Model.PlayerStats;
import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controlador de la pantalla de victoria.
 * Recibe los datos de la partida desde GameScreenController
 * a través de VictoryData (clase estática compartida).
 */
public class VictoryController {

    @FXML private Label lblLevelName;
    @FXML private Label lblMoves;
    @FXML private Label lblPushes;
    @FXML private Label lblTime;
    @FXML private Label lblBestMoves;
    @FXML private Label lblBestTime;
    @FXML private Label lblNewRecord;
    @FXML private Button btnNextLevel;
    @FXML private Button btnPlayAgain;

    // ---------------------------------------------------------------
    // DTO estático para pasar datos entre pantallas sin acoplamiento
    // ---------------------------------------------------------------

    /** Datos de la última partida completada, fijados por GameScreenController antes de navegar. */
    public static class VictoryData {
        public static int levelId;
        public static int moves;
        public static int pushes;
        public static long timeMillis;
    }

    // ---------------------------------------------------------------
    // Inicialización
    // ---------------------------------------------------------------

    @FXML
    void initialize() {
        GameController gc = MainMenuController.getGameController();
        Player player     = gc.getCurrentPlayer();

        int    levelId    = VictoryData.levelId;
        int    moves      = VictoryData.moves;
        int    pushes     = VictoryData.pushes;
        long   timeMillis = VictoryData.timeMillis;

        if (player != null) {
            GameResult result = new GameResult(levelId, moves, pushes, timeMillis);
            player.getStats().addResult(result);
            gc.savePlayerStats();
        }

        lblLevelName.setText("Nivel " + levelId);
        lblMoves.setText(String.valueOf(moves));
        lblPushes.setText(String.valueOf(pushes));
        lblTime.setText(formatTime(timeMillis));

        if (player != null) {
            PlayerStats stats = player.getStats();

            GameResult prevBestMoves = stats.getBestByMoves(levelId);
            GameResult prevBestTime  = stats.getBestByTime(levelId);

            GameResult result = new GameResult(levelId, moves, pushes, timeMillis);
            stats.addResult(result);
            gc.savePlayerStats();

            GameResult bestMoves = stats.getBestByMoves(levelId);
            GameResult bestTime  = stats.getBestByTime(levelId);

            lblBestMoves.setText(bestMoves != null ? bestMoves.getMoves() + " mov" : "-");
            lblBestTime.setText(bestTime   != null ? formatTime(bestTime.getTimeMillis()) : "-");

            boolean isMovesRecord = prevBestMoves == null || moves < prevBestMoves.getMoves();
            boolean isTimeRecord  = prevBestTime  == null || timeMillis < prevBestTime.getTimeMillis();

            if (isMovesRecord || isTimeRecord) {
                lblNewRecord.setText("¡Nuevo récord personal! 🏆");
            } else {
                lblNewRecord.setText("");
            }
        } else {
            lblBestMoves.setText("-");
            lblBestTime.setText("-");
            lblNewRecord.setText("");
        }

        if (levelId >= 3) {
            btnNextLevel.setDisable(true);
            btnNextLevel.setStyle(btnNextLevel.getStyle() + "-fx-opacity: 0.4;");
        }
    }

    // ---------------------------------------------------------------
    // Handlers de botones
    // ---------------------------------------------------------------

    @FXML
    void handlePlayAgain() {
        try {
            GameController gc = MainMenuController.getGameController();
            gc.loadLevel(VictoryData.levelId);
            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            System.err.println("Error al reiniciar nivel: " + e.getMessage());
        }
    }

    /**
     * Handles the "Next Level" button action.
     * Updates the global level ID and triggers the loading process.
     */
    @FXML
    void handleNextLevel() {
        try {
            // 1. Calculate the next level ID
            int nextLevelId = VictoryData.levelId + 1;
            GameController gc = MainMenuController.getGameController();

            System.out.println("Attempting to load level: " + nextLevelId);

            // 2. Load the level. If it fails, the controller returns false
            if (gc.loadLevel(nextLevelId)) {
                // Update the static data so the game knows it's now at the new level
                VictoryData.levelId = nextLevelId;

                // Navigate to the game screen.
                // The initialize() method in GameScreenController will handle the render
                SokobanApplication.navigateTo("Game");
            } else {
                System.out.println("No more levels found. Returning to Main Menu.");
                SokobanApplication.navigateTo("MainMenu");
            }
        } catch (Exception e) {
            System.err.println("Error loading next level: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewStats() {
        try {
            SokobanApplication.navigateTo("Stats");
        } catch (Exception e) {
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
        }
    }

    @FXML
    void handleMainMenu() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            System.err.println("Error al volver al menú: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Utilidades
    // ---------------------------------------------------------------

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
