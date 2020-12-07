package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.DAO.DiscDAO;
import org.example.Disc.Disc;
import org.example.Service.SceneHandler;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    public javafx.scene.layout.AnchorPane AnchorPane;

    public DiscDAO repodisc = new DiscDAO();
    public static List<Object> dataSend = new ArrayList<>(); // Données qui seront stockées par ce controlleur et utilisé par le controlleur du formulaire

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vide les données envoyées
            dataSend.clear();

        // Rend le tableau non éditable
            tableau.setEditable(false);

        // Peuplement du DiscDAO via la BD pour affichage dans le tableau
            try {
                // Récupération du repo des disques
                listDisc.addAll(this.repodisc.List());

                // Lien entre les colonnes et les membres de la classe disc
                    col_Disc.setCellValueFactory(new PropertyValueFactory<>("disc_title"));
                    col_Artist.setCellValueFactory(new PropertyValueFactory<>("artist_name"));

                // Ajout les éléments du repo en tant que données du tableau
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
                    // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                        dataSend.add("Ajout"); //Stockage de l'opération demandée
                        GoToForm(actionEvent);
                    break;

                // Modification d'un disque existant
                case "btnModifier":
                    // Informe le controlleur du formulaire que l'on veut effectuer une modification
                        dataSend.add("Modification"); //Stockage de l'opération demandée
                        dataSend.add(tableau.getSelectionModel().getSelectedItem()); //Stockage du disque concerné
                        GoToForm(actionEvent);
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
     * Change de scène - Passe au formulaire
     * @param actionEvent Evemement déclenchant l'action
     */
    private void GoToForm(ActionEvent actionEvent){
        // Retour à l'écran principal via le service de changement de scene
            SceneHandler sceneHandler = new SceneHandler();
            sceneHandler.setScene(actionEvent, "Formulaire/formulaire");
    }
}