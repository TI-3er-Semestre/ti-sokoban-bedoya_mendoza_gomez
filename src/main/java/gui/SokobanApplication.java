package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;

/**
 * clase principal de javafx que cargara las pantallas
 */
public class SokobanApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL fxmlLocation = getClass().getResource("/fxml/Register.fxml");
        if (fxmlLocation == null) {
            System.out.println("error: no se encontro el archivo fxml");
            return;
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("sokoban - registro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}