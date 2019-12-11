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
                model = MainModel.GetInstance();//ENsure that we are working with the samme instance of the MainModel as all the other controllers.
            }
    
   
private Song edit; //Sets the song we are working on, if any.
  

    @Override
    public void initialize(URL url, ResourceBundle rb) { //When initialillzing...
       if(WinpampManager.wm.getSongBoolean() == false) //fetches the boolean for if a song is being edited or created.
       {
         edit(model.getESong());//If the song is being edited, get the song we are working on.
       }
       
       SongCategory.getItems().addAll("Pop", "Hip-Hop", "Rock"); //Add these items to the category selector.
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
    private void SaveEditSong(ActionEvent event) throws SQLException {//When the save button is pressed...
      songName = SongTitle.getText();
      songArtist = SongArtist.getText();
      songCategory = (String) SongCategory.getValue();
      songTime = SongTime.getText();
      songFileLocation = SongFileLocation.getText();
      
    if(WinpampManager.wm.getSongBoolean() == true)//if we are adding a new song.
    {
        model.getsongs().add(WinpampManager.wm.NewSong(songName, songArtist, songCategory, songTime, songFileLocation));//add the song to our local list in the MainModel, as well as to the database, via the dal.
    }
    else{ //if we are editing a song.
        model.getsongs().remove(model.getESong());//remove the song as it is from our list in the MainModel
        model.getsongs().add(WinpampManager.wm.EditSong(model.getESong(),songName, songArtist, songCategory, songTime, songFileLocation ));//add the song to our MainModel local list, as well as EDIT the song in our database via our dal.
        WinpampManager.wm.setSongBoolean(true);
       }
        closeStage(event);//close the window once song is passed on.
    }

    @FXML
    private void closeStage(ActionEvent event) {//Method for actually closing the window.
        Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
    }

    @FXML
    private void chooseFile(ActionEvent event) {//method for being able to automatically choose a file from your HDD.
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            SongFileLocation.setText(selectedFile.getPath());//select the path
            SongTitle.setText(selectedFile.getName());//selec the name via filename
            setTimeField(selectedFile);//select the time, based on the file.
            
            
        }
    }
     private void setTimeField(File selectedFile)//Method to fetch the song time fromt the file. 
    {
      Media mediaFile = new Media(selectedFile.toURI().toString());//which media file.
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
    private void addCategory(ActionEvent event) {//Method to add a custom category to the edit/new song.
        SongCategory.getItems().add(addCategory.getText());
        addCategory.clear();
    }
    
    public void edit(Song song)//Method to edit the song, inside our business entity Song.
    {
        edit = song;
        SongTitle.setText(edit.getName());
        SongArtist.setText(edit.getArtist());
        SongCategory.setValue(edit.getCategory());     
        SongFileLocation.setText(edit.getFile());       
        SongTime.setText(edit.getTime());
    }

}
 
        