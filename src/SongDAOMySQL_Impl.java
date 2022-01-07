import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SongDAOMySQL_Impl implements SongDAO{
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/music_app?allowPublicKeyRetrieval=true&useSSL=false";
    public Scanner scan = new Scanner(System.in);

        @Override
        public void add (){
        System.out.print("artist: ");
        String artist = scan.nextLine();
        System.out.print("title: ");
        String title = scan.nextLine();
        System.out.print("duration: ");
        float duration = 0;
        try {
            duration = Float.parseFloat(scan.nextLine());
        }catch(Exception e){
            System.out.println("Duration inserted incorrectly. Automatically setting it to 0.");
        }
        System.out.print("type: ");
        String type = scan.nextLine();
        System.out.print("link: ");
        String link = scan.nextLine();
        //int id = Integer.parseInt(scan.nextLine());
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into songs(artist,title,duration,type,link) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist);//s.setArtist(scan.nextLine()));
            ps.setString(2, title);
            ps.setDouble(3, duration);
            ps.setString(4, type);
            ps.setString(5, link);
            int affectedRows = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rs.getInt(1);
            } ;
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        @Override
        public void delete (){
        try {
            System.out.print("id: ");
            int id_song = Integer.parseInt(scan.nextLine());
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from songs where id = ?");
            ps.setInt(1, id_song);
            int affectedRows = ps.executeUpdate();
            closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Override
        public Song[] getAllSongs () throws SQLException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from songs");
            ResultSet rs = ps.executeQuery();
            ArrayList<Song> SongsList = new ArrayList<Song>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                double duration = rs.getDouble("duration");
                String type = rs.getString("type");
                String link = rs.getString("link");
                SongsList.add(new Song(id, artist, title, duration, type, link));
                for (int i = 0; i < SongsList.size(); i++) {
                    Collections.sort(SongsList, new Compare());
                }
            }
            closeConnection(conn);
            return SongsList.toArray(new Song[SongsList.size()]);

        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Song getSong(String artistName, String songTitle) throws SQLException{
        try {
            Song song = null;
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM music_app.songs WHERE artist = ? AND title = ?");
            ps.setString(1, artistName);
            ps.setString(2, songTitle);
            ResultSet rs = ps.executeQuery();
            ArrayList<Song> SongsList = new ArrayList<Song>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                double duration = rs.getDouble("duration");
                String type = rs.getString("type");
                String link = rs.getString("link");
                song = new Song(id, artist, title, duration, type, link);
            }
            closeConnection(conn);
            return song;

        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Song[] getAllSongsPerArtist(String artistName){
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from songs where artist = ?");
            ps.setString(1, artistName);
            ResultSet rs = ps.executeQuery();
            ArrayList<Song> SongsList = new ArrayList<Song>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                double duration = rs.getDouble("duration");
                String type = rs.getString("type");
                String link = rs.getString("link");
                SongsList.add(new Song(id, artist, title, duration, type, link));
                for (int i = 0; i < SongsList.size(); i++) {
                    Collections.sort(SongsList, new Compare());
                }
            }
            closeConnection(conn);
            return SongsList.toArray(new Song[SongsList.size()]);

        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Song[] getAllSongsPerPlaylist(int playlistId) throws SQLException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM music_app.songs INNER JOIN music_app.playlist_song ON songs.id = playlist_song.songid WHERE playlistid = ?");
            ps.setInt(1, playlistId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Song> SongsList = new ArrayList<Song>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                double duration = rs.getDouble("duration");
                String type = rs.getString("type");
                String link = rs.getString("link");
                SongsList.add(new Song(id, artist, title, duration, type, link));
                for (int i = 0; i < SongsList.size(); i++) {
                    Collections.sort(SongsList, new Compare());
                }
            }
            closeConnection(conn);
            return SongsList.toArray(new Song[SongsList.size()]);

        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public void playSong(int id, String url) throws SQLException, IOException {
        java.awt.Desktop.getDesktop().browse( java.net.URI.create(url));
        try{
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into statistic_plays(songid, dateplayed) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            java.util.Date date=java.util.Calendar.getInstance().getTime();
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

            ps.setDate(2, sqlStartDate);
            int affectedRows = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rs.getInt(1);
            }
            closeConnection(conn);
        }catch(SQLException e){

        }
    }

    public Connection getConnection () throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_URL, "liv", "Fermuar123");
    }


    public void closeConnection (Connection conn) throws SQLException
    {
        conn.close();
    }
}
