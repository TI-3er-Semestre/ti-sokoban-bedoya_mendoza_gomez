module ti.sokoban.bedoya.mendoza.gomez {
    requires com.google.gson;

    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    exports gui;

    opens controller to javafx.fxml;
    exports controller;

    opens Model to javafx.fxml;
    exports Model;

    exports Model.structures;
    exports Model.structures.Graph;

}