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

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sokoban");
        primaryStage.setResizable(false);
        navigateTo("MainMenu");
        primaryStage.show();
    }

    /**
     * Navigates to a screen by loading its FXML file.
     * @param fxmlName name of the FXML file without extension
     */
    public static void navigateTo(String fxmlName) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                SokobanApplication.class.getResource("/fxml/" + fxmlName + ".fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    /**
     * Navigates to a screen and returns its controller.
     * @param fxmlName name of the FXML file without extension
     * @return the controller of the loaded screen
     */
    public static Object navigateToWithController(String fxmlName) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                SokobanApplication.class.getResource("/fxml/" + fxmlName + ".fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        return loader.getController();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}
