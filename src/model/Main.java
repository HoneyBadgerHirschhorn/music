package model;

import model.Artist;
import model.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSource();
        if (!dataSource.open()){
            System.out.println("can't open datasource");
            return;
        }
//        dataSource.queryAlbumByArtist();
        int count = dataSource.getCount(DataSource.Table_Artists);

//        List<Artist> artists = dataSource.queryArtists(DataSource.Order_By_None);
//        if (artists == null){
//            System.out.println("No artists");
//            return;
//        }
//        for (Artist artist : artists){
//            System.out.println("ID = "+artist.getId()+ " Name "+artist.getName());
//        }
        dataSource.close();

    }
}
