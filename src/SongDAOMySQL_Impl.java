import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class SongDAOMySQL_Impl implements SongDAO{
    public static final String CONNECTION_URL = "jdbc:mysql://localhost/music_app";

    @Override
    public void add(Song s) {

        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into songs(artist,title,duration,type,link) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getArtist());
            ps.setString(2, s.getTitle());
            ps.setDouble(3, s.getDuration());
            ps.setString(4, s.getType());
            ps.setString(5, s.getLink());
            int affectedRows = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                s.setId(rs.getInt(1));
            }
            closeConnection(conn);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_URL, "root", "");
    }



    public void closeConnection(Connection conn) throws SQLException
    {
        conn.close();
    }



    @Override
    public boolean delete(int id) {
        try
        {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from songs where id = ?");
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            closeConnection(conn);
            return affectedRows == 1;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public void createPlaylist() {

    }

    @Override
    public Song[] getAllSongs() throws SQLException {
        try
        {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from songs");
            ResultSet rs = ps.executeQuery();
            ArrayList<Song> SongsList = new ArrayList<Song>();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String artist= rs.getString("artist");
                String title= rs.getString("title");
                double duration = rs.getDouble("duration");
                String type= rs.getString("type");
                String link= rs.getString("link");
                SongsList.add(new Song(id, artist,title,duration,type,link));
                for(int i=0;i<SongsList.size();i++ ) {
                    Collections.sort(SongsList,new Compare());}
            }
            closeConnection(conn);
            return SongsList.toArray(new Song[SongsList.size()]);

        } catch (SQLException e) {

            return null;
        }


    }



}
