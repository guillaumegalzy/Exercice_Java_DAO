package org.example.gui.Formulaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.Artist.Artist;
import org.example.DAO.ArtistDAO;
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

public class FormulaireControlleur implements Initializable {
    @FXML
    public Button btnAccept,btnCancel,btnLoad;
    @FXML
    public ImageView ImgDisc;
    @FXML
    public ComboBox<String> comboArtist;
    @FXML
    public TextField inputArtist,inputPrix,inputGenre,inputLabel,inputAnnee,inputTitre,inputID;
    @FXML
    public javafx.scene.layout.AnchorPane AnchorPane;

    public ObservableList<String> listArtist = FXCollections.observableArrayList();
    public ArtistDAO repoArtist = new ArtistDAO();
    public DiscDAO repoDisc = new DiscDAO();
    public List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur principal

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Récupération des artistes présents dans la BDD et ajout dans la combobox
            try {
                listArtist.add("Ajouter un nouvel artiste");
                for (Artist artist: repoArtist.List()) {
                    listArtist.add(artist.getArtist_name());
                }
                comboArtist.setItems(listArtist);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        // Remplissage des champs s'il s'agit d'une modification
            Image test = new Image("img/Fugazi.jpeg");
            ImgDisc.setImage(test);
    }

    /**
     * Fonction lancé par click sur le bouton 'Accepter'. L'action effectuée est fonction de la demande initiale, modifier ou ajouter un disque
     * @param actionEvent Action déclenché par appui sur le bouton 'Accepter'
     * @throws SQLException
     */
    public void btn_Click(ActionEvent actionEvent) throws SQLException {
        // Vérification des champs
        verify();

        // Initialisation de la variable qui contiendra le nom de l'artiste, égal au texte de la combobox par défaut
        String artistName = this.comboArtist.getSelectionModel().getSelectedItem();

        // Ajout de l'artiste si besoin et récupération de son nom pour l'ajouter au disque par la suite
        if (this.inputArtist.isVisible()){
            Artist newArtist = new Artist(repoArtist.LastID()+1,this.inputArtist.getText(),"");
            repoArtist.Insert(newArtist);
            artistName = repoArtist.FindByName(newArtist.getArtist_name()).getArtist_name();
        }

        // Création du nouveau disque à partir des champs renseignés
        Disc newDisc = new Disc(
                this.inputTitre.getText(),
                Integer.parseInt(this.inputAnnee.getText()),
                this.ImgDisc.getImage().getUrl(),
                this.inputLabel.getText(),
                this.inputGenre.getText(),
                Double.parseDouble(this.inputPrix.getText()),
                artistName
        ) ;

        // Opération en BDD
            switch (this.dataReceive.get(0).toString()){
                case "Ajout":
                    // Ajoute le disque en BDD
                        repoDisc.Insert(newDisc);

                    // Message de confirmation
                        System.out.println("Ajout effectué");
                    break;

                case "Modification":
                    Disc oldDisc = (Disc) dataReceive.get(1);
                    // Modifie l'ancien disque en BDD
                        repoDisc.Update(oldDisc,newDisc);

                    // Message de confirmation
                        System.out.println("Modification effectué");
                    break;

                default: break;
            }

        // Retour au menu
            retourHome(actionEvent);

    }

    /**
     * Changement de fenêtre - Retour à l'écran principal
     * @param actionEvent Action déclenché par appui sur les bouton 'Accepter' ou 'Annuler'
     */
    public void retourHome(ActionEvent actionEvent) {
        // Vide les données stockées
            this.dataReceive.clear();

        // Retour à l'écran principal
            Scene sceneHome = ((Button) actionEvent.getSource()).getParent().getScene();
            Parent homeRoot = sceneHome.getRoot();
            try {
                homeRoot = FXMLLoader.load(getClass().getResource("/org/example/gui/home.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sceneHome.setRoot(homeRoot);
    }

    /**
     * Fonction vérifiant la validé des champs du formulaire avant requête d'ajout ou de modification
     */
    public void verify(){
        System.out.println("vérification");

    }

    /**
     * Récupère les UserDatas stockées par le controlleur principal et lié à la stage principale
     */
    public void getData(){
        // Récupération de la stage principale à partir du tableau
            DataHolder holder = DataHolder.getDataStock();
            Data data = holder.getData();
            dataReceive.addAll(data.getDatas());
            System.out.println("data Receive : " + dataReceive);

        // S'il s'agit d'une modification
            if(this.dataReceive.get(0).equals("Modification")){
                // Remplissage des champs avec les informations du disque
                    Disc disc = (Disc) this.dataReceive.get(1);
                    this.inputID.setText(String.valueOf(disc.getDisc_id()));
                    this.inputTitre.setText(disc.getDisc_title());
                    this.inputAnnee.setText(String.valueOf(disc.getDisc_year()));
                    this.inputLabel.setText(disc.getDisc_title());
                    this.inputGenre.setText(disc.getDisc_genre());
                    this.inputPrix.setText(String.valueOf(disc.getDisc_price()));
                    this.ImgDisc.setImage(new Image("img/"+disc.getDisc_picture()));
                    this.comboArtist.getSelectionModel().select(disc.getArtist_name());
            }
    }

    /**
     * Active l'affichage de l'input pour la saisie d'un nouvel artiste sur sélection de l'option "Ajouter un nouvel artiste"
     */
    public void saisieManuelle() {
        this.inputArtist.setVisible(this.comboArtist.getSelectionModel().getSelectedItem().equals("Ajouter un nouvel artiste"));
    }
}