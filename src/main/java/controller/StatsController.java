package controller;

import Model.GameResult;
import Model.Player;
import Model.PlayerStats;
import gui.SokobanApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controlador de la pantalla de estadísticas.
 * Obtiene el jugador actual desde el GameController compartido.
 */
public class StatsController {

    @FXML private Label lblUsername;
    @FXML private Label lblTotalGames;
    @FXML private Label lblExperience;

    @FXML private Label lblBest1Moves;
    @FXML private Label lblBest1Time;
    @FXML private Label lblBest2Moves;
    @FXML private Label lblBest2Time;
    @FXML private Label lblBest3Moves;
    @FXML private Label lblBest3Time;

    @FXML private ListView<String> historyListView;

    @FXML
    void initialize() {
        GameController gc = MainMenuController.getGameController();
        Player player = gc.getCurrentPlayer();

        if (player == null) {
            lblUsername.setText("jugador: (sin sesión activa)");
            lblTotalGames.setText("0");
            lblExperience.setText("-");
            historyListView.setItems(
                FXCollections.observableArrayList("Sin partidas registradas aún.")
            );
            return;
        }

        PlayerStats stats = player.getStats();

        // Encabezado
        lblUsername.setText("jugador: " + player.getUsername());
        lblTotalGames.setText(String.valueOf(stats.getTotalGames()));
        lblExperience.setText(player.getExperienceLevel().name().toLowerCase());

        // Mejores marcas por nivel
        fillBestForLevel(stats, 1, lblBest1Moves, lblBest1Time);
        fillBestForLevel(stats, 2, lblBest2Moves, lblBest2Time);
        fillBestForLevel(stats, 3, lblBest3Moves, lblBest3Time);

        // Historial ordenado por movimientos (usa getSortedResults del modelo)
        GameResult[] sorted = stats.getSortedResults();
        ObservableList<String> items = FXCollections.observableArrayList();

        for (GameResult r : sorted) {
            items.add(String.format(
                "Nivel %d  |  %d movs  |  %d empujes  |  %s",
                r.getLevelId(),
                r.getMoves(),
                r.getPushes(),
                formatTime(r.getTimeMillis())
            ));
        }

        if (items.isEmpty()) {
            items.add("Sin partidas registradas aún.");
        }

        historyListView.setItems(items);
    }

    private void fillBestForLevel(PlayerStats stats, int levelId,
                                   Label movesLabel, Label timeLabel) {
        GameResult bestMoves = stats.getBestByMoves(levelId);
        GameResult bestTime  = stats.getBestByTime(levelId);

        movesLabel.setText(bestMoves != null ? bestMoves.getMoves() + " mov" : "-");
        timeLabel.setText(bestTime   != null ? formatTime(bestTime.getTimeMillis()) : "-");
    }

    @FXML
    void handleBack() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            System.err.println("Error al volver al menú: " + e.getMessage());
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
