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
        public static int    levelId     = 1;
        public static int    moves       = 0;
        public static int    pushes      = 0;
        public static long   timeMillis  = 0;
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

        // Registrar resultado en el historial del jugador
        if (player != null) {
            GameResult result = new GameResult(player, levelId, moves, pushes, timeMillis);
            player.getStats().addResult(result);
            gc.savePlayerStats();
        }

        // Llenar resultados de la partida
        lblLevelName.setText("Nivel " + levelId);
        lblMoves.setText(String.valueOf(moves));
        lblPushes.setText(String.valueOf(pushes));
        lblTime.setText(formatTime(timeMillis));

        // Mejores marcas personales
        if (player != null) {
            PlayerStats stats = player.getStats();

            GameResult bestMoves = stats.getBestByMoves(levelId);
            GameResult bestTime  = stats.getBestByTime(levelId);

            lblBestMoves.setText(bestMoves != null ? bestMoves.getMoves() + " mov" : "-");
            lblBestTime.setText(bestTime  != null ? formatTime(bestTime.getTimeMillis()) : "-");

            // Detectar récord nuevo
            boolean isMovesRecord = bestMoves != null && bestMoves.getMoves() == moves;
            boolean isTimeRecord  = bestTime  != null && bestTime.getTimeMillis() == timeMillis;

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

        // Deshabilitar "Siguiente nivel" si es el último
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

    @FXML
    void handleNextLevel() {
        try {
            int next = VictoryData.levelId + 1;
            GameController gc = MainMenuController.getGameController();
            gc.loadLevel(next);
            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            System.err.println("Error al cargar siguiente nivel: " + e.getMessage());
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
