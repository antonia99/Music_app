import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO{
    public static final String CONNECTION_URL = "jdbc:mysql://localhost/music_app";
    public Scanner scan = new Scanner(System.in);

    @Override
    public void addToPlaylist() {

            //int id = Integer.parseInt(scan.nextLine());

            try {Connection conn = getConnection();
                System.out.print("Playlist name: ");
                String name = scan.nextLine();
                if(existPlaylist(conn,name)==true){
                String ans = "y";
                while (ans.equals("y")){
                    System.out.print("artist: ");
                String artist = scan.nextLine();
                System.out.print("title: ");
                String title = scan.nextLine();
                if(existSong(conn,name,artist,title)==true){
                PreparedStatement st = conn.prepareStatement("select id from songs where artist=? and title=?", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, artist);
                st.setString(2, title);
                ResultSet rs = st.executeQuery();
                boolean noBreak = true;
                int id = 0;
                PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                while (rs.next()) {
                    id = rs.getInt("id");
                    noBreak = false;
                }
                if (noBreak == false) {
                    ps.setString(1, name);
                    ps.setInt(2, id);

                    int affectedRows = ps.executeUpdate();
                    System.out.println("Song added!");

                } else System.out.println("Song/Artist doesn't exist!");

            }else System.out.println("Song already exist in playlist!");
                System.out.println("Do you want to add songs from another artist? y/n:");
                    ans= scan.nextLine();
                    if (ans.equals("n")) {
                        System.out.println("Action ended!");}}
                    closeConnection(conn); }
                else System.out.println("Playlist name already exist!"); }
            catch (SQLException e) {
                e.printStackTrace();}


    }
    @Override
    public void addAllToPlaylist() {


        //int id = Integer.parseInt(scan.nextLine());

        try {
            Connection conn = getConnection();
            System.out.print("Playlist name: ");
            String name = scan.nextLine();
            if (existPlaylist(conn, name) == false) {
                String ans = "y";
                while (ans.equals("y")) {
                    System.out.print("artist: ");
                    String artist = scan.nextLine();
                    if (existArtist(conn, artist, name) == true) {
                        PreparedStatement st = conn.prepareStatement("select id from songs where artist=? ", Statement.RETURN_GENERATED_KEYS);
                        st.setString(1, artist);

                        ResultSet rs = st.executeQuery();
                        ArrayList<Integer> id_s = new ArrayList<Integer>();
                        PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                        boolean noBreak = true;
                        while (rs.next()) {

                            int id = rs.getInt("id");

                            id_s.add(Integer.valueOf(id));
                            noBreak = false;
                        }

                        if (noBreak == false) {

                            ps.setString(1, name);
                            for (Integer id : id_s) {
                                ps.setInt(2, id);
                                ps.addBatch();
                            }
                            ps.executeBatch();
                            System.out.println("Songs added!");

                        } else System.out.println("Artist doesn't exist!");

                    } else System.out.println("Songs from this artist already exist in this playlist!");
                    System.out.println("Do you want to add songs from another artist? y/n:");
                    ans = scan.nextLine();

                    if (ans.equals("n")) {
                        System.out.println("Action ended!");
                    }
                }
                closeConnection(conn);
            } else System.out.println("Playlist doesn't exist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToAnExistingPlaylist() {
        //int id = Integer.parseInt(scan.nextLine());
        try {Connection conn = getConnection();
            System.out.print("Playlist name: ");
            String name = scan.nextLine();
                PreparedStatement st = conn.prepareStatement("select name from playlist where name=? ", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, name);
                ResultSet rs = st.executeQuery();

                boolean noBreak = true;

                if (rs.next()) {
                    //String name_pl=rs.getString("name");
                    //names.add(String.valueOf(name_pl)) ;
                    noBreak = false;
                }
                if (noBreak == false) {
                    String ans = "y";
                    while (!(ans.equals("n"))) {
                        int id = 0;
                        boolean nobreak = true;
                        System.out.print("artist: ");
                        String artist = scan.nextLine();
                        System.out.print("title: ");
                        String title = scan.nextLine();
                        if(existSong(conn,name,artist,title)==true){
                        PreparedStatement at = conn.prepareStatement("select id from songs where artist=? and title=?", Statement.RETURN_GENERATED_KEYS);
                        at.setString(1, artist);
                        at.setString(2, title);
                        ResultSet bt = at.executeQuery();
                        //ArrayList<String>names=new ArrayList<String>();
                        PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                        while (bt.next()) {
                            id = bt.getInt("id");
                            nobreak = false;
                        }
                        if (nobreak == false) {
                            ps.setString(1, name);
                            ps.setInt(2, id);
                            int affectedRows = ps.executeUpdate();
                            System.out.println("Song added!");

                        } else System.out.println("Artist/song doesn't exist!");


                        // addSong(conn,name);
                    }else System.out.println("Song already exist!");
                        System.out.println("Do you want to add another song? y/n:");
                        ans = scan.nextLine();
                        if (ans.equals("n")) {
                            System.out.println("Action ended!");
                        }}


                } else System.out.println("Playlist doesn't exist!");
                closeConnection(conn)
                ;

            } catch (SQLException e) {
            e.printStackTrace();}
    }


    @Override
    public void playSongs() {
        try {
            Connection conn = getConnection();
            System.out.print("Playlist name: ");
            String name = scan.nextLine();
            if (existPlaylist(conn, name) == false) {
                PreparedStatement st = conn.prepareStatement("select id_s,link,title,duration from songs s,playlist p where s.id=p.id_s and p.name=? ", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, name);
                ResultSet rs = st.executeQuery();
                boolean noBreak = true;
                ArrayList<String> title=new ArrayList<>();
                ArrayList<String> link=new ArrayList<>();
                //ArrayList<Date> time=new ArrayList<>();
                while (rs.next()) {
                    String song=rs.getString("title");
                    String link_s=rs.getString("link");
                    //String name_pl=rs.getString("name");
                    //names.add(String.valueOf(name_pl)) ;
                    title.add(song);
                    link.add(link_s);
                    noBreak = false;
                }
                //System.out.println(link.size());

                if (noBreak == false) {
                    for(int i=0;i<link.size();i++){

                        System.out.println("Playing song:"+ title.get(i));
                        java.awt.Desktop.getDesktop().browse(java.net.URI.create(link.get(i)));
                    }
                }else System.out.println("Playlist is empty!");
            }else System.out.println("Playlist doesn't exist!");
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection () throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_URL, "root", "");
    }


    public void closeConnection (Connection conn) throws SQLException
    {
        conn.close();
    }
//   public void addSong(Connection conn,String name) throws SQLException {
//        int id=0;
//       boolean nobreak=true;
//       System.out.print("artist: ");
//       String artist = scan.nextLine();
//       System.out.print("title: ");
//       String title = scan.nextLine();
//       PreparedStatement at = conn.prepareStatement("select id from songs where artist=? and title=?", Statement.RETURN_GENERATED_KEYS);
//       at.setString(1, artist);
//       at.setString(2, title);
//       ResultSet bt = at.executeQuery();
//       //ArrayList<String>names=new ArrayList<String>();
//       PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
//       while (bt.next()) {
//           id = bt.getInt("id");
//           nobreak = false;
//       }
//       if (nobreak == false) {
//           ps.setString(1, name);
//           ps.setInt(2, id);
//           int affectedRows = ps.executeUpdate();
//           System.out.println("Song added!");
//       } else System.out.println("Artist/song doesn't exist!");
//       System.out.println("Do you want to add another song? y/n:");
//       String ans = scan.nextLine();
//   }
public boolean existPlaylist(Connection conn, String name) throws SQLException {
    PreparedStatement st = conn.prepareStatement("select name from playlist where name=? ", Statement.RETURN_GENERATED_KEYS);
    st.setString(1, name);
    ResultSet rs = st.executeQuery();
    //ArrayList<String> names=new ArrayList<String>();
    boolean noBreak=true;
    while (rs.next()) {
        //String name_pl=rs.getString("name");
       // names.add(String.valueOf(name_pl)) ;
        noBreak = false;
    }
    return noBreak;


}
public boolean existSong(Connection conn,String name,String artist,String title) throws SQLException {
    PreparedStatement st = conn.prepareStatement("select artist,title from songs,playlist where name=? and artist=? and title=? and songs.id=playlist.id_s", Statement.RETURN_GENERATED_KEYS);
    st.setString(1, name);
    st.setString(2,artist);
    st.setString(3,title);
    ResultSet rs = st.executeQuery();
    boolean noBreak=true;
    while (rs.next()) {
        //id = rs.getInt("id");
        noBreak = false;
    }
    return noBreak;
}
public boolean existArtist(Connection conn, String artist,String name) throws SQLException {
    PreparedStatement st = conn.prepareStatement("select artist from songs,playlist where name=? and artist=? and songs.id=playlist.id_s", Statement.RETURN_GENERATED_KEYS);
    st.setString(1, name);
    st.setString(2,artist);
    ResultSet rs = st.executeQuery();
    boolean noBreak=true;

    while (rs.next()) {
        noBreak = false;
    }

    return noBreak;
}

}
