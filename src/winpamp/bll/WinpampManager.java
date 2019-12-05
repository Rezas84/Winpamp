/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.bll;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import winpamp.be.Song;
import winpamp.dal.DalController;

/**
 *
 * @author filip
 */
public class WinpampManager {
    
    public static WinpampManager wm = new WinpampManager();
    
    
    private boolean booleanSong;
    
    private DalController dalController = new DalController();

    
    
    
    
    public void EditSong(String songN, String songA, String songC, String songT, String songF) {
        dalController.EditSong(songN, songA, songC, songT, songF);
    }
    
    public Song NewSong(String songN, String songA, String songC, String songT, String songF) throws SQLException {
        //dalController.NewSong(songN, songA, songC, songT, songF);
        return (dalController.NewSong(songN, songA, songC, songT, songF));
    }
    
    public void setSongBoolean (boolean songBoolean) {
        booleanSong = songBoolean;
    }
    
    public boolean getSongBoolean () {
        return booleanSong;
    }
}