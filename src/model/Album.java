package model;

public class Album {

    private int id;
    private String name;
    private int artistID;

    public Album(int id, String name, int artistID) {
        this.id = id;
        this.name = name;
        this.artistID = artistID;
    }

    public Album() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }
}
