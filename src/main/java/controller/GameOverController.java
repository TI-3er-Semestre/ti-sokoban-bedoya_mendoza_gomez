package controller;

import gui.SokobanApplication;
import javafx.fxml.FXML;

public class GameOverController {

    private GameController gameController;

    @FXML
    void initialize() {
        gameController = MainMenuController.getGameController();
    }

    @FXML
    void handleUndo() {
        try {
            gameController.undo();

            gameController.setResumingGame(true);

            SokobanApplication.navigateTo("Game");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleStats() {
        try {
            SokobanApplication.navigateTo("Stats");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMenu() {
        try {
            SokobanApplication.navigateTo("MainMenu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}