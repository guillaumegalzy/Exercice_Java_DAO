package org.example.Artist;

public class Artist {
    private int artist_id;
    private String artist_name;
    private String artist_url;

    public Artist(String artist_name, String artist_url) {
    }

    public Artist(int artist_id, String artist_name, String artist_url) {
        this.artist_id = artist_id;
        this.artist_name = artist_name;
        this.artist_url = artist_url;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_url() {
        return artist_url;
    }

    public void setArtist_url(String artist_url) {
        this.artist_url = artist_url;
    }
}