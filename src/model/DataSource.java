package model;

import com.sun.source.tree.TryTree;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DataSource {

    public static final String DB_Name = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:Y:\\\\SQLITE\\\\tools\\\\" + DB_Name;

    public static final String Table_Albums = "albums";
    public static final String Column_Album_ID = "_id";
    public static final String Column_Album_name = "name";
    public static final String Column_Album_Artist = "artist";
    public static final int Idex_Album_ID = 1;
    public static final int Index_Album_Name = 2;
    public static final int Index_album_Artist =3;

    public static final String Table_Artists = "artists";
    public static final String Column_Artist_ID = "_id";
    public static final String Column_Artist_Name = "name";
    public static final int Index_Artist_ID = 1;
    public static final int Index_Artist_Name = 2;

    public static final String Table_Songs = "songs";
    public static final String Column_Song_ID = "track";
    public static final String Column_Song_Track = "track";
    public static final String Column_Song_Title = "title";
    public static final String Column_Song_Album = "album";
    public static final int Index_Song_ID = 1;
    public static final int Index_Song_Track = 2;
    public static final int Index_Song_Title = 3;
    public static final int Index_Song_Album = 4;

    public static final int Order_By_None = 1;
    public static final int Order_by_ASC = 2;
    public static final int Order_by_DESC = 3;

    public static final String nameConflict1 = "cow";

    private Connection conn;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

        } catch (SQLException e) {
            System.out.println("Connection not opened" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection not closed! " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public List<Artist> queryArtists(int sortOrder) {
//        Statement statement = null;
//        ResultSet results = null;
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(Table_Artists);
        if (sortOrder != Order_By_None) {
            sb.append(" ORDER BY ");
            sb.append(Column_Artist_Name);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == Order_by_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
             ResultSetMetaData meta = results.getMetaData();
             String name = meta.getColumnName(1);


            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist();
                artist.setId(results.getInt(Column_Artist_ID));
                artist.setName(results.getString(Column_Artist_Name));
                artists.add(artist);
            }
            close();
            return artists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int getCount(String table){
        String sql = "SELECT COUNT (*) AS count, MIN(_id) AS min_id FROM "+table;
        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            int count = resultSet.getInt("count");
            int min = resultSet.getInt("min_id");
            System.out.format("Count = %d, min = %d\n", count, min);
            return count;

        }
        catch (SQLException e){
            System.out.println("Meh "+e.getMessage());
            e.printStackTrace();
            return  -1;
        }
    }

    public List<Album> queryAlbumByArtist(){
        //Select albums.name, artists.name FROM albums INNER JOIN artists on albums.artist = artists._id ORDER BY

        StringBuilder sb = new StringBuilder(" SELECT ");
        sb.append(Table_Albums);
        sb.append(".");
        sb.append(Column_Album_name);
        sb.append(",");
        sb.append(Table_Artists);
        sb.append(".");
        sb.append(Column_Artist_Name);
        sb.append(" AS "+nameConflict1+" FROM ");
        sb.append(Table_Albums);
        sb.append(" INNER JOIN ");
        sb.append(Table_Artists);
        sb.append(" ON ");
        sb.append(Table_Albums);
        sb.append(".");
        sb.append(Column_Album_Artist);
        sb.append(" = ");
        sb.append(Table_Artists);
        sb.append(".");
        sb.append(Column_Artist_ID);
        sb.append(" ORDER BY ");
        sb.append(Table_Artists);
        sb.append(".");
        sb.append(Column_Artist_Name);
        sb.append(" COLLATE NOCASE ASC");

        // artists.name COLLATE NOCASE ASC;



//    " INNER JOIN "+Table_Artists +" on "+
//                   Table_Albums+"."+Colum_Album_Artist+" = "+Table_Artists+Column_Artist_ID+
//        "          ORDER BY " +Column_Album_name+" COLLATE NOCASE ASC; ");
//           sb.append(" Order BY "+Column_Artist_Name+" DESC;");
        if (conn == null){
            open();
        }
           try (Statement statement = conn.createStatement();
               ResultSet results = statement.executeQuery(sb.toString())){
               List<Artist> artistList = new ArrayList<>();
               List<Album> albumList = new ArrayList<>();
               while (results.next()){
                   Artist artistObject = new Artist();
                   artistObject.setName(results.getString(nameConflict1));
                   artistList.add(artistObject);
               }
               results.close();
               statement.close();

           try (Statement statement2 = conn.createStatement();
                ResultSet results2 = statement.executeQuery(sb.toString())){
               while (results2.next()) {
                   Album albumObject = new Album();
                   albumObject.setName(results2.getString(Column_Album_name));
                   albumList.add(albumObject);
               }
               results2.close();
               conn.close();

           }
           catch (SQLException e) {
               System.out.println("Query failed " + e.getMessage());
               e.printStackTrace();
               return null;
           }

           for (int i = 0; i < artistList.size(); i ++) {
                System.out.println("Artist "+artistList.get(i).getName() + "  Album   "+albumList.get(i).getName() );
               }

               return albumList;
           }
           catch (SQLException e){
               System.out.println("Something went wrong "+e.getMessage());
               e.printStackTrace();
               return null;
           }

    }

//        } finally {
//            try {
//                if (results != null) {
//                    results.close();
//                }
//            }
//            catch(SQLException e){
//                System.out.println("Could not close results "+e.getMessage());
//                e.printStackTrace();
//
//            }
//            try{
//                if (statement != null){
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Statement is not closed "+e.getMessage());
//                e.printStackTrace();
//            }
//        }





}
