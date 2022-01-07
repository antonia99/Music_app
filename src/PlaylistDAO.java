import java.sql.SQLException;

public interface PlaylistDAO {
    public void add(int songId, int playlistId) throws SQLException;
    public void delete();
    public void createPlaylist();
    public Playlist[] getAllPlaylists() throws SQLException;
}