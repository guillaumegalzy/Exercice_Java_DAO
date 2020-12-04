package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.DAO.DiscDAO;
import org.example.Disc.Disc;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    @FXML
    public TableView<Disc> tableau;
    @FXML
    public ObservableList<Disc> listDisc = FXCollections.observableArrayList();
    @FXML
    public Button btnAjouter,btnModifier,btnSupprimer;
    @FXML
    public TableColumn<Disc,String> col_Disc,col_Artist;
    public DiscDAO repodisc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Rend le tableau non éditable
            tableau.setEditable(false);

        // Peuplement du DiscDAO via la BD pour affichage dans le tableau
            this.repodisc = new DiscDAO();

            try {
                // Récupération du repo des disques
                listDisc.addAll(this.repodisc.List());

                // Lien entre les colonnes et les membres de la classe disc
                    col_Disc.setCellValueFactory(new PropertyValueFactory<>("disc_title"));
                    col_Artist.setCellValueFactory(new PropertyValueFactory<>("artist_name"));

                // Ajout des éléments du repo en tant que données du tableau
                tableau.setItems(listDisc);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public void btn_Click(ActionEvent actionEvent) throws SQLException {
        // Récupère l'ID du bouton dans une string
            String btnText = ((Button)actionEvent.getSource()).getId();

        //Action différente selon le boutton
            switch (btnText){
                // Ajout d'un disque à la base de données
                case "btnAjouter":
                    // Affiche le formulaire
                        afficheForm(actionEvent);

                    // Vérifie les données

                    // Ajoute le disque en BDD et ObservableList
                    repodisc.Insert(new Disc());

                    break;

                // Modification d'un disque existant
                case "btnModifier":
                    // Affiche le formulaire
                        afficheForm(actionEvent);

                    // Vérifie les données

                    // Modifie le disque en BDD et ObservableList
                        repodisc.Update(tableau.getSelectionModel().getSelectedItem());
                    break;

                // Suppression d'un disque à la base de données
                case "btnSupprimer":
                    // Récupération de l'ID du disque à supprimer
                    Disc disc = tableau.getSelectionModel().getSelectedItem();

                    // Le supprime de la BDD
                    this.repodisc.Delete(disc);

                    // Le supprime de l'observableList
                    listDisc.remove(disc);
                    break;

                default:break;
            }
    }

    /**
     * Changement de fenêtre
     * @param actionEvent
     */
    public void afficheForm(ActionEvent actionEvent){
        Scene sceneHome = ((Button) actionEvent.getSource()).getParent().getScene();
        Parent homeRoot = sceneHome.getRoot();
        try {
            homeRoot = FXMLLoader.load(getClass().getResource("/org/example/gui/formulaire.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sceneHome.setRoot(homeRoot);
    }
}
