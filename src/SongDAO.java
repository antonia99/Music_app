import java.io.IOException;
import java.sql.SQLException;

public interface SongDAO {
    public void add();
    public void delete();
    public Song[] getAllSongs() throws SQLException;
    public void playSong(int songId, String url) throws SQLException, IOException;
}
