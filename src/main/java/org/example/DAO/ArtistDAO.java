package org.example.DAO;

import javafx.collections.FXCollections;
import org.example.Artist.Artist;

import java.sql.*;
import java.util.List;

public class ArtistDAO extends DAO{
    private List<Artist> repoArtist = FXCollections.observableArrayList();

    public ArtistDAO() {
        // Utilisation du constructeur par défault, sans paramètre de la classe parente DAO préparamétrée pour la BDD 'record'
        super();
    }

    /**
     * Ajoute un artiste à la BDD
     * @throws SQLException
     */
    public void Insert(Artist artist) throws SQLException {
        // Création de la requête d'ajout d'un artiste
            PreparedStatement insert = con.prepareStatement("INSERT INTO artist (artist_name, artist_url) VALUES (?,?)");
        
        // Définit les paramètres pour la requête préparée
            insert.setString(1, artist.getArtist_name());
            insert.setString(2, artist.getArtist_url());
        
        // Exécute la requête
            insert.execute();

    }

    public void Update(Artist artist){

    }

    public void Delete(Artist artist){

    }

    public Artist FindByID(int artist_id){
        return null;
    }

    public Artist FindByName(String artist_name) throws SQLException {
        // Création de la requête de recherche de l'ensemble des disques
            PreparedStatement getOneByName = con.prepareStatement("SELECT * from artist where artist_name=?");

        // Définit les paramètres pour la requête préparée
            getOneByName.setString(1, artist_name);

        // Exécute la requête et récupération du résultat
            ResultSet oneByName = getOneByName.executeQuery();

        // On se positionne sur le premier résultat
            oneByName.first();

        // Ferme la requête
            getOneByName.close();

        return new Artist(
                oneByName.getInt(1),
                oneByName.getString(2),
                oneByName.getString(3)
        );
    }

    /**
     * Récupère l'ensemble des artistes de la BDD, trié par nom d'artiste
     * @return une list d'artiste
     * @throws SQLException
     */
    public List<Artist> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des disques
            Statement getAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet allArtist = getAll.executeQuery("SELECT * FROM artist ORDER BY artist_name");

        while (allArtist.next()) {
            repoArtist.add(new Artist(
                    allArtist.getInt(1),
                    allArtist.getString(2),
                    allArtist.getString(3)
                    ));
        }
        // Ferme la requête
            getAll.close();

        return repoArtist;
    }

    /**
     * Permet de récupérer le dernier ID existant dans la table artist
     * @return (int) Dernier ID dans la table artist
     * @throws SQLException
     */
    public int LastID() throws SQLException {
        int lastID = 0;
        // Création de la requête de recherche du dernier ID d'artiste
            Statement getLastId = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet allDisc = getLastId.executeQuery("SELECT MAX (artist_id) from artist");

            allDisc.first();

            lastID = allDisc.getInt(1);

        // Ferme la requête
            getLastId.close();
        return lastID;
    }

    /**
     * Getter juste pour vérifier que la connexion a bien été paramétrée
      * @return Connection à la bdd record
     */
    public Connection getCon(){
        return this.con;
    }
}
