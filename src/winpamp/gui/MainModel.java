/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import java.sql.SQLException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import winpamp.be.Playlist;
import winpamp.be.Song;
import winpamp.dal.DalController;

/**
 *
 * @author filip
 */
public class MainModel {
    private ObservableList<Song> sopList;
    private ObservableList<Song> songList;
    private ObservableList<Playlist> playlistList;
    private static MainModel instance;
    private Song msong;
    private Playlist playlisttodelete;
    private int itemcounter;
    private MainModel()
            { 
             playlisttodelete = new Playlist("",0);
                itemcounter = 0;
               msong = new Song("","","","","",0);
            sopList = FXCollections.observableArrayList(dc.getPlaylistSongs("PlaylistRock"));
            songList = FXCollections.observableArrayList(dc.getAllSongs());
            playlistList = FXCollections.observableArrayList(dc.getAllPlSongs());
            
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
     public ObservableList<Playlist> getplsongs()
     {
         return playlistList;
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
     public ObservableList<Song> getSopList(String pname)
     {
         sopList.clear();
         sopList = FXCollections.observableArrayList(dc.getPlaylistSongs(pname));
         return sopList;
     }
     public void setItemcounter(int number)
     {
         this.itemcounter = number;
     }
     public int getItemcounter()
     {
         return itemcounter;
     }
      public void moveSongUpOnPlaylist(Song song)
    {
        int id = sopList.indexOf(song);
        if(id!=0)
        {
            Collections.swap(sopList, id, id-1);
         
        }
    }

    public void moveSongDownOnPlaylist(Song song) {
        int id = sopList.indexOf(song);
      
        
            Collections.swap(sopList, id, id+1);
      
    }
    
    public int test(Song song)
    {
        return sopList.indexOf(song);
    }
    public int getCounter(int gc)
    {
        itemcounter = gc + 1;
        return itemcounter;
    }
            
    public  void setPlaylistToDelete(Playlist playlist)
    {
        playlisttodelete = playlist;
    }
    public Playlist getPlaylistToDelete()
    {
        return playlisttodelete;
    }
            
    public void removefromPlaylists()
    {
        playlistList.remove(getPlaylistToDelete());
}

    public void addPlaylist(String name) throws SQLException
    {
        playlistList.add(dc.newPlaylist(name));
    }
            
    
    
    
}