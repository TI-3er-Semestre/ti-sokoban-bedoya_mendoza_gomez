package controller;

import Model.Player;
import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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

    /**
     * Returns the shared GameController instance across all screens.
     */
    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    public void initialize() {
        // Al cargar el menú, leemos todos los jugadores existentes en "src/main/resources/players"
        // Asegúrate de que esta ruta coincida con la que tienes en el constructor de GameController
        Model.PlayerRepository repo = new Model.PlayerRepository("src/main/resources/players");

        playerSelector.getItems().addAll(repo.loadAll());

        // Seleccionamos el primero si hay alguno, para que no esté vacío
        if (!playerSelector.getItems().isEmpty()) {
            playerSelector.setValue(playerSelector.getItems().get(0));
        }
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
        // 1. Obtenemos el jugador seleccionado en el menú
        Player selectedPlayer = playerSelector.getValue();

        if (selectedPlayer == null) {
            showError("Por favor, selecciona un jugador primero.");
            return;
        }

        try {
            // 2. Usamos el nombre de usuario para verificar y cargar la partida
            String username = selectedPlayer.getUsername();

            if (gameController.hasSavedGame(username)) {
                // Pasamos el username al controlador para cargar su estado específico
                gameController.loadGame(username);
                gameController.setResumingGame(true);

                // Importante: Asegúrate de guardar el jugador actual en el controlador
                gameController.setCurrentPlayer(selectedPlayer);

                SokobanApplication.navigateTo("Game");
            } else {
                showInfo("No hay partida guardada para " + username);
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