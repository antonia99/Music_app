import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {  SongDAOFactory songDAOFactory = new SongDAOFactory();
        SongDAO sDAO = songDAOFactory.createSongDAO();

        Song s1 = new Song(3, "Gigi","Winter",  3.3,"classic,rock,romantic","www.youtube.com");
        Song s2 = new Song(4, "Selena","Summer",3.2,"pop,folk","www.youtube.com/SelenaGomez");
        //sDAO.add(s1);
        //sDAO.add(s2);
        //sDAO.delete(3);
        for(Song s:sDAO.getAllSongs())
			System.out.println(s);



    }

}
