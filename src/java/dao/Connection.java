/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import servlets.music;

/**
 *
 * @author aroum
 */
public class Connection {
     public static java.sql.Connection getConnection(){
        java.sql.Connection conn = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/shazam");
            conn = ds.getConnection();
 
        } catch (NamingException ex) {
            Logger.getLogger(music.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(music.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;

    }
}
