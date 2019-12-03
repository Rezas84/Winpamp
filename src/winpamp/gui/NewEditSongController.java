/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import java.awt.TextField;
import winpamp.bll.WinpampManager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author cille
 */
public class NewEditSongController implements Initializable {
    
    
    
    private WinpampManager winpamplogic = new WinpampManager(); 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private TextField SongName;
    
    @FXML
    private TextField SongArtist;
        
    @FXML
    private ChoiceBox SongCategory;    
    
    @FXML
    private TextField SongTime;
    
    @FXML
    private TextField SongFileLocation;
    
    private String songName = SongName.getText();
    private String songArtist = SongArtist.getText();
    private String songCategory = (String) SongCategory.getValue();
    private String songTime = SongTime.getText();
    private String songFileLocation = SongFileLocation.getText();
    
    @FXML
    private void EditSong(ActionEvent event) {

        winpamplogic.EditSong(songName, songArtist, songCategory, songTime, songFileLocation );
    }
    

}
