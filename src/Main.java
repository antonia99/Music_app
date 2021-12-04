import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException
    {  SongDAOFactory songDAOFactory = new SongDAOFactory();
        SongDAO sDAO = songDAOFactory.createSongDAO();

        //Song s1 = new Song(3, "Gigi","Winter",  3.3,"classic,rock,romantic","www.youtube.com");
        //Song s2 = new Song(4, "Selena","Summer",3.2,"pop,folk","www.youtube.com/SelenaGomez");
        //sDAO.add(s1);
        //sDAO.add(s2);
        //sDAO.delete(3);
        //for(Song s:sDAO.getAllSongs())
		//	System.out.println(s);
        //Menu menu=new Menu();
        //menu.printMenu();
        //menu.runMenu();

        System.out.println("1- add a song\n " +
                "2- delete a song\n " +
                "3- see all songs\n" +
                "4- add songs in playlist\n" +
                "5- add all songs from an artist to playlist\n"+
                "6- play the songs in the playlist");

        Scanner scanner = new Scanner(System.in);
        String next=scanner.next();
        switch (next){
            case "1":
                sDAO.add();
                System.out.println("Song added!");
                break;
            case "2":
                break;
        }

        }
    }









