/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip, Cecilia, Reza and Frncesco.
 */
public class NewPlaylistInterfaceController implements Initializable {

    @FXML
    private TextField playlistName;
    @FXML
    private Button cancel;
    private MainModel model; 
    /**
     * Initializes the controller class.
     */
    public NewPlaylistInterfaceController()
    {
         model = MainModel.GetInstance(); //ensure that we are working in the right instance of the MainModel, the same as all the other controllers.

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nothing needed here.
    }    

    @FXML
    private void cancelView(ActionEvent event) {//When clicking cancel, close the window.
         Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
    }

    @FXML
    private void newPl(ActionEvent event) throws SQLException {
        model.addPlaylist(playlistName.getText());//adds the playlist to our MainModel, which in turns adds it to the DAL.
        Stage stage = (Stage)cancel.getScene().getWindow();//Then select the window to close.
    
    stage.close();//close the window.
    }
    
}
