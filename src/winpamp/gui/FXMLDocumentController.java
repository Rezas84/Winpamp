/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import winpamp.be.Song;
import winpamp.bll.WinpampManager;
import winpamp.dal.DalController;

/**
 *
 * @author filip
 *//////
public class FXMLDocumentController implements Initializable {
    private MainModel model;
    @FXML
    private Button deleteSongButton;
    @FXML
    private Button close;
    @FXML
    private Button searcher;
    @FXML
    private TextField searcherfield;
    @FXML
    private Label musiclabel;
     @FXML
    private Slider volumeBar;
    
    public FXMLDocumentController()
    {
        model = MainModel.GetInstance();
    }
    
    
    
    
    DalController dl = new DalController();
     private boolean clicked = true;
     private boolean newSong;
     private boolean playing = true;
    WinpampManager wm = new WinpampManager();
  
    NewEditSongController nesc = new NewEditSongController();

    @FXML
    private Label label;

    @FXML
    private Button DeletePlaylist;
    @FXML
    private Button NewPlaylist;
    @FXML
    private Button EditPlaylist;
    @FXML
    private Button NewSong;
    @FXML
    private Button EditSong;
    @FXML
    private TableView<Song> songsList;
    @FXML
    private Button testb;
    @FXML
    private TableColumn<Song, String> TitleC;
    @FXML
    private TableColumn<Song, String> ArtistC;
    @FXML
    private TableColumn<Song, String> CategoryC;
    @FXML
    private TableColumn<Song, String> TimeC;
    @FXML
    private ImageView playId;
    
   

    @FXML
    private void ShowDelete(ActionEvent event) throws IOException {
        Parent blah = FXMLLoader.load(getClass().getResource("/winpamp/gui/ConfirmDeletePlaylist.fxml"));
        Scene scene = new Scene(blah);
        Stage SD = new Stage();
        SD.setScene(scene);
        SD.show();
    }

    @FXML
    private void ShowNewEdit(ActionEvent event) throws IOException {
        Parent blahh = FXMLLoader.load(getClass().getResource("/winpamp/gui/EditPlaylistInterface.fxml"));
        Scene scenee = new Scene(blahh);
        Stage SNE = new Stage();
        SNE.setScene(scenee);
        SNE.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();
//        TitleC.setCellValueFactory(
//                new PropertyValueFactory("name"));
//        ArtistC.setCellValueFactory(
//            new PropertyValueFactory("artist"));
//       CategoryC.setCellValueFactory(
//            new PropertyValueFactory("category"));
//       TimeC.setCellValueFactory(
//            new PropertyValueFactory("time"));
//       songsList.setItems(WinpampManager.wm.GetsList());
       
       
     //  if(!mList.equals(wm.GetsList()))
     //  {
     // songsList.setItems(wm.GetsList());  experimental auto update
     //  mList = wm.GetsList();
     //  }
          }    

    public void update()
    {
        songsList.getItems().clear();
         TitleC.setCellValueFactory(
                new PropertyValueFactory("name"));
        ArtistC.setCellValueFactory(
            new PropertyValueFactory("artist"));
       CategoryC.setCellValueFactory(
            new PropertyValueFactory("category"));
       TimeC.setCellValueFactory(
            new PropertyValueFactory("time"));
       songsList.setItems(model.getsongs());
    }
    
    
    
    
    @FXML
    private Song getselectedid(ActionEvent event)
    {
       Song song = songsList.getSelectionModel().getSelectedItem();
        return song;
    }
    
    
    
    
    
    
    
    @FXML
    private void SongNew(ActionEvent event)  {
        newSong = true;
        WinpampManager.wm.setSongBoolean(newSong);
        SongShowNewEdit();
    }
    
    @FXML
    private void SongEdit(ActionEvent event)  {
        newSong = false;
        WinpampManager.wm.setSongBoolean(newSong);
       Song selectedSong = songsList.getSelectionModel().getSelectedItem();
       model.edit(selectedSong);
        SongShowNewEdit();
         
         
    }
    
   
   private void SongShowNewEdit() {
     
        try {
            Parent blahhh = FXMLLoader.load(getClass().getResource("/winpamp/gui/NewEditSong.fxml"));
            Scene sceneee = new Scene(blahhh);
            Stage SSNE = new Stage();
            SSNE.setScene(sceneee);
            SSNE.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }



//    @FXML
//    private void changeVolume(MouseEvent event) {
//        player.setVolume(volumeBar.getValue() / 100);
//    }

    @FXML
    private void deleteSong(ActionEvent event) throws SQLException {
       Song song = songsList.getSelectionModel().getSelectedItem();
       dl.DeleteSong(song);
       model.getsongs().remove(song);
       }

    @FXML
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void search(ActionEvent event) {
        if(clicked)
        { songsList.setItems(wm.search(model.getsongs(),searcherfield.getText()));
        searcher.setText("");
        clicked = false;}
        else
        {songsList.setItems(model.getsongs());
        searcher.setText("Search");
        searcherfield.clear();
        clicked = true;}
        
        
    }
     private static MediaPlayer mediaPlayer;
     
    @FXML
    private void playSong(MouseEvent event) {
     File f = new File(songsList.getSelectionModel().getSelectedItem().getFile());
     URI u = f.toURI();
     String s = u.toString();
     Media media = new Media(s);
     mediaPlayer = new MediaPlayer(media); 
      
        if (playing == false)
        {
            mediaPlayer.stop();
            File img = new File ("Play.png");
            playId.setImage(new Image(img.toURI().toString()));
            musiclabel.setText("nothing is playing");
            playing = true;
        }
            
        else{
           mediaPlayer.play();
           File img = new File ("Pause.png");
           playId.setImage(new Image(img.toURI().toString()));
           musiclabel.setText(songsList.getSelectionModel().getSelectedItem().getName());
           playing = false;
    }
        }
    @FXML
    private void changeVolume(MouseEvent event) {
        mediaPlayer.setVolume(volumeBar.getValue() / 100);
    }
}
