/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import winpamp.be.Playlist;
import winpamp.be.Song;
import winpamp.gui.MainModel;


/**
 *
 * @author filip, Cecilia, Reza and Francesco
 */
public class DalController {
     SQLServerDataSource ds; //The database we are using
      private MainModel model;
    public DalController()
    {
        //Setting up all the info we need to connect to the databse we have set up.
        ds = new SQLServerDataSource();
        ds.setDatabaseName("Winpamp");
        ds.setUser("CSe19B_6");
        ds.setPassword("CSe19B_6");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
    }
     
    //Method to edit a single song
    public Song EditSong(Song song, String name, String artist, String category, String time, String filelocation) throws SQLServerException, SQLException  {
        
    
        try(Connection con = ds.getConnection()){
            String sqlIf = "UPDATE ALLSONGS SET name = ?, artist = ?, category = ?, time = ?, Filelocation = ? WHERE id=?"; //SQL statement to alter the table, identifying the post through song ID.
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            //Set the ? values on the SQL statement
            pstmt.setString(1, "" + name + "");
            pstmt.setString(2, "" + artist + "");
            pstmt.setString(3, "" + category + "");
            pstmt.setString(4, "" + time + "");
            pstmt.setString(5, "" + filelocation + "");
            pstmt.setInt(6,song.getId());
            pstmt.execute(); //Execute the statement
        return new Song(name,artist,category,time,filelocation,song.getId());//Return the song so that we can also update our local instance of the business entity song
    }
    }
    //Method to add a new song
    public Song NewSong(String name, String artist, String category, String time, String filelocation) throws SQLException {
   
        try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLSONGS (Name, Artist, Category, Time, Filelocation) VALUES (?, ?, ?, ?, ?);"; //The SQL statement. We don't need an id, as the id is auto incremented.
            PreparedStatement pstmt = con.prepareStatement(sqlIf,Statement.RETURN_GENERATED_KEYS);//Prepare the statement, and make sure to get the generated key returned.
            //Add the actual info for the ? in the prepared statement.
            pstmt.setString(1, "" + name + "");
            pstmt.setString(2, "" + artist + "");
            pstmt.setString(3, "" + category + "");
            pstmt.setString(4, "" + time + "");
            pstmt.setString(5, "" + filelocation + "");
            pstmt.execute(); //Execute the statment.
            ResultSet rs = pstmt.getGeneratedKeys(); //Store the return form executing the statement.
            rs.next();
            int id = rs.getInt(1);//Store the id of the song, which we got returned from the statement, into an int.
            return new Song(name,artist,category,time,filelocation,id);//Return all the info of the song, so that we can store it in our local instance of the business entity Song.
        } 
    }
    
    //Method to fetch all songs from the Database.
     public List<Song> getAllSongs() 
    { 
        List<Song> songs = new ArrayList();//create a list to store all our songs
        try (Connection con = ds.getConnection()){
            String sqlStatement = "SELECT * FROM ALLSONGS"; //The SQL statement
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);//fetch the result of the SQL statement and store it.
            while(rs.next())//While there is something listed...
            {
                //Fetch all the relevant info
                String name = rs.getString("name");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String file = rs.getString("Filelocation");
                int id = rs.getInt("id");
                //Create a new instance of the busioness entity Song with the values.
                Song p = new Song(name,artist,category,time,file,id);
                songs.add(p);//Add the song to the list.
            }
            
        }
        catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return songs;//return the list of songs.
    }

    public void DeleteSong(Song song) throws SQLException {
       String sqlStatement = "DELETE FROM PLAYLIST_SONGS WHERE SongId=? ; DELETE FROM ALLSONGS WHERE id=?; ";//THe SQL statment to delete a song by it's id. We delete from the PLAYLIST_SONGS table first as this table depends on the id's from the ALLSONGS table.
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);
            //set the two ? to be the song ID in the statement.
            pstmt.setInt(1,song.getId());
            pstmt.setInt(2,song.getId());
            pstmt.execute();//execute the statement
        }
        
    }
    //Method to return all the songs connected to a certain playlist.
    public List<Song> getPlaylistSongs (String plname) {
       List<Song> plsongs = new ArrayList();//Create a list to store the songs in.    
         try (Connection con = ds.getConnection()){
        //SQL statement to find the playlist ID via the name in ALLPLAYLISTS table, then use that PlaylistID to find the associated songId in the PLAYLIST_SONGS table, only to find the songs with those id's in the ALLSONGS table.
        String sqlStatement = "SELECT * FROM ALLSONGS WHERE Id IN(Select SongId FROM PLAYLIST_SONGS WHERE PlaylistId IN(SELECT PlaylistId FROM ALLPLAYLISTS WHERE Name='"+plname+"')); "; 
         Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);//Execute the statment.
            while(rs.next())//WHile there info listed...
            {
               //Store all the relevant values.
                String name = rs.getString("name");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String file = rs.getString("Filelocation");
                int id = rs.getInt("id");
                Song p = new Song(name,artist,category,time,file,id);//create a new instance of a Song
                plsongs.add(p); //Add the song to our list.
            }
           
        }
          catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return plsongs;//return the list.
    }
    //Method to add a song to a playilst through playlistID.
    public void addSongToPlaylist (Song song, int playlistId) throws SQLException{
        String sqlStatement = "INSERT INTO PLAYLIST_SONGS (PlaylistId, SongId) VALUES(?, ?);";//SQL statment to add the PlaylistID and the SOngID to the PLAYLIST_SONGS relationship table.
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);//prepare the statement
            //Set the values for the SQL statments.
            pstmt.setString(1,"" + playlistId + "");
            pstmt.setInt(2,song.getId()); //OUr song has a method to fetch the ID.
            pstmt.execute();//execute the statement.
        } 
    }
    //KMethod to remmove a song from one playlist.
    public void removeSongFromPlaylist (Song song, int playlistId, int id) throws SQLException{
        String sqlStatement = "DELETE FROM PLAYLIST_SONGS WHERE PlaylistId=? AND SongId=?;";//SQL Statement to remove a song from a playlist. We need both values, as a song needs only to be removed from ONE playlsit at a time.
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);//prepare the statement.
            //Set the vallues needed for the SQL statment.
            pstmt.setString(1,"" + playlistId + "");
            pstmt.setInt(2,id);
            pstmt.execute();//execute the statemnt.
        } 
      
    }    
    //Method to fetch all the playlists.
     public List<Playlist> getAllPlSongs() 
    { 
        List<Playlist> playlists = new ArrayList();//create a list to store our playlists.
        try (Connection con = ds.getConnection()){
            String sqlStatement = "SELECT * FROM ALLPLAYLISTS";//SQL statment to fetch all the playlists created.
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next())//WHile there is info...
            {
                //Fetch the different values.
                String name = rs.getString("Name");
                int id = rs.getInt("PlaylistId");
                Playlist p = new Playlist(name,id,0);//Create a new instance of the business entity Playlist for now set the last row to 0,
                p.setRow(getNumberOfSongs(name));//channge the final row to include all the number of songs in the playlist.
                playlists.add(p);//Add to the list.
            }
            
        }
        catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playlists;//Return the list of playlists.
    }
     
     
     //Method to count the number of songs in a playlist, by using the playlist name.
      public int getNumberOfSongs (String plname) {
    //    List<Song> plsongsc = new ArrayList();    
         int counter = 0; //Initialize a counter.
         try (Connection con = ds.getConnection()){
             //SQL statement to find all the songs associated with a certain playlist, by first finding the id of the playlist, and then finding all occurences of that playlist ID wihtin PLAYLIST_SONGS.
        String sqlStatement = "SELECT * FROM ALLSONGS WHERE Id IN(Select SongId FROM PLAYLIST_SONGS WHERE PlaylistId IN(SELECT PlaylistId FROM ALLPLAYLISTS WHERE Name='"+plname+"')); "; 
         Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) //While there is info returned...
            {
               counter++;//add one to thee counter.
            }
           
        }
          catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return counter;//return the final count.
    }
     
     
     //Method to delete a playlist.
     public void deletePlaylist(Playlist playlist) throws SQLException
     {
          String sqlStatement = "DELETE FROM PLAYLIST_SONGS WHERE PlaylistId=? ; DELETE FROM ALLPLAYLISTS WHERE PlaylistId=? ;";//SQL Statment to delete the relationship to songs first in PLAYLIST_SONGS; and then remove the playlist from ALLPLAYLISTS.
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);//prepare the statment.
            //Add the correct id based on the business entity playlist.
            pstmt.setString(1,"" + playlist.GetId() + "");
            pstmt.setString(2,"" + playlist.GetId() + "");
            pstmt.execute();//execute the statement.
        } 
     }
     
     //MEthod to create a new playlist.
     public Playlist newPlaylist(String name) throws SQLException
     {     
       try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLPLAYLISTS (Name) VALUES (?);";//SQL statement to insert a name into the ALLPLAYLISTS table, we don't need id as this is ann auto incremented number.
            PreparedStatement pstmt = con.prepareStatement(sqlIf,Statement.RETURN_GENERATED_KEYS);//Prepare the statement and make sure we return the auto generated key.
            pstmt.setString(1, "" + name + "");//insert the actual name into the statement.
          
            pstmt.execute();//execute the statement.
            ResultSet rs = pstmt.getGeneratedKeys();//Store the returned result.
            rs.next();
            int id = rs.getInt(1);//Fetch the generated key as ´an int, and store it in id.
             return new Playlist(name,id,0);//return the Playlist with the values that this business entity needs.
        
                                               }
     
     }
     //Method to edit a playlist.
 public Playlist EditPlaylist(Playlist playlist, String name, int row) throws SQLServerException, SQLException  {
        
      
        
        try(Connection con = ds.getConnection()){
            String sqlIf = "UPDATE ALLPLAYLISTS SET name = ? WHERE PlaylistId=?"; //SQL Statement to update the playlist inside the ALLPLAYLISTS table.
            PreparedStatement pstmt = con.prepareStatement(sqlIf);//prepare the statement.
            pstmt.setString(1, "" + name + "");//insert the name into the statement.
            pstmt.setInt(2,playlist.GetId());//fetch the playlist ID from out corresponding business entity, and add it to the statment.
            pstmt.execute();//execute the statement.
        return new Playlist(name,playlist.GetId(),row);//return a new instance of Playlist.
    }
    }
 
}
