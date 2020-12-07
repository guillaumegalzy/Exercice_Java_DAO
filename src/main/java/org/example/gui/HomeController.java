package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.DAO.DiscDAO;
import org.example.Data.Data;
import org.example.Data.DataHolder;
import org.example.Disc.Disc;
import java.io.IOException;
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
    public List<Object> dataSend = new ArrayList<>(); // Données qui seront transmises par ce controlleur au controlleur du formulaire

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Rend le tableau non éditable
            tableau.setEditable(false);

        // Peuplement du DiscDAO via la BD pour affichage dans le tableau
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
                    // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                        this.dataSend.add("Ajout"); //Stockage de l'opération demandée
                        sendData(actionEvent,dataSend);
                    break;

                // Modification d'un disque existant
                case "btnModifier":
                    // Informe le controlleur du formulaire que l'on veut effectuer une modification
                        this.dataSend.add("Modification"); //Stockage de l'opération demandée
                        this.dataSend.add(tableau.getSelectionModel().getSelectedItem()); //Stockage du disque concerné
                        sendData(actionEvent,dataSend);
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
     * Stocke une variable UserData qui pourra être récupérée par le controlleur du formulaire pour savoir comment gérer la demande de l'utilisateur
     * @param dataSend Liste comprennant les paramètres sous forme d'Objetc. Ces paramètres seront utilisés par le formulaire
     */
    private void sendData(ActionEvent actionEvent,List<Object> dataSend){
        // Step 1
        Data data = new Data();
        data.setDatas(dataSend);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/gui/Formulaire/formulaire.fxml"));
            // Step 2
            DataHolder holder = DataHolder.getDataStock();
            // Step 3
            holder.setData(data);
            // Step 4
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.printf("Error: %s%n", e.getMessage());
        }
        System.out.println("Données stockées " + data.getDatas());
    }
}