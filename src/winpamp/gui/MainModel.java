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
 * @author filip, Cecilia, Reza and Francesco
 */
public class MainModel {
    private ObservableList<Song> sopList; //List for the songs on Playlist
    private ObservableList<Song> songList; //List for all the songs
    private ObservableList<Playlist> playlistList; //List for all the playlists
    private static MainModel instance;
    private Song msong;
    private Playlist playlisttodelete;
    private int itemcounter;
    private MainModel()
            { //initialize all the empty lists, and 
             playlisttodelete = new Playlist("",0);
                itemcounter = 0;
               msong = new Song("","","","","",0);
            sopList = FXCollections.observableArrayList(dc.getPlaylistSongs("PlaylistRock")); //initialized with randomPlaylist
            songList = FXCollections.observableArrayList(dc.getAllSongs());
            playlistList = FXCollections.observableArrayList(dc.getAllPlSongs());
            
            }
    DalController dc = new DalController();
    
     public static MainModel GetInstance() //Needed so that we always work in the same instance of the MainModel.
    {
        if(instance == null)
        {
            instance = new MainModel();
        }
        return instance;
    }
    
     public ObservableList<Song> getsongs() //Method for getting a list of all the recorded songs.
     {
         return songList;
     }
     public ObservableList<Playlist> getplsongs() //Method for getting the songs in a certain playlist. IT gets the playlist from the selection in the controller.
     {
         return playlistList;
     }

     public static String ttoString(int timeInSeconds) //Calculate the time.
    {
        String result = "";
        int minutes = timeInSeconds/60;
        timeInSeconds %= 60;
        int seconds = timeInSeconds;
        if(minutes != 0)//provided there are any minutes in the time
        {
            if(minutes<10)//make sure that the minutes are listed as 2 digits.
            {
                result += "0";
            }
            result += minutes + ":";
        }
        
        else
        {
            result += "00:";
        }
        if(seconds<10)//Make sure the seconds are listed as 2 digits.
        {
            result += "0";
        }
        result += timeInSeconds;
        return result;
        
    }
     
     public void edit(Song song) //returns which song to edit.
     {
        msong = song;
     }
     public Song getESong() //returns the current song.
     {
         return msong;
     }
     public ObservableList<Song> getSopList(String pname) //Method for getting all the songs of a certain playlist.
     {
         sopList.clear(); //first clear the list.
         sopList = FXCollections.observableArrayList(dc.getPlaylistSongs(pname));//Fill the list based on the plauylist name.
         return sopList; //return the songs of the playlist specified.
     }
     public void setItemcounter(int number) //To count amount of songs on playlist.
     {
         this.itemcounter = number;
     }
     public int getItemcounter()//returnns the amoung of songs on the playlist.
     {
         return itemcounter;
     }
      public void moveSongUpOnPlaylist(Song song) //method for moving a song UP on the playlist.
    {
        int id = sopList.indexOf(song);
        if(id!=0)
        {
            Collections.swap(sopList, id, id-1);//Change the number on the playlist with -1.
         
        }
    }

    public void moveSongDownOnPlaylist(Song song) { //Method for moving the song DOWN on the playlist.
        int id = sopList.indexOf(song);
      
        
            Collections.swap(sopList, id, id+1); //Alter the number on the playslist by adding one.
      
    }
    
    public int test(Song song)//Method to find out what number the song is on playlist.
    {
        return sopList.indexOf(song);
    }
    public int getCounter(int gc)//Returns index of the songs on the playlist, +1, as we don't want it to start from 0.
    {
        itemcounter = gc + 1;
        return itemcounter;
    }
            
    public  void setPlaylistToDelete(Playlist playlist)//Method for selecting the playlist to delete.
    {
        playlisttodelete = playlist;
    }
    public Playlist getPlaylistToDelete()//MEthod for returning the playlist that is up for deletion.
    {
        return playlisttodelete;
    }
            
    public void removefromPlaylists()//Method to actually remove the playlist from the List for all playlists (playlisList).
    {
        playlistList.remove(getPlaylistToDelete());
}

    public void addPlaylist(String name) throws SQLException //Method to add a new playlist to the playlistList.
    {
        playlistList.add(dc.newPlaylist(name));//adds the playslist to the MAinModel list, and passes the information to our DAL.
    }
            
    
    
    
}