import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static SongDAOFactory songDAOFactory = new SongDAOFactory();
    static SongDAO sDAO = songDAOFactory.createSongDAO();

    static PlaylistDAOFactory playlistDAOFactory = new PlaylistDAOFactory();
    static PlaylistDAO playlistDAO = playlistDAOFactory.createPlaylistDAO();

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
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
                "4- add songs in playlist\n" +
                "5- add all songs from an artist to playlist\n"+
                "6- play the songs in the playlist\n"  +
                "7- Exit";
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
                showPlaylists();

                System.out.println("Select what you would like to do:\n" +
                        "1- Create a new playlist\n" +
                        "2- Add a song to an existing playlist");

                boolean validValue = false;
                while(validValue == false){
                    next = scanner.next();

                    if(next.equals("1")){
                        playlistDAO.createPlaylist();
                        validValue = true;
                    }
                    else
                    if(next.equals("2")){
                        System.out.println("Please enter the artist's name and the song's title.");
                        System.out.println("Artist name: ");
                        String name = scanner.next();
                        System.out.println("Song title: ");
                        String title = scanner.next();

                        Song foundSong = sDAO.getSong(name, title);

                        if(foundSong == null){
                            System.out.println("Song hasn't been found.");
                            break;
                        }
                        else
                        {
                            System.out.println("The song has been found.");
                        }

                        System.out.println("Please insert the playlist Id you want to add the song to.");
                        System.out.println("Id: ");
                        int playlistId = Integer.valueOf(scanner.next());

                        playlistDAO.add(foundSong.getId(), playlistId);

                        validValue = true;
                    }
                    else
                        System.out.println("Please enter a correct option");
                }
                break;

            case "5":
                System.out.println("Please select the playlist you want to add songs to:");
                showPlaylists();

                int playlistId = Integer.valueOf(scanner.next());
                Playlist selectedPlaylist = Arrays.stream(playlistDAO.getAllPlaylists()).filter(x -> x.getId() == playlistId).findAny().get();

                System.out.println("Playlist " + "'" + selectedPlaylist.getName() + "'" + " has been selected.");
                System.out.println("Please write the artist's name whose songs you want to add in the playlist:");

                String selectedArtistName = scanner.next();
                Song[] songs = sDAO.getAllSongsPerArtist(selectedArtistName);

                for(Song s: songs) {
                    playlistDAO.add(s.getId(), playlistId);
                }

                System.out.println("All songs from artist " + "'" + selectedArtistName + "'" + " have been added to the playlist "
                + "'" + selectedPlaylist.getName() + "'");

                break;

            case "6":
                showPlaylists();

                playlistId = Integer.valueOf(scanner.next());
                Song[] songList = sDAO.getAllSongsPerPlaylist(playlistId);

                for(Song song: songList){
                    sDAO.playSong(song.getId(), song.getLink());

                    System.out.println("The current song is playing. Please select the following:");
                    System.out.println("1- to proceed to the next song");
                    System.out.println("2- play this song on repeat");

                    boolean playNext = false;
                    validValue = false;
                    do{
                        if(playNext == true){
                            break;
                        }

                        String input = scanner.next();
                        if(input.equals("1")){
                            validValue = true;
                        }
                        else
                            if(input.equals("2")) {
                                System.out.println("Repeat how many times?");
                                int repeat = Integer.valueOf(scanner.next());
                                int iterations = 0;
                                while(repeat != iterations){
                                    sDAO.playSong(song.getId(), song.getLink());
                                    iterations++;

                                    System.out.println("The current song is playing on repeat. Please select the following:");
                                    System.out.println("1- to proceed to the next song");
                                    System.out.println("2- wait for the song to end");

                                    input = scanner.next();

                                    if(input.equals("1"))
                                    {
                                        iterations = repeat;
                                        playNext = true;
                                    }else
                                        if(input.equals("2")){
                                            Thread.sleep(Long.valueOf((long) song.getDuration()) * 1000);
                                        }
                                }
                            }
                            else
                                System.out.println("No available option for this choice!");
                    }while(validValue == false);
                }
                break;

            case "7":
                quit=true;
                System.out.println("You exit the program!");
                break;

            default:
                System.out.println("No available option for this choice!");
                System.out.println(menu);

        }

        } while(!quit);
    }

    public static void showPlaylists() throws SQLException {
        System.out.println("Your playlists:");
        for(Playlist p: playlistDAO.getAllPlaylists())
        {
            System.out.println("Id: " + p.getId() + " | " + "Name: " + p.getName() + " | " + "Duration: " + p.getId());
        }
    }
}









