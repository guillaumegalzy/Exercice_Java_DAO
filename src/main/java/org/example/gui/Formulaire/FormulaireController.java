package org.example.gui.Formulaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.Artist.Artist;
import org.example.DAO.ArtistDAO;
import org.example.DAO.DiscDAO;
import org.example.Disc.Disc;
import org.example.Service.SceneHandler;
import org.example.gui.HomeController;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormulaireController implements Initializable {
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
        // Vide les précédentes données récupérées
            dataReceive.clear();

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

        // Récupération des données stockées par le controlleur principal
            getData();

        // Ajout gestionnaire d'écoute sur la combobox, affiche l'inputArtist si la valeur sélectionnée est "Ajouter un nouvel artiste"
            this.comboArtist.valueProperty().addListener((observable, oldValue, newValue) ->
                    inputArtist.setVisible(newValue.equals("Ajouter un nouvel artiste")));

        // Ajout gestionnaire d'écoute sur l'image, pour permettre sa modification
            if(dataReceive.get(0).equals("Modification")) {
                ImgDisc.setOnMouseClicked(event -> {
                    // Création et paramétrage d'un filechooser
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Sélectionner une image");
                        fileChooser.getExtensionFilters().addAll( //Extensions autorisées
                                new FileChooser.ExtensionFilter("Image (.jpg, .jpeg, .png)", "*.jpeg", "*.jpg", "*.png")
                        );

                    //Chemin d'accès au fichier de resources du projet pour paramétrage du chemin d'accès par défaut
                        String directory = System.getProperty("user.dir");
                        Path path = Paths.get(directory + "/src/main/resources/img");
                        fileChooser.setInitialDirectory(new File(String.valueOf(path)));

                    // Ouverture de la fenêtre de dialogue et récupération de l'image choisie
                        File selectedFile = fileChooser.showOpenDialog(ImgDisc.getScene().getWindow());

                    // Modification de l'image prévisualisée
                        Image newImage = new Image("/img/" + selectedFile.getName());
                        ImgDisc.setImage(newImage);
                });
            }
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
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Validation");
                        alert.setContentText("L'ajout a bien \u00E9t\u00E9 effectu\u00E9");
                        alert.showAndWait();

                    break;

                case "Modification":
                    Disc oldDisc = (Disc) dataReceive.get(1);
                    // Modifie l'ancien disque en BDD
                        repoDisc.Update(oldDisc,newDisc);

                    // Message de confirmation
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Modification effectu\u00E9");
                        alert.setContentText("La modification a bien \u00E9t\u00E9 effectu\u00E9e");
                        alert.showAndWait();

                    break;

                default: break;
            }

        // Retour au menu
            GoToHome(actionEvent);
    }

    /**
     * Changement de scene - Retour à l'écran principal
     * @param actionEvent Action déclenché par appui sur les bouton 'Accepter' ou 'Annuler'
     */
    public void GoToHome(ActionEvent actionEvent) {
        // Retour à l'écran principal via le service de changement de scene
            SceneHandler sceneHandler = new SceneHandler();
            sceneHandler.setScene(actionEvent, "home");
    }

    /**
     * Fonction vérifiant la validé des champs du formulaire avant requête d'ajout ou de modification
     */
    public void verify(){

    }

    /**
     * Récupère les datas stockées par le controlleur principal
     */
    public void getData(){
        // Récupération de la stage principale à partir du tableau
            dataReceive.addAll(HomeController.dataSend);

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
}