package org.example.DAO;

import javafx.collections.FXCollections;
import org.example.Artist.Artist;
import org.example.Disc.Disc;
import java.sql.*;
import java.util.List;

public class DiscDAO extends DAO{
    private final List<Disc> repoDisc = FXCollections.observableArrayList();

    public DiscDAO() {
        // Utilisation du constructeur par défault, sans paramètre de la classe parente DAO préparamétrée pour la BDD 'record'
        super();
    }

    public void Insert(Disc disc) throws SQLException {
        // Création de la requête d'ajout d'un disque
            PreparedStatement insert = con.prepareStatement("INSERT INTO disc (disc_title, disc_year, disc_picture, disc_label, disc_genre, disc_price, artist_id) VALUES (?,?,?,?,?,?,?)");

        // Récupération de l'ID de l'artiste à partir de son nom
            ArtistDAO artistDAO = new ArtistDAO();
            Artist artist = artistDAO.FindByName(disc.getArtist_name());

        // Définit les paramètres pour la requête préparée
            insert.setString(1, disc.getDisc_title());
            insert.setInt(2, disc.getDisc_year());
            insert.setString(3, disc.getDisc_picture().substring(disc.getDisc_picture().lastIndexOf("/")+1).replaceAll("%20"," "));
            insert.setString(4, disc.getDisc_label());
            insert.setString(5, disc.getDisc_genre());
            insert.setDouble(6, disc.getDisc_price());
            insert.setInt(7, artist.getArtist_id());

        // Exécute la requête d'ajout
            insert.execute();
    }

    public void Update(Disc oldDisc, Disc newDisc) throws SQLException {
        // Création de la requête de màj d'un disque
            PreparedStatement maj = con.prepareStatement("UPDATE disc SET disc_title=?,disc_year=?,disc_picture=?,disc_label=?,disc_genre=?,disc_price=?,artist_id=? WHERE disc_id =?");

        // Récupération de l'ID de l'artiste à partir de son nom
            ArtistDAO artistDAO = new ArtistDAO();
            Artist artist = artistDAO.FindByName(newDisc.getArtist_name());

        // Définit les paramètres pour la requête préparée
            maj.setString(1, newDisc.getDisc_title());
            maj.setInt(2, newDisc.getDisc_year());
            maj.setString(3, newDisc.getDisc_picture().substring(newDisc.getDisc_picture().lastIndexOf("/")+1).replaceAll("%20"," "));
            maj.setString(4, newDisc.getDisc_label());
            maj.setString(5, newDisc.getDisc_genre());
            maj.setDouble(6, newDisc.getDisc_price());
            maj.setInt(7, artist.getArtist_id());
            maj.setInt(8, oldDisc.getDisc_id());

        // Exécute la requête
            maj.execute();
    }

    public void Delete(Disc disc) throws SQLException {
        // Création de la requête de recherche de l'ensemble des disques
        PreparedStatement delOne = con.prepareStatement("DELETE from disc WHERE disc_id =?");

        // Définit le critère de recherche pour la requête préparée
        delOne.setInt(1, disc.getDisc_id());

        // Exécute la requête
        delOne.execute();
    }

    public Disc Find(int disc_id) throws SQLException {
        // Création de la requête préparée de recherche du disque
            PreparedStatement stmtSearch = con.prepareStatement("SELECT disc.*,artist_name FROM disc join artist ON disc.artist_id = artist.artist_id WHERE disc_id=?");

        // Définit le critère de recherche pour la requête préparée
            stmtSearch.setInt(1, disc_id);

        // Exécute la requête et récupération du résultat
            ResultSet result = stmtSearch.executeQuery();

        // On se positionne au premier emplacement du ResultSet
            result.first();

        // Création du disque en utilisant le constructeur spécifique renseignant l'ensemble des membres
        return new Disc(
                result.getInt(1),
                result.getString(2),
                result.getInt(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
                result.getDouble(7),
                result.getInt(8),
                result.getString(9)
                );
    }

    /**
     * Récupère l'ensemble des disques de la BDD, trié par id croissant
     * @return une list de disque
     * @throws SQLException
     */
    public List<Disc> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des disques
            Statement getAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet allDisc = getAll.executeQuery("SELECT disc.*,artist.artist_name FROM disc LEFT join artist ON disc.artist_id = artist.artist_id ORDER BY disc_id");

        while(allDisc.next()){
            repoDisc.add(new Disc(
                    allDisc.getInt(1),
                    allDisc.getString(2),
                    allDisc.getInt(3),
                    allDisc.getString(4),
                    allDisc.getString(5),
                    allDisc.getString(6),
                    allDisc.getDouble(7),
                    allDisc.getInt(8),
                    allDisc.getString(9)
            ));
        }
        return repoDisc;
    }

    /**
     * Getter juste pour vérifier que la connexion a bien été paramétrée
     * @return Connection à la bdd record
     */
    public Connection getCon(){
        return this.con;
    }

}