package org.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class FormulaireControlleur {
    public Button btn;

    public void test(ActionEvent actionEvent) {
        // Affiche le formulaire
        Scene sceneHome = ((Button) actionEvent.getSource()).getParent().getScene();
        Parent test = sceneHome.getRoot();
        try {
            test = FXMLLoader.load(getClass().getResource("/org/example/gui/home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sceneHome.setRoot(test);

        // Vérifie les données
        // Ajoute le disque en BDD et ObservableList
    }
}
