/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.bll;

import java.sql.SQLException;
import winpamp.dal.DalController;

/**
 *
 * @author filip
 */
public class WinpampManager {
    private DalController dalController = new DalController();

    public void EditSong(String songN, String songA, String songC, String songT, String songF) {
        dalController.EditSong(songN, songA, songC, songT, songF);
    }
    
    public void NewSong(String songN, String songA, String songC, String songT, String songF) {
        dalController.NewSong(songN, songA, songC, songT, songF);
    }
}