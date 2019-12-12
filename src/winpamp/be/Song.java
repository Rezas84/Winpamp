/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.be;

import winpamp.gui.MainModel;

/**
 *
 * @author filip, Cecilia, Reza and Francesco
 */
public class Song {
    private String Name;
    private String Artist;
    private String Category;
    private String Time;
    private String File;
    
    private int Id;
    private int Row;
    private MainModel model;
    
    //when creating a new Song, define all the characteristics.
    public Song(String name,String artist,String category,String time,String file,int id) {
        this.Name = name;
        this.Artist = artist;
        this.Category = category;
        this.Time = time;
        this.File = file;
        this.Id = id;
    }
    
    //Method to return the name of the song
     public String getName() {
        return Name;
    }

     //Method to set the name of the song.
    public void setName(String name) {
        this.Name = name;
    }
    
    //Method to get the id of the song.
    public int getId()
    {
        return Id;
    }
    
    //Method to set a row number when in a playlist.
    public void setRow(int row)
    {
        this.Row = row;
    }
    
    //Method to return the row of the song within a playlist.
    public int getRow()
    {
        return Row;
    }
    
    //Method to get the file location.
    public String getFile()
    {
        return File;
    }
    
    
    //Method to set the file location
    public void setFile(String file)
    {
        this.File = file;
    }

    //Method to set the artist.
    public void setArtist(String artist) {
        this.Artist = artist;
    }

    //Method to set the Category
    public void setCategory(String category) {
      this.Category = category;
    }

    //Method to set the duration time of the song
    public void setTime(String time) {
      this.Time = time;
    }
    
    //Method to get the duration time of the song
    public String getTime()
    {
        return Time;
    }
    
    //Method to get the category of the song
    public String getCategory()
    {
        return Category;
    }
    
//Method to get the artist of the song
    public String getArtist()
    {
        return Artist;
    }
    
    
    @Override
    public String toString() {
       model = MainModel.GetInstance();
        
        return  model.test(this)+ 1 + ". " + Name;
    }

}
