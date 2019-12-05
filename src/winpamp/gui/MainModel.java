/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import winpamp.be.Song;
import winpamp.dal.DalController;

/**
 *
 * @author filip
 */
public class MainModel {
    private ObservableList<Song> songList;
    private static MainModel instance;
    private MainModel()
            {
            songList = FXCollections.observableArrayList(dc.getAllSongs());
            }
    DalController dc = new DalController();
    
     public static MainModel GetInstance()
    {
        if(instance == null)
        {
            instance = new MainModel();
        }
        return instance;
    }
    
     public ObservableList<Song> getsongs()
     {
         return songList;
     }
     
     
     
}
