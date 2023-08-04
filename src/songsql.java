
/**
 *
 * @author Bryce Stephen Halnin
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
    
// Methods for jTable1 (using song_id)
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

// Methods for jTable3 (using song_title)
public void setLyricsByTitle(String songTitle, JTextArea jTextArea1) {
    String lyricsPath = getLyricsPathByTitle(songTitle);
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

public void setTitleByTitle(String songTitle, JLabel jLabel2, JLabel jLabel3) {
    try {
        getConn();
        String query = "SELECT song_title, song_artist FROM songs WHERE song_title = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, songTitle);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            String songArtist = resultSet.getString("song_artist");
            jLabel2.setText(songTitle);
            jLabel3.setText(songArtist);
        }
        cn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void setCoverByTitle(String songTitle, JLabel jLabel1) {
    try {
        getConn();
        String query = "SELECT song_imgfpath FROM songs WHERE song_title = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, songTitle);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            String songImgfpath = resultSet.getString("song_imgfpath");
            InputStream stream = getClass().getResourceAsStream(songImgfpath);
            if (stream != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(stream));
                jLabel1.setIcon(icon);
            }
        }
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
    public String getLyricsPathByTitle(String songTitle) {
        try {
            getConn();
            String query = "SELECT song_lyrfpath FROM songs WHERE song_title = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, songTitle);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("song_lyrfpath");
            }
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
    
    public ResultSet getSongsFromPlaylist(String playlistTitle){
        try{
            getConn();
            String query = "SELECT * FROM playlists WHERE list_title = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, playlistTitle);
            ResultSet resultSet = ps.executeQuery();
            return resultSet;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<String> getAllPlaylistTitles(){
        ArrayList<String> playlistTitles = new ArrayList<>();
        try {
            getConn();
            Statement st = cn.createStatement();
            String query = "SELECT list_title FROM playlists";
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                String playlistTitle = resultSet.getString("list_title");
                playlistTitles.add(playlistTitle);
            }
            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlistTitles;
    }
    public ArrayList<String> getAllSongTitles() {
        ArrayList<String> songTitles = new ArrayList<>();

        try {
            getConn();
            String query = "SELECT song_title FROM songs";
            ResultSet resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                String songTitle = resultSet.getString("song_title");
                songTitles.add(songTitle);
            }

            st.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return songTitles;
    }
    public boolean createPlaylist(String playlistTitle, ArrayList<String> selectedSongTitles) {
        try {
            getConn();

            // Insert the playlist into the 'playlists' table.
            String insertPlaylistQuery = "INSERT INTO playlists (list_song_id, list_title, list_song_title, list_song_artist, list_song_duration, list_song_fpath, list_song_lyrfpath, list_song_imgfpath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPlaylistStmt = cn.prepareStatement(insertPlaylistQuery, Statement.RETURN_GENERATED_KEYS);
            insertPlaylistStmt.setString(1, playlistTitle);

            // Now, insert the songs into the 'playlists' table with the given playlist title.
            String insertSongQuery = "INSERT INTO playlists (list_song_id, list_title, list_song_title, list_song_artist, list_song_duration, list_song_fpath, list_song_lyrfpath, list_song_imgfpath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertSongStmt = cn.prepareStatement(insertSongQuery);

            for (String songTitle : selectedSongTitles) {
                // Retrieve song details from the 'songs' table based on the selected song title.
                String query = "SELECT * FROM songs WHERE song_title = ?";
                PreparedStatement ps = cn.prepareStatement(query);
                ps.setString(1, songTitle);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    String songId = resultSet.getString("song_id");
                    String songArtist = resultSet.getString("song_artist");
                    String songDuration = resultSet.getString("song_duration");
                    String songFpath = resultSet.getString("song_fpath");
                    String songLyrfpath = resultSet.getString("song_lyrfpath");
                    String songImgfpath = resultSet.getString("song_imgfpath");

                    // Insert the selected song details along with the playlist title into the 'playlists' table.
                    insertSongStmt.setString(1, songId);
                    insertSongStmt.setString(2, playlistTitle);
                    insertSongStmt.setString(3, songTitle);
                    insertSongStmt.setString(4, songArtist);
                    insertSongStmt.setString(5, songDuration);
                    insertSongStmt.setString(6, songFpath);
                    insertSongStmt.setString(7, songLyrfpath);
                    insertSongStmt.setString(8, songImgfpath);
                    insertSongStmt.executeUpdate();
                }
            }

            insertPlaylistStmt.close();
            insertSongStmt.close();
            cn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getSongIdByTitle(String songTitle) {
    try {
        getConn();
        String query = "SELECT song_id FROM songs WHERE song_title = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, songTitle);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("song_id");
        }
        cn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public String getSongFilePathByTitle(String songTitle) {
        try {
            getConn();
            String query = "SELECT song_fpath FROM songs WHERE song_title = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, songTitle);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("song_fpath");
            }
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
