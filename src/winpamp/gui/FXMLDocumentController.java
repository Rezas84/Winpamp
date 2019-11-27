/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author filip
 */
public class FXMLDocumentController implements Initializable {
    
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
        // TODO
    }    

    @FXML
    private void SongShowNewEdit(ActionEvent event) throws IOException {
    
Parent blahhh = FXMLLoader.load(getClass().getResource("/winpamp/gui/NewEditSong.fxml"));
            Scene sceneee = new Scene(blahhh);
            Stage SSNE = new Stage();
            SSNE.setScene(sceneee);
            SSNE.show();    
    }
}
