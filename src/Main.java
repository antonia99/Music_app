import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException
    {  SongDAOFactory songDAOFactory = new SongDAOFactory();
        SongDAO sDAO = songDAOFactory.createSongDAO();
        PlaylistDAOFactory PlaylistDAOFactory = new PlaylistDAOFactory();
        PlaylistDAO pDAO = PlaylistDAOFactory.createPlaylistDAO();
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
       String menu="1- add a song\n " +
                "2- delete a song\n " +
                "3- see all songs\n" +
                "4- add songs in a new playlist\n" +
                "5- add songs in an existing playlist\n"+
                "6- add all songs from an artist to playlist\n"+
                "7- play the songs in the playlist\n"  +
                "8- Exit";
         System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        boolean quit=false;
        do{
        String next=scanner.next();
        switch (next){
            case "1":
                sDAO.add();
                System.out.println("Song added!");
                break;
            case "2":
                sDAO.delete();
                System.out.println("Song deleted!");
                break;

            case "3":
                System.out.println("Your songs:");
                for(Song s:sDAO.getAllSongs())
                    System.out.println(s);
                break;
            case "4":
                pDAO.addToPlaylist();
                //System.out.println("Song/s added!");
                break;
            case "5":
                pDAO.addToAnExistingPlaylist();
                //System.out.println("Song/s added!");
                break;
            case "6":
                pDAO.addAllToPlaylist();
                //System.out.println("Song/s added!");
                break;
            case "8":
                quit=true;
                System.out.println("You exit the program!");
                break;
            default:
                System.out.println("No avaliable option for this choice!");

        }

        } while(!quit);
    }}









