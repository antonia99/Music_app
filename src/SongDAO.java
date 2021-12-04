import java.sql.SQLException;

public interface SongDAO {
    public void add();
    public boolean delete(int id);
    public void createPlaylist();
    public Song[] getAllSongs() throws SQLException;
}
