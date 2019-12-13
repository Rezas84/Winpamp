/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.be;

/**
 *
 * @author filip, Cecilia, Reza and Francesco
 */
public class Playlist {
    private String Name;
    private int Id;
    private int Row;
    public Playlist(String name,int id,int row)//When creating a new playlist, set the relevant data.
    {
        this.Name = name;
        this.Id = id;
        this.Row = row;
    }
    
    //Method to get the name of the playlist
    public String getName()
    {
        return Name;
    }
    
    //Method to get the id of the playlist
    public int GetId()
    {
        return Id;
    }
    
    //Method to set the name of the playlist
     public void setName(String name) {
        this.Name = name;
    }
     
    //Method to get the number of songs in the playlist
     public void setRow(int row)
     {
         this.Row = row;
     }
     
     //Method to get the number of songs in the playlist
     public int getRow()
     {
         return Row;
     }
}
