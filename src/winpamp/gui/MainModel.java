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
    private Song msong;
    private MainModel()
            {
               msong = new Song("","","","","",0);
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

     public static String ttoString(int timeInSeconds)
    {
        String result = "";
        int minutes = timeInSeconds/60;
        timeInSeconds %= 60;
        int seconds = timeInSeconds;
        if(minutes != 0)
        {
            if(minutes<10)
            {
                result += "0";
            }
            result += minutes + ":";
        }
        
        else
        {
            result += "00:";
        }
        if(seconds<10)
        {
            result += "0";
        }
        result += timeInSeconds;
        return result;
        
    }
     
     public void edit(Song song)
     {
        msong = song;
     }
     public Song getESong()
     {
         return msong;
     }
}
