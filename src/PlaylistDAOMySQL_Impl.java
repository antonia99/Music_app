import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO{

    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/music_app?allowPublicKeyRetrieval=true&useSSL=false";
    public Scanner scan = new Scanner(System.in);

    @Override
    public void add(int songId, int playlistId) throws SQLException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into playlist_song(playlistid, songid) VALUES (?,?)");
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);
            int affectedRows = ps.executeUpdate();
            closeConnection(conn);
        }catch(SQLException e){

        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void createPlaylist() {
        System.out.print("Playlist name: ");
        String name = scan.nextLine();
        System.out.print("Duration: ");
        double duration = 0;
        try {
            duration = Float.parseFloat(scan.nextLine());
        }catch(Exception e){
            System.out.println("Duration inserted incorrectly. Automatically setting it to 0.");
        }

        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into playlist(name, duration) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, duration);
            int affectedRows = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rs.getInt(1);
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Playlist[] getAllPlaylists() throws SQLException {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from playlist");
            ResultSet rs = ps.executeQuery();
            ArrayList<Playlist> Playlists = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                Playlists.add(new Playlist(id, name, duration));
            }
            closeConnection(conn);
            return Playlists.toArray(new Playlist[Playlists.size()]);
        } catch (SQLException e) {
            return null;
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
