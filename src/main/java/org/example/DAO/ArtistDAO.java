package org.example.DAO;

import org.example.Artist.Artist;

import java.sql.Connection;
import java.util.List;

public class ArtistDAO extends DAO{

    public ArtistDAO(Object con) {
        // Utilisation du constructeur par défault, sans paramètre de la classe parente DAO préparamétrée pour la BDD 'record'
        super();
    }


    public void Insert(Artist artist){

    }

    public void Update(Artist artist){

    }

    public void Delete(Artist artist){

    }

    public Artist Find(int artist_id){
        return null;
    }

    public List<Artist> List(){
        return null;
    }

    /**
     * Getter juste pour vérifier que la connexion a bien été paramétrée
      * @return Connection à la bdd record
     */
    public Connection getCon(){
        return this.con;
    }
}
