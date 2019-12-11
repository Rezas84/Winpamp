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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import winpamp.dal.DalController;

/**
 * FXML Controller class
 *
 * @author cille, filip, Reza and Francesco
 */
public class ConfirmDeletePlaylistController implements Initializable {
  private MainModel model;
  private DalController dc = new DalController();
    @FXML
    private Button cd;
    @FXML
    private Label plname;
    @FXML
    private Button cancel;
    public ConfirmDeletePlaylistController()
    {
          model = MainModel.GetInstance();//Ensure that we are working int he same instance of the MainModel as all the other controllers.
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {//When initializing..
        plname.setText(model.getPlaylistToDelete().getName()); //Get and set the name of the plauylist we are deleting.
    }    

    @FXML
    private void deletePlaylist(ActionEvent event) throws SQLException {
        dc.deletePlaylist(model.getPlaylistToDelete());//Passes the playlist to delete to our dal.
        model.removefromPlaylists();//removes the playlist from our MaoinModel.
    }

    @FXML
    private void cancelview(ActionEvent event) {//Close the window when pressing the cancel button.
          Stage stage = (Stage)cancel.getScene().getWindow();
            stage.close();
    }
    
}
