/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.be;

/**
 *
 * @author filip
 */
public class Song {
    private String Name;
    private String Artist;
    private String Category;
    private String Time;
    

    public Song(String name,String artist,String category,String time) {
        this.Name = name;
        this.Artist = artist;
        this.Category = category;
        this.Time = time;
    }
     public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
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
}
