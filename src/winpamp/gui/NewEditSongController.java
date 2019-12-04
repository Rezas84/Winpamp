/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;


import winpamp.bll.WinpampManager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
    private TextField SongTitle;
    
   @FXML
   private TextField SongArtist;
       
   @FXML
   private ChoiceBox SongCategory;    
  
   @FXML
  private TextField SongTime;
   
   @FXML
   private TextField SongFileLocation;
    
   private String songName;
   private String songArtist;
   private String songCategory;
   private String songTime;
   private String songFileLocation;
    
    @FXML
    private void SaveEditSong(ActionEvent event) {
      songName = SongTitle.getText();
      songArtist = SongArtist.getText();
      songCategory = (String) SongCategory.getValue();
      songTime = SongTime.getText();
      songFileLocation = SongFileLocation.getText();
        
        
     winpamplogic.EditSong(songName, songArtist, songCategory, songTime, songFileLocation );
    }
    

}
