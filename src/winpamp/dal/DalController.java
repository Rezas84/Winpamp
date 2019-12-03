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
import winpamp.be.Song;


/**
 *
 * @author filip
 */
public class DalController {
     SQLServerDataSource ds;
    public DalController()
    {
        ds = new SQLServerDataSource();
        ds.setDatabaseName("Winpamp");
        ds.setUser("CSe19B_6");
        ds.setPassword("CSe19B_6");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
    }


    public void EditSong(String name, String artist, String category, String time, String file) {
        
       
        
        try(Connection con = ds.getConnection()){
            String sqlIf = "UPDATE FROM ALLSONGS SET name = ?, artist = ?, category = ?, time = ? WHERE file = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            pstmt.setString(1, name);
            pstmt.setString(2, artist);
            pstmt.setString(3, category);
            pstmt.setString(4, time);
            pstmt.setString(5, file);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void NewSong(String name, String artist, String category, String time, String file) {
        
   
        try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLSONGS (name, artist, category, time, file) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            pstmt.setString(1, name);
            pstmt.setString(2, artist);
            pstmt.setString(3, category);
            pstmt.setString(4, time);
            pstmt.setString(5, file);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
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
                Song p = new Song(name,artist,category,time);
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
    
    
    
    
    
}
