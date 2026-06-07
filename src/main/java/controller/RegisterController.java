package controller;

import Model.ExperienceLevel;
import Model.Player;
import Model.PlayerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * controlador para registro
 */
public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField avatarField;

    @FXML
    private ToggleGroup experienceGroup;

    @FXML
    private RadioButton radioBeginner;

    @FXML
    private RadioButton radioAdvanced;

    @FXML
    private RadioButton radioExpert;

    @FXML
    private Label errorLabel;


    @FXML
    public void handleRegister() {
        String name = nameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String avatar = avatarField.getText();

        //errores
        if (name.isEmpty() || email.isEmpty() || username.isEmpty()) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("error: por favor llena todos los campos obligatorios");
            return;
        }

        ExperienceLevel level = ExperienceLevel.BEGINNER;
        if (radioAdvanced.isSelected()) {
            level = ExperienceLevel.ADVANCED;
        } else if (radioExpert.isSelected()) {
            level = ExperienceLevel.EXPERT;
        }

        try {
            // creacion y guardado del jugador
            Player newPlayer = new Player(name, email, username, avatar, level);
            PlayerRepository repo = new PlayerRepository("data/players");
            repo.save(newPlayer);

            // mensaje de confirmacion
            errorLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            errorLabel.setText("¡jugador " + username + " creado con exito!");


            nameField.clear();
            emailField.clear();
            usernameField.clear();
            avatarField.clear();

        } catch (Exception e) {
            // por si ocurre algun error al guardar el archivo json
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("hubo un problema al guardar los datos del jugador");
        }
    }
}