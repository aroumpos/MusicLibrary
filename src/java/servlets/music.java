package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import org.json.*;

import com.beaglebuddy.mp3.MP3;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static dao.Connection.getConnection;


/**
 *
 * @author aroum
 */

@WebServlet(name = "music", urlPatterns = {"/music"})
@MultipartConfig(maxFileSize = 10737418240L, maxRequestSize = 10737418240L, fileSizeThreshold = 52428800)
   
public class music extends HttpServlet {
   
   
    public static String Lyrics(String a, String s) throws MalformedURLException, ProtocolException, IOException{
        String a1=a.replaceAll(" ", "%20");
        String s1=s.replaceAll(" ", "%20");
        URL url=new URL("https://api.lyrics.ovh/v1/"+a1+"/"+s1+"");
        try (InputStream is = url.openStream();
        JsonReader rdr = Json.createReader(is)) { 
        JsonObject obj = rdr.readObject();
        String lyrics = obj.getString("lyrics");
        return lyrics;
        }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MP3 m;
        PreparedStatement stm=null;
        final Part filepart=request.getPart("music");
        final String filename=filepart.getSubmittedFileName();
        filepart.write("C:\\javacode\\"+filename);
        File f=new File("C:\\javacode\\"+filename);
        m = new MP3(f);
        String artist=m.getBand();
        String title=m.getTitle();
        String sql=null;
        String lyrics=Lyrics(artist,title);
        while(lyrics==null){
            lyrics="No Lyrics";
        }
        
        String sqls="INSERT INTO library VALUES (?,?,?,?)";
        try{
            System.out.print("Inserting a new row into table...");
            stm=getConnection().prepareStatement(sqls); 
            stm.setString(1, artist);
            stm.setString(2, title);
            stm.setString(3, lyrics);
            stm.setBlob(4,filepart.getInputStream());
            int n=stm.executeUpdate();
            System.out.println(n+" "+"record inserted");
        } catch (SQLException ex) {
            Logger.getLogger(music.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/library.jsp");
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
