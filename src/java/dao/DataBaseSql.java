/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import static dao.Connection.getConnection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author aroum
 */
public class DataBaseSql {    
    
    public static ResultSet DtbsSql(){
        Statement DataBaseSqlstatement = null;
        ResultSet DataBaseSqlresultSet = null;
         
        try {
            DataBaseSqlstatement = getConnection().createStatement();
            DataBaseSqlresultSet = DataBaseSqlstatement.executeQuery("SELECT * FROM library ;");
            return DataBaseSqlresultSet;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseSql.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return DataBaseSqlresultSet;
    }
    
    public static void DltSql(String s, String a){
        Statement deletestatement = null;        
        String sql=null;                
        try {            
            deletestatement = getConnection().createStatement();  
            System.out.println(s+" "+a);
            sql = "DELETE FROM library WHERE (Artist='"+s+"' AND Song='"+a+"')";
            try (PreparedStatement statement = getConnection().prepareStatement(sql)){      
                
                int rowsAffected = statement.executeUpdate(sql);
                System.out.println(rowsAffected + " row(s) deleted");
            }catch (SQLException ex) {
                System.out.println("Sorry, problems with the database connection!");
                System.out.println(ex.toString());
                System.exit(0);
            }                  
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
            
}
