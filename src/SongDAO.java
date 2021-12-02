import java.sql.SQLException;

public interface SongDAO {
    public void add(Song c);
    public boolean update(Song c);
    public boolean delete(int id);
    public void createPlaylist();
    public void getAllSongs() throws SQLException;
}
