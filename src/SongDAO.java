import java.sql.SQLException;

public interface SongDAO {
    public void add();
    public void delete();
    public void createPlaylist();
    public Song[] getAllSongs() throws SQLException;
}
