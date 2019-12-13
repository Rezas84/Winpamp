/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.bll;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import winpamp.be.Song;
import winpamp.dal.DalController;

/**
 *
 * @author filip, Cecilia, Reza and Francesco
 */
public class WinpampManager {
    
    public static WinpampManager wm = new WinpampManager();//When first created create a specific instance of itsself that all the other classes can use.
    
    
    private boolean booleanSong; //Create a boolean for whether we are editing or creating a song.
    
    private DalController dalController = new DalController(); //Create the dal controller

    
    
    
    //Method to pass the info from the gui to the dal, when editing a song.
    public Song EditSong(Song song, String songN, String songA, String songC, String songT, String songF) throws SQLException {
      return dalController.EditSong(song, songN, songA, songC, songT, songF);
    }
    
     //Method to pass the info from the gui to the dal, when creating a new song.
    public Song NewSong(String songN, String songA, String songC, String songT, String songF) throws SQLException {
        return (dalController.NewSong(songN, songA, songC, songT, songF));
    }
    
    
    //Method to alter the boolean.
    public void setSongBoolean (boolean songBoolean) {
        booleanSong = songBoolean;
    }
    
    
    //Method to get the boolean
    public boolean getSongBoolean () {
        return booleanSong;
    }
    
     @FXML
      public ObservableList<Song> search(ObservableList<Song> searchBase, String query) {
          
       ObservableList<Song> filtered = FXCollections.observableArrayList();

        if (query.isEmpty()) {
            return searchBase;
        }

        for (Song song : searchBase) {
            if (song.getName().toLowerCase().contains(query.toLowerCase()) || song.getArtist().toLowerCase().contains(query.toLowerCase()) ) 
            {
                filtered.add(song);
            }
        }

        return filtered;
    }
}