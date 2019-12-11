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
import javafx.scene.control.ListView;
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
import winpamp.be.Playlist;
import winpamp.be.Song;
import winpamp.bll.WinpampManager;
import winpamp.dal.DalController;

/**
 *
 * @author filip, Cecilia, Reza and Francesco.
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
    @FXML
    private TableView<Playlist> playlistList;
    @FXML
    private TableColumn<Playlist, String> playlistName;
    @FXML
    private ListView<Song> sop;
    @FXML
    private Button addtop;
    @FXML
    private Button deletespl;
    @FXML
    private Button rowm;
    @FXML
    private Button rowp;
    @FXML
    private TableColumn<Playlist, Integer> playlistsSongs;
    
    public FXMLDocumentController()
    {
        model = MainModel.GetInstance(); //Ensure that we are always working with the right instance  of MainModel, which is the same for all the COntrollers.
    }
    
    
    
    
    
     private boolean clicked = true;
     private boolean newSong;
     private boolean playing = true;
    WinpampManager wm = new WinpampManager();
    DalController dl = new DalController();
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
    private void ShowDelete(ActionEvent event) throws IOException { //Method to show the Confirm Delete playlist.
        model.setPlaylistToDelete(playlistList.getSelectionModel().getSelectedItem());
        Parent blah = FXMLLoader.load(getClass().getResource("/winpamp/gui/ConfirmDeletePlaylist.fxml"));
        Scene scene = new Scene(blah);
        Stage SD = new Stage();
        SD.setScene(scene);
        SD.show();
    }

    @FXML
    private void ShowNewEdit(ActionEvent event) throws IOException { //MEthod to show the EditPLaylist.
        model.setPlaylistToDelete(playlistList.getSelectionModel().getSelectedItem());
        Parent blahh = FXMLLoader.load(getClass().getResource("/winpamp/gui/EditPlaylistInterface.fxml"));
        Scene scenee = new Scene(blahh);
        Stage SNE = new Stage();
        SNE.setScene(scenee);
        SNE.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();

          }    

    public void update() //Updates all the info in the main view.
    {
        playlistList.getItems().clear();
        songsList.getItems().clear();
        
       playlistsSongs.setCellValueFactory(
           new PropertyValueFactory ("row"));
       playlistName.setCellValueFactory(
            new PropertyValueFactory("name"));
         TitleC.setCellValueFactory(
            new PropertyValueFactory("name"));
        ArtistC.setCellValueFactory(
            new PropertyValueFactory("artist"));
       CategoryC.setCellValueFactory(
            new PropertyValueFactory("category"));
       TimeC.setCellValueFactory(
            new PropertyValueFactory("time"));
       songsList.setItems(model.getsongs());
    playlistList.setItems(model.getplsongs());
             
    }
   
  
    @FXML
    private void SongNew(ActionEvent event)  { //Method for adding a new song. A boolean gets passed to the wm instance of the Winpamp Manager.
        newSong = true;
        WinpampManager.wm.setSongBoolean(newSong); //Adds a boolean so that we know if we are adding a new song, or editing an existing one.
        SongShowNewEdit(); //Run the method to open a new window.
    }
    
    @FXML
    private void SongEdit(ActionEvent event)  { //Method for editing an existing song, sends a boolean to the wm instance of the Winpamp Manager.
        newSong = false;
        WinpampManager.wm.setSongBoolean(newSong); //Passes the boolean as false to know that we are editing a song, not adding it.
       Song selectedSong = songsList.getSelectionModel().getSelectedItem();
       model.edit(selectedSong);
        SongShowNewEdit();
         
         
    }
    
   
   private void SongShowNewEdit() { //Method to load the new/edit song window.
     
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
   
   
   
    @FXML
    private void deleteSong(ActionEvent event) throws SQLException { //Method to delete a song.
       Song song = songsList.getSelectionModel().getSelectedItem();
       dl.DeleteSong(song);
       model.getsongs().remove(song);
       }

    @FXML
    private void closeStage(ActionEvent event) { //Method to close the main window.
        Stage stage = (Stage) close.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void search(ActionEvent event) { //method for seaching or filtering the songs.
        if(clicked)
        { songsList.setItems(wm.search(model.getsongs(),searcherfield.getText()));//Search the MainModel which stores the lists, for the text entered into the field. DIsplay only those songs.
        searcher.setText("");
        clicked = false;}
        else
        {songsList.setItems(model.getsongs()); //Just display all the songs.
        searcher.setText("Search");
        searcherfield.clear();
        clicked = true;}
        
        
    }
     private static MediaPlayer mediaPlayer; //Creating a media player to make the play related buttons work.
     
    @FXML
    private void playSong(MouseEvent event) {
     File f = new File(songsList.getSelectionModel().getSelectedItem().getFile());//Select the file from the song.
     URI u = f.toURI();
     String s = u.toString();
     Media media = new Media(s);
     mediaPlayer = new MediaPlayer(media); //Sends teh song to the mediaplayer.
      
        if (playing == false) //Sets what to do it the player is not currently playing a song.
        {
            mediaPlayer.stop();
            File img = new File ("Play.png");
            playId.setImage(new Image(img.toURI().toString()));
            musiclabel.setText("nothing is playing");
            playing = true;
        }
            
        else{ //Sets the information when a song is playing.
           mediaPlayer.play();
           File img = new File ("Pause.png");
           playId.setImage(new Image(img.toURI().toString()));
           musiclabel.setText(songsList.getSelectionModel().getSelectedItem().getName()); //DIsplays the name of the current song.
           playing = false;
    }
        }
    @FXML
    private void changeVolume(MouseEvent event) { //MEthod to change the volume of the media player.
        mediaPlayer.setVolume(volumeBar.getValue() / 100);
    }

    @FXML
    private void showPlSongs(MouseEvent event) { //Displays the songs associated with e certian playlist, based on the playlist's name.
        sop.setItems(model.getSopList(playlistList.getSelectionModel().getSelectedItem().getName())); //Runs the method from our instance of the Main Model.
    
    }

    @FXML
    private void addToPl(ActionEvent event) throws SQLException { //MEthod to add song to a playlist.
        dl.addSongToPlaylist(songsList.getSelectionModel().getSelectedItem(),playlistList.getSelectionModel().getSelectedItem().GetId()); //Passes the song and playlist to our dal.
        sop.setItems(model.getSopList(playlistList.getSelectionModel().getSelectedItem().getName())); //passes the song to add to the playlist to our Main MOdel, to keep our internal list updated.
        playlistList.getSelectionModel().getSelectedItem().setRow(playlistList.getSelectionModel().getSelectedItem().getRow()+1);//Gives the song added an index within the playlsit.
        playlistsSongs.setVisible(false);//Just to update the number of songs in the playlist.
         playlistsSongs.setVisible(true);//Just to update the number of songs in the playlist.
        
           
    }

    @FXML
    private void deleteFromSOP(ActionEvent event) throws SQLException {
        int id = sop.getSelectionModel().getSelectedItem().getId();
        dl.removeSongFromPlaylist(songsList.getSelectionModel().getSelectedItem(),playlistList.getSelectionModel().getSelectedItem().GetId(),id);//passes the song to remove and the playlist to remove from to our dal.
         sop.setItems(model.getSopList(playlistList.getSelectionModel().getSelectedItem().getName())); //Deletes the song from the LIST on the playlist, which we store in the MainModel
          playlistList.getSelectionModel().getSelectedItem().setRow(playlistList.getSelectionModel().getSelectedItem().getRow()-1); //Removes one from the total number of songs in the playlist.
        playlistsSongs.setVisible(false); //Just to update the number of songs in the playlist.
         playlistsSongs.setVisible(true); //Just to update the number of songs in the playlist.
    }

    @FXML
    private void rowMinus(ActionEvent event) {//The method to move our song UP on the playlilst.
       model.moveSongUpOnPlaylist(sop.getSelectionModel().getSelectedItem());//pass the info to our MainModel.
     
    }

    @FXML
    private void rowPlus(ActionEvent event) { //THe method to move our song DOWN on playlist.
         model.moveSongDownOnPlaylist(sop.getSelectionModel().getSelectedItem()); //Passes the info to our Main MOdel.
    }

    private void indexor(MouseEvent event) { //TO update our amount of songs in the playlist.
        model.setItemcounter(sop.getSelectionModel().getSelectedIndex()); //Passes the info to our Main Model.
    }

    @FXML
    private void ShowNew(ActionEvent event) throws IOException {//Method to display the window for adding a new song.
          Parent blahh = FXMLLoader.load(getClass().getResource("/winpamp/gui/NewPlaylistInterface.fxml"));
        Scene scenee = new Scene(blahh);
        Stage SNE = new Stage();
        SNE.setScene(scenee);
        SNE.show();
    }
  
}
