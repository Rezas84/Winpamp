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
public class Playlist {
    private String Name;
    private int Id;
    public Playlist(String name,int id)
    {
        this.Name = name;
        this.Id = id;
    }
    public String getName()
    {
        return Name;
    }
    public int GetId()
    {
        return Id;
    }
     public void setName(String name) {
        this.Name = name;
    }
}
