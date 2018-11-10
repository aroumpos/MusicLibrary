<%-- 
    Document   : library
    Created on : 28 Οκτ 2018, 11:03:55 μμ
    Author     : aroum
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="dao.DataBaseSql"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CB_PROJECT_4</title>
         <style>
            body{
                background-image:url("https://www.tarafdari.com/sites/default/files/contents/user427498/content-note/dj-boy-abstract-art-hd.jpg");
                height:100%;
                background-position:center;
                background-repeat: no-repeat;
                background-size: cover;
                background-attachment: fixed;
            }
            
        </style>
    </head>
    <body>
        <div> <h1><B><I>SHAZAM</I></B></h1></div>
        <div><h4>MUSIC LIBRARY</h4></div>
        <form method="post">
        <%
        ResultSet rs= DataBaseSql.DtbsSql();
        while(rs.next()){   
            String Artist=rs.getString(1);
            String Song=rs.getString(2);
            String Lyrics=rs.getString(3);
        %>
        <table border="2" style="width:100% ; text-shadow:2px 2px 10px white">
            <tr>
                <td name="artist"><% out.println("ARTIST: "+Artist); %></td>
                <td name="song"><% out.println("SONG: "+Song); %></td>
                <td><a href="delete?artist=<% out.println(Artist); %>&title=<% out.println(Song); %>">DELETE</td>
            </tr>
            <tr>
                <td colspan="3">LYRICS</td>
            </tr>
            <tr>
                <td colspan="3"><% out.println(Lyrics); %></td>
            </tr>
            <%
            }    
            %>
        </table>
            <P>Do you want to upload more?</P>
        </form> 
        <form method="post" action="music" enctype="multipart/form-data">            
            <input type="file" name="music">
            <input type="submit" value="upload">
        </form>
    </body>
</html>
