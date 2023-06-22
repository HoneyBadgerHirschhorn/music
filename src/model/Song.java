package model;

public class Song {

    private int id;
    private int track;
    private String name;
    private int albumID;

    public Song(int id, int track, String name, int albumID) {
        this.id = id;
        this.track = track;
        this.name = name;
        this.albumID = albumID;
    }

    public Song() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
