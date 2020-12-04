package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("DAO");
        stage.centerOnScreen();
        stage.setResizable(true);

        // Premier node
        Parent home = null;
        try {
            home = FXMLLoader.load(getClass().getResource("/org/example/gui/home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scenehome = new Scene(home);
        stage.setScene(scenehome);

        stage.show();
    }
}