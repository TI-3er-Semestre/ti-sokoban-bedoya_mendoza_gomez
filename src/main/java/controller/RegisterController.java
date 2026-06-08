package controller;

import Model.ExperienceLevel;
import Model.Player;
import gui.SokobanApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * controlador para registro
 */
public class RegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUsername;
    @FXML private ComboBox<String> cmbAvatar;
    @FXML private ComboBox<ExperienceLevel> cmbExperience;
    @FXML private Button btnRegister;
    @FXML private Button btnBack;
    @FXML private Label lblError;

    @FXML
    void initialize() {
        // Opciones de avatar
        cmbAvatar.getItems().addAll("🧑", "👩", "🧔", "👱", "🧕", "🧒");
        cmbAvatar.getSelectionModel().selectFirst();

        // Niveles de experiencia
        cmbExperience.getItems().addAll(ExperienceLevel.values());
        cmbExperience.getSelectionModel().selectFirst();

        lblError.setVisible(false);
    }

    @FXML
    void handleRegister() {
        String name       = txtName.getText().trim();
        String email      = txtEmail.getText().trim();
        String username   = txtUsername.getText().trim();
        String avatar     = cmbAvatar.getValue();
        ExperienceLevel level = cmbExperience.getValue();

        // Validaciones
        if (name.isEmpty() || email.isEmpty() || username.isEmpty()) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showError("El correo electrónico no es válido.");
            return;
        }

        if (username.length() < 3) {
            showError("El username debe tener al menos 3 caracteres.");
            return;
        }

        // Verificar si el username ya existe
        controller.GameController gc = MainMenuController.getGameController();
        if (gc.usernameExists(username)) {
            showError("El username ya está en uso. Elige otro.");
            return;
        }

        // Crear y registrar jugador
        Player player = new Player(name, email, username, avatar, level);
        gc.registerPlayer(player);

        // Navegar al juego
        try {
            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            showError("Error al cargar la pantalla de juego.");
        }
    }

    @FXML
    void handleBack() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            showError("Error al volver al menú.");
        }
    }

    private void showError(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
    }
}