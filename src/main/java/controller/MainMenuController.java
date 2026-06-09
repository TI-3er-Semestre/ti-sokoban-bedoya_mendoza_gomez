package controller;

import Model.Player;
import Model.PlayerRepository;
import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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
            "src/main/resources/saves/save.dat",
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
            // 1. Recibimos el arreglo como lo devuelve tu repositorio
            Model.Player[] players = playerRepository.loadAll();

            // 2. Verificamos si no es nulo y tiene elementos (usando .length para arreglos)
            if (players != null && players.length > 0) {

                // 3. Limpiamos y añadimos los jugadores al ChoiceBox
                playerSelector.getItems().clear();
                playerSelector.getItems().addAll(players);

                // 4. Seleccionamos el primero
                playerSelector.setValue(playerSelector.getItems().get(0));

                // Habilitamos los botones
                btnNewGame.setDisable(false);
                btnContinue.setDisable(false);
            } else {
                // Si no hay jugadores, deshabilitamos los botones
                btnNewGame.setDisable(true);
                btnContinue.setDisable(true);
            }
        } catch (Exception e) {
            showError("Error al cargar los perfiles: " + e.getMessage());
        }
    }

    @FXML
    void handleNewGame() {
        // 1. Verificación controlada
        Player selected = playerSelector.getValue();

        if (selected == null) {
            showError("No hay un usuario seleccionado. Regístrate o selecciona un jugador.");
            return; // Salida controlada
        }

        try {
            // 2. Ejecución segura
            GameController gc = getGameController();
            gc.setCurrentPlayer(selected);
            gc.setResumingGame(false); // Inicia desde cero

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
        // 1. Verificación controlada
        Player selected = playerSelector.getValue();
        if (selected == null) {
            showError("Por favor, selecciona un jugador primero.");
            return;
        }

        try {
            String username = selected.getUsername();
            GameController gc = getGameController();

            if (gc.hasSavedGame(username)) {
                // 2. Ejecución segura dentro del try-catch que ya tenías
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