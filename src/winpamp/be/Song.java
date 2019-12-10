/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.be;

import winpamp.gui.MainModel;

/**
 *
 * @author filip
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
    public Song(String name,String artist,String category,String time,String file,int id) {
        this.Name = name;
        this.Artist = artist;
        this.Category = category;
        this.Time = time;
        this.File = file;
        this.Id = id;
    }
     public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public int getId()
    {
        return Id;
    }
    public void setRow(int row)
    {
        this.Row = row;
    }
    public int getRow()
    {
        return Row;
    }
    
    
    public String getFile()
    {
        return File;
    }
    
    public void setFile(String file)
    {
        this.File = file;
    }

    public void setArtist(String artist) {
        this.Artist = artist;
    }

    public void setCategory(String category) {
      this.Category = category;
    }

    public void setTime(String time) {
      this.Time = time;
    }
    public String getTime()
    {
        return Time;
    }
    public String getCategory()
    {
        return Category;
    }
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
