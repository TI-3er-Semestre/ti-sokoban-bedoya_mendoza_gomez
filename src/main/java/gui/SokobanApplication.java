package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * clase principal de javafx que cargara las pantallas
 */
public class SokobanApplication extends Application {

    @Override
    public void start(Stage stage) {
        // panel basico para probar
        StackPane root = new StackPane();
        root.getChildren().add(new Label("sokoban mvc configurado"));

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("sokoban");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}