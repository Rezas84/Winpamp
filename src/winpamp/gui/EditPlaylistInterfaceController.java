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
import winpamp.dal.DalController;

/**
 * FXML Controller class
 *
 * @author cille
 */
public class EditPlaylistInterfaceController implements Initializable {
    private DalController dc = new DalController();
 private MainModel model;
         
    @FXML
    private TextField playlistName;
    @FXML
    private Button cancel;
    @FXML
    private Button savebutt;
    /**
     * Initializes the controller class.
     */
 public EditPlaylistInterfaceController()
 {
      model = MainModel.GetInstance(); //ensure we are working with the same instance of the MainModel, as all the other controllers.
 }
    @Override
    public void initialize(URL url, ResourceBundle rb) { //When initializing...
        playlistName.setText(model.getPlaylistToDelete().getName()); //fetch and set the name of the playlist we are editing.
       
    }    

    @FXML
    private void cancelView(ActionEvent event) { //exit the window when pressing the cancel button.
         Stage stage = (Stage)cancel.getScene().getWindow();
    
    stage.close();
     }
    
    public void edit() throws SQLException
    {
        int row = model.getPlaylistToDelete().getRow();
         model.getplsongs().add(dc.EditPlaylist(model.getPlaylistToDelete(),playlistName.getText(),model.getPlaylistToDelete().getRow()));
        model.getplsongs().remove(model.getPlaylistToDelete());
       
    
       
    }
    
    @FXML
    private void saveEdited(ActionEvent event) throws SQLException {
           edit();
            Stage stage = (Stage)cancel.getScene().getWindow();
            stage.close();
    }
    
            
}
