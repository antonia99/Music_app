import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException
    {  SongDAOFactory songDAOFactory = new SongDAOFactory();
        SongDAO sDAO = songDAOFactory.createSongDAO();
        PlaylistDAOFactory PlaylistDAOFactory = new PlaylistDAOFactory();
        PlaylistDAO pDAO = PlaylistDAOFactory.createPlaylistDAO();

       String menu="1- add a song\n " +
                "2- delete a song\n " +
                "3- see all songs\n" +
                "4- add songs in a new playlist\n" +
                "5- add songs in an existing playlist\n"+
                "6- add all songs from an artist to an existing playlist\n"+
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
            case "7":
                pDAO.playSongs();
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









