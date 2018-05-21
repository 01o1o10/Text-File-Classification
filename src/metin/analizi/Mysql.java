/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metin.analizi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilyas
 */
public class Mysql {
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public void connect(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gramaj?user=gramaj&password=gramaj123&useSSL=False");
            System.out.println("DB connection succes!");
            query("delete from grams;");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    public void query(String q){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(q);
        } catch (SQLException ex) {
            Logger.getLogger(Mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String[][] squery(String q){
        String[][] result = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(q);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 0; i < rs.getRow() && rs.next(); i++){
                for(int j = 1; j <= columnCount; j++){
                    result[i][j] = rs.getString(j);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
