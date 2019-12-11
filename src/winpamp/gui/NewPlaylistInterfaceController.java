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
 * @author filip
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
         model = MainModel.GetInstance();

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelView(ActionEvent event) {
         Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
    }

    @FXML
    private void newPl(ActionEvent event) throws SQLException {
        model.addPlaylist(playlistName.getText());
        Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
    }
    
}
