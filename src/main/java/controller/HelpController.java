package controller;

import gui.SokobanApplication;
import javafx.fxml.FXML;

public class HelpController {

    @FXML
    void handleBack() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            System.err.println("Error al volver al menú: " + e.getMessage());
        }
    }
}