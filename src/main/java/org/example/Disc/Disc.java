package org.example.Disc;

public class Disc {
    private int disc_id;
    private String disc_title;
    private int disc_year;
    private String disc_picture;
    private String disc_label;
    private String disc_genre;
    private int disc_price;
    private int artist_id;
    private String artist_name;

    public Disc() {
    }

    public Disc(int disc_id, String disc_title, int disc_year, String disc_picture, String disc_label, String disc_genre, int disc_price, int artist_id, String artist_name) {
        this.disc_id = disc_id;
        this.disc_title = disc_title;
        this.disc_year = disc_year;
        this.disc_picture = disc_picture;
        this.disc_label = disc_label;
        this.disc_genre = disc_genre;
        this.disc_price = disc_price;
        this.artist_id = artist_id;
        this.artist_name = artist_name;
    }

    public Disc(int disc_id, String disc_title, int disc_year, String disc_picture, String disc_label, String disc_genre, int disc_price, int artist_id) {
        this.disc_id = disc_id;
        this.disc_title = disc_title;
        this.disc_year = disc_year;
        this.disc_picture = disc_picture;
        this.disc_label = disc_label;
        this.disc_genre = disc_genre;
        this.disc_price = disc_price;
        this.artist_id = artist_id;
    }

    public Disc(int disc_id, String disc_title, int disc_year, String disc_picture, String disc_label, String disc_genre, int disc_price, String artist_name) {
        this.disc_id = disc_id;
        this.disc_title = disc_title;
        this.disc_year = disc_year;
        this.disc_picture = disc_picture;
        this.disc_label = disc_label;
        this.disc_genre = disc_genre;
        this.disc_price = disc_price;
        this.artist_name = artist_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public int getDisc_id() {
        return disc_id;
    }

    public void setDisc_id(int disc_id) {
        this.disc_id = disc_id;
    }

    public String getDisc_title() {
        return disc_title;
    }

    public void setDisc_title(String disc_title) {
        this.disc_title = disc_title;
    }

    public int getDisc_year() {
        return disc_year;
    }

    public void setDisc_year(int disc_year) {
        this.disc_year = disc_year;
    }

    public String getDisc_picture() {
        return disc_picture;
    }

    public void setDisc_picture(String disc_picture) {
        this.disc_picture = disc_picture;
    }

    public String getDisc_label() {
        return disc_label;
    }

    public void setDisc_label(String disc_label) {
        this.disc_label = disc_label;
    }

    public String getDisc_genre() {
        return disc_genre;
    }

    public void setDisc_genre(String disc_genre) {
        this.disc_genre = disc_genre;
    }

    public int getDisc_price() {
        return disc_price;
    }

    public void setDisc_price(int disc_price) {
        this.disc_price = disc_price;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }
}
