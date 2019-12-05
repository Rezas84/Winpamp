/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;


import java.io.File;
import winpamp.bll.WinpampManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import winpamp.be.Song;
/**
 * FXML Controller class
 *
 * @author cille
 */
public class NewEditSongController implements Initializable {
    private MainModel model;
    @FXML
    private Button cancel;
    @FXML
    private Button choose;
    @FXML
    private Button save;
    @FXML
    private Button moreCategories;
    @FXML
    private TextField addCategory;
    public NewEditSongController()
            {
                model = MainModel.GetInstance();
            }
    
   
private Song edit;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       if(WinpampManager.wm.getSongBoolean() == false)
       {
         edit(model.getESong());
       }
       
       SongCategory.getItems().addAll("Pop", "Hip-Hop", "Rock");
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
    private void SaveEditSong(ActionEvent event) throws SQLException {
      songName = SongTitle.getText();
      songArtist = SongArtist.getText();
      songCategory = (String) SongCategory.getValue();
      songTime = SongTime.getText();
      songFileLocation = SongFileLocation.getText();
      
    if(WinpampManager.wm.getSongBoolean() == true)
    {
     //WinpampManager.wm.NewSong(songName, songArtist, songCategory, songTime, songFileLocation );
     model.getsongs().add(WinpampManager.wm.NewSong(songName, songArtist, songCategory, songTime, songFileLocation));
    }
    else{
    model.getsongs().remove(model.getESong());
    model.getsongs().add(WinpampManager.wm.EditSong(model.getESong(),songName, songArtist, songCategory, songTime, songFileLocation ));
    WinpampManager.wm.setSongBoolean(true);
       }
        closeStage(event);
    }

    @FXML
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
    }

    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
       fileChooser.setTitle("Open Resource File");
       File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            SongFileLocation.setText(selectedFile.getPath());
            SongTitle.setText(selectedFile.getName());
            setTimeField(selectedFile);
            
            
        }
    }
     private void setTimeField(File selectedFile) 
    {
      Media mediaFile = new Media(selectedFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(mediaFile);
        mediaPlayer.setOnReady(new Runnable()
            {
                @Override
                public void run()
                {
                    int timeOfSong = (int) mediaFile.getDuration().toSeconds();
                    SongTime.setText(model.ttoString(timeOfSong));
                }
            }      
        );
    }

    @FXML
    private void addCategory(ActionEvent event) {
        SongCategory.getItems().add(addCategory.getText());
        addCategory.clear();
    }
    
    public void edit(Song song)
    {
        edit = song;
        SongTitle.setText(edit.getName());
        SongArtist.setText(edit.getArtist());
        SongCategory.setValue(edit.getCategory());     
        SongFileLocation.setText(edit.getFile());       
        SongTime.setText(edit.getTime());
        System.out.println("wykonuje");
    }

}
 
        