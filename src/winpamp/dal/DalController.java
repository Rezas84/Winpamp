/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winpamp.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class DalController {
    


    public void EditSong(String name, String artist, String category, String time, String file) {
        
        //configuring the connection
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("Winpamp");
        ds.setUser("CSe19B_6");
        ds.setPassword("CSe19B_6");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
        
        try(Connection con = ds.getConnection()){
            String sqlIf = "UPDATE FROM ALLSONGS SET name = ?, artist = ?, category = ?, time = ? WHERE file = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            pstmt.setString(1, name);
            pstmt.setString(2, artist);
            pstmt.setString(3, category);
            pstmt.setString(4, time);
            pstmt.setString(5, file);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void NewSong(String name, String artist, String category, String time, String file) {
        
        //configuring the connection
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("Winpamp");
        ds.setUser("CSe19B_6");
        ds.setPassword("CSe19B_6");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
        
        try(Connection con = ds.getConnection()){
            String sqlIf = "INSERT INTO ALLSONGS (name, artist, category, time, file) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlIf);
            pstmt.setString(1, name);
            pstmt.setString(2, artist);
            pstmt.setString(3, category);
            pstmt.setString(4, time);
            pstmt.setString(5, file);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
