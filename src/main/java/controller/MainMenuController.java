package controller;

import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controller for the Main Menu screen.
 * Handles navigation to other screens.
 */
public class MainMenuController {

    @FXML private Button btnNewGame;
    @FXML private Button btnContinue;
    @FXML private Button btnStats;
    @FXML private Button btnHelp;
    @FXML private Button btnExit;

    private static GameController gameController = new GameController(
            "src/main/resources/levels",
            "src/main/resources/saves/save.dat",
            "src/main/resources/players"
    );

    /**
     * Returns the shared GameController instance across all screens.
     */
    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    void handleNewGame() {
        try {
            SokobanApplication.navigateTo("Register");
        } catch (Exception e) {
            showError("Error al cargar la pantalla de registro.");
        }
    }

    @FXML
    void handleContinue() {
        try {
            if (gameController.hasSavedGame()) {
                gameController.loadGame();
                gameController.setResumingGame(true);
                SokobanApplication.navigateTo("Game");
            } else {
                showInfo("No hay partida guardada disponible.");
            }
        } catch (Exception e) {
            showError("Error al cargar la partida guardada.");
        }
    }

    @FXML
    void handleStats() {
        try {
            SokobanApplication.navigateTo("Stats");
        } catch (Exception e) {
            showError("Error al cargar las estadísticas.");
        }
    }

    @FXML
    void handleHelp() {
        try {
            SokobanApplication.navigateTo("Help");
        } catch (Exception e) {
            showError("Error al cargar la ayuda.");
        }
    }

    @FXML
    void handleExit() {
        SokobanApplication.getPrimaryStage().close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(message);
        alert.showAndWait();
    }
}