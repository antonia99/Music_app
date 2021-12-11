import java.sql.*;
import java.util.Scanner;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO{
    public static final String CONNECTION_URL = "jdbc:mysql://localhost/music_app";
    public Scanner scan = new Scanner(System.in);

    @Override
    public void addToPlaylist() {
        System.out.print("Playlist name: ");
            String name = scan.nextLine();
            System.out.print("artist: ");
            String artist = scan.nextLine();
            System.out.print("title: ");
            String title = scan.nextLine();
            //int id = Integer.parseInt(scan.nextLine());

            try {
                Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement("select id from songs where artist=? and title=?", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, artist);
                st.setString(2, title);
                ResultSet rs = st.executeQuery();
                PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                while (rs.next()) {
                    int id_s = rs.getInt("id");

                    ps.setString(1, name);
                    ps.setInt(2, id_s);

                    int affectedRows = ps.executeUpdate();
                }


                ;
                closeConnection(conn);

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
    @Override
    public void addAllToPlaylist() {
        System.out.print("id: ");
        int id = Integer.parseInt(scan.nextLine());
        System.out.print("name: ");
        String name = scan.nextLine();
        System.out.print("Id song: ");
        int id_s = Integer.parseInt(scan.nextLine());
        //int id = Integer.parseInt(scan.nextLine());
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into playlist(id,name,id_s) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);//s.setArtist(scan.nextLine()));
            ps.setString(2, name);
            ps.setInt(3, id_s);
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
    public void playSongs() {

    }

    public Connection getConnection () throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_URL, "root", "");
    }


    public void closeConnection (Connection conn) throws SQLException
    {
        conn.close();
    }
}
