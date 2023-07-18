/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Notorious
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;


public class songsql {
    public Connection cn;
    public Statement st;
    private JTable jTable1;
    private DefaultTableModel model;

    
    public void getConn(){
       try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/songbase?zeroDateTimeBehavior=CONVERT_TO_NULL","root","");
            st = cn.createStatement();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Not Connected");
        }
    }
    
    public void showData(DefaultTableModel model){
        this.model = model;
      try {
            getConn();
            Statement st = cn.createStatement();
            // Execute the query to retrieve data from the "studrec" table
            String query = "SELECT song_id, song_title, song_artist, song_duration FROM songs";
            ResultSet resultSet = st.executeQuery(query);
            
            // Clear the existing table data
            model.setRowCount(0);
            
            // Iterate over the result set and populate the table model
            while (resultSet.next()) {
                String song_id = resultSet.getString("song_id");
                String song_title = resultSet.getString("song_title");
                String song_artist = resultSet.getString("song_artist");
                String song_duration = resultSet.getString("song_duration");
                
                // Add a new row to the table model
                model.addRow(new Object[]{"▶", song_id, song_title, song_artist, song_duration});
            }
            
            // Close the statement and connection
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
    public String getSongId(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            return (String) model.getValueAt(rowIndex, 1); // Assuming the song_id is in the second column (index 1)
        }
        return null;
    }
    
    public String getLyricsPath(String songId) {
        try {
            getConn();
            Statement st = cn.createStatement();
            String query = "SELECT song_lyrfpath FROM songs WHERE song_id = '" + songId + "'";
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("song_lyrfpath");
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void setLyrics(String songId, JTextArea jTextArea1) {
        String lyricsPath = getLyricsPath(songId);
        if (lyricsPath != null) {
            try {
                FileReader reader = new FileReader(lyricsPath);
                BufferedReader br = new BufferedReader(reader);
                jTextArea1.read(br, null);
                br.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setTitle(String songId, JLabel jLabel2, JLabel jLabel3) {
        try {
            getConn();
            Statement st = cn.createStatement();
            String query = "SELECT song_title, song_artist FROM songs WHERE song_id = '" + songId + "'";
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                String songTitle = resultSet.getString("song_title");
                String songArtist = resultSet.getString("song_artist");
                jLabel2.setText(songTitle);
                jLabel3.setText(songArtist);
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setCover(String songId, JLabel jLabel1) {
        try {
            getConn();
            Statement st = cn.createStatement();
            String query = "SELECT song_imgfpath FROM songs WHERE song_id = '" + songId + "'";
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                String songImgfpath = resultSet.getString("song_imgfpath");
                InputStream stream = getClass().getResourceAsStream(songImgfpath);
                if (stream != null) {
                    ImageIcon icon = new ImageIcon(ImageIO.read(stream));
                    jLabel1.setIcon(icon);
                }
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getSongFilePath(String songId) {
        try {
            getConn();
            String query = "SELECT song_fpath FROM songs WHERE song_id = '" + songId + "'";
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("song_fpath");
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSongDuration(String songId) {
        try {
            getConn();
            String query = "SELECT song_duration FROM songs WHERE song_id = '" + songId + "'";
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("song_duration");
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
