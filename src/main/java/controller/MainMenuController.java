package controller;

import Model.Player;
import Model.PlayerRepository;
import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.Arrays;
import java.util.List;

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
    @FXML private ChoiceBox<Player> playerSelector;


    private static GameController gameController = new GameController(
            "src/main/resources/levels",
            "src/main/resources/saves",
            "src/main/resources/players"
            

    );
    private PlayerRepository playerRepository = MainMenuController.getGameController().getPlayerRepository();

    /**
     * Returns the shared GameController instance across all screens.
     */
    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    public void initialize() {
        try {
            Player[] playersArray = playerRepository.loadAll();

            if (playersArray != null && playersArray.length > 0) {
                List<Player> players = Arrays.asList(playersArray);

                playerSelector.getItems().clear();
                playerSelector.getItems().addAll(players);

                playerSelector.setValue(playerSelector.getItems().get(0));

                btnNewGame.setDisable(false);
                btnContinue.setDisable(false);
            } else {
                btnNewGame.setDisable(true);
                btnContinue.setDisable(true);
            }
        } catch (Exception e) {
            showError("Error al cargar los perfiles: " + e.getMessage());
        }
    }

    @FXML
    void handleNewGame() {
        Player selected = playerSelector.getValue();

        if (selected == null) {
            showError("No hay un usuario seleccionado. Regístrate o selecciona un jugador.");
            return; // Salida controlada
        }

        try {
            GameController gc = getGameController();
            gc.setCurrentPlayer(selected);
            gc.setResumingGame(false);

            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            showError("Error al iniciar nueva partida: " + e.getMessage());
        }
    }

    @FXML
    void handleRegister() {
        try {
            SokobanApplication.navigateTo("Register");
        } catch (Exception e) {
            showError("No se pudo abrir la pantalla de registro.");
        }
    }

    @FXML
    void handleContinue() {
        Player selected = playerSelector.getValue();
        if (selected == null) {
            showError("Por favor, selecciona un jugador primero.");
            return;
        }

        try {
            String username = selected.getUsername();
            GameController gc = getGameController();

            if (gc.hasSavedGame(username)) {
                gc.loadGame(username);
                gc.setResumingGame(true);
                gc.setCurrentPlayer(selected);
                SokobanApplication.navigateTo("Game");
            } else {
                showInfo("No existe una partida guardada para " + username);
            }
        } catch (Exception e) {
            showError("Error al cargar la partida: " + e.getMessage());
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