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
 * @author filip
 */
public class DalController {
     SQLServerDataSource ds;
      private MainModel model;
    public DalController()
    {
        
        ds = new SQLServerDataSource();
        ds.setDatabaseName("Winpamp");
        ds.setUser("CSe19B_6");
        ds.setPassword("CSe19B_6");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
    }
     

    public Song EditSong(Song song, String name, String artist, String category, String time, String filelocation) throws SQLServerException, SQLException  {
        
       System.out.println("EditSong working");
        
        try(Connection con = ds.getConnection()){
            String sqlIf = "UPDATE ALLSONGS SET name = ?, artist = ?, category = ?, time = ?, Filelocation = ? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            pstmt.setString(1, "" + name + "");
            pstmt.setString(2, "" + artist + "");
            pstmt.setString(3, "" + category + "");
            pstmt.setString(4, "" + time + "");
            pstmt.setString(5, "" + filelocation + "");
            pstmt.setInt(6,song.getId());
            pstmt.execute();
        return new Song(name,artist,category,time,filelocation,song.getId());
    }
    }
    
    public Song NewSong(String name, String artist, String category, String time, String filelocation) throws SQLException {
   
        try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLSONGS (Name, Artist, Category, Time, Filelocation) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sqlIf,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "" + name + "");
            pstmt.setString(2, "" + artist + "");
            pstmt.setString(3, "" + category + "");
            pstmt.setString(4, "" + time + "");
            pstmt.setString(5, "" + filelocation + "");
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new Song(name,artist,category,time,filelocation,id);
        } 
    }
    
     public List<Song> getAllSongs() 
    { 
        List<Song> songs = new ArrayList();
        try (Connection con = ds.getConnection()){
            String sqlStatement = "SELECT * FROM ALLSONGS";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next())
            {
                String name = rs.getString("name");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String file = rs.getString("Filelocation");
                int id = rs.getInt("id");
                Song p = new Song(name,artist,category,time,file,id);
                songs.add(p);
            }
            
        }
        catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return songs;
    }

    public void DeleteSong(Song song) throws SQLException {
       String sqlStatement = "alter table PLAYLIST_SONGS nocheck constraint all; DELETE FROM ALLSONGS WHERE id=?; alter table PLAYLIST_SONGS check constraint all;";
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);
        
            pstmt.setInt(1,song.getId());
            pstmt.execute();
        }
        
    }
    
    public List<Song> getPlaylistSongs (String plname) {
       List<Song> plsongs = new ArrayList();    
         try (Connection con = ds.getConnection()){
        String sqlStatement = "SELECT * FROM ALLSONGS WHERE Id IN(Select SongId FROM PLAYLIST_SONGS WHERE PlaylistId IN(SELECT PlaylistId FROM ALLPLAYLISTS WHERE Name='"+plname+"')); "; 
         Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next())
            {
               
                String name = rs.getString("name");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                String time = rs.getString("time");
                String file = rs.getString("Filelocation");
                int id = rs.getInt("id");
                Song p = new Song(name,artist,category,time,file,id);
                //p.setRow(rs.getRow());
                plsongs.add(p);
            }
           
        }
          catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return plsongs;
    }
    
    public void addSongToPlaylist (Song song, int playlistId) throws SQLException{
        String sqlStatement = "INSERT INTO PLAYLIST_SONGS (PlaylistId, SongId) VALUES(?, ?);";
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);
        
            pstmt.setString(1,"" + playlistId + "");
            pstmt.setInt(2,song.getId());
            pstmt.execute();
        } 
    }
    
    public void removeSongFromPlaylist (Song song, int playlistId, int id) throws SQLException{
        String sqlStatement = "DELETE FROM PLAYLIST_SONGS WHERE PlaylistId=? AND SongId=?;";
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);
          
            pstmt.setString(1,"" + playlistId + "");
            pstmt.setInt(2,id);
            pstmt.execute();
        } 
      
    }    
    
     public List<Playlist> getAllPlSongs() 
    { 
        List<Playlist> playlists = new ArrayList();
        try (Connection con = ds.getConnection()){
            String sqlStatement = "SELECT * FROM ALLPLAYLISTS";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next())
            {
                String name = rs.getString("Name");
                int id = rs.getInt("PlaylistId");
                Playlist p = new Playlist(name,id);
                p.setRow(getNumberOfSongs(name));
                playlists.add(p);
            }
            
        }
        catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playlists;
    }
     
     
     
      public int getNumberOfSongs (String plname) {
    //    List<Song> plsongsc = new ArrayList();    
         int counter = 0;
         try (Connection con = ds.getConnection()){
        String sqlStatement = "SELECT * FROM ALLSONGS WHERE Id IN(Select SongId FROM PLAYLIST_SONGS WHERE PlaylistId IN(SELECT PlaylistId FROM ALLPLAYLISTS WHERE Name='"+plname+"')); "; 
         Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next())
            {
               counter++;
            }
           
        }
          catch (SQLServerException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return counter;
    }
     
     
     
     public void deletePlaylist(Playlist playlist) throws SQLException
     {
          String sqlStatement = "alter table PLAYLIST_SONGS nocheck constraint all; DELETE FROM ALLPLAYLISTS WHERE PlaylistId=? ;alter table PLAYLIST_SONGS check constraint all; ";
               
               
        try (Connection con = ds.getConnection()){
          PreparedStatement pstmt = con.prepareStatement(sqlStatement);
          
            pstmt.setString(1,"" + playlist.GetId() + "");
            pstmt.execute();
        } 
     }
     
     
     public Playlist newPlaylist(String name) throws SQLException
     {     
       try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLPLAYLISTS (Name) VALUES (?);";
            PreparedStatement pstmt = con.prepareStatement(sqlIf,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "" + name + "");
          
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
             return new Playlist(name,id);
        
                                               }
     
     }
     
}