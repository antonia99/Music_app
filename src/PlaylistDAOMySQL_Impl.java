import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO{
    public static final String CONNECTION_URL = "jdbc:mysql://localhost/music_app";
    public Scanner scan = new Scanner(System.in);

    @Override
    public void addToPlaylist() {
            System.out.print("Playlist name: ");
            String name = scan.nextLine();
             System.out.print("artist: ");
             String artist = scan.nextLine();
             System.out.print("title: ");
             String title = scan.nextLine();

            //int id = Integer.parseInt(scan.nextLine());

            try {Connection conn = getConnection();
                    PreparedStatement st = conn.prepareStatement("select id from songs where artist=? and title=?", Statement.RETURN_GENERATED_KEYS);
                    st.setString(1, artist);
                    st.setString(2, title);
                    ResultSet rs = st.executeQuery();
                   boolean  noBreak=true;
                   ArrayList<Integer>id_s=new ArrayList<Integer>();
                PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                while (rs.next()) {
                    String id=rs.getString("id");
                     id_s.add(Integer.valueOf(id)) ;

                     noBreak=false;
                }
                if(noBreak==false){
                    ps.setString(1, name);
                    ps.setString(2, String.valueOf(id_s));

                    int affectedRows = ps.executeUpdate();
                    System.out.println("Song added!");}
               else System.out.println("Song/Artist doesn't exist!");
                ;
                closeConnection(conn);

            } catch (SQLException e) {
                e.printStackTrace();}


    }
    @Override
    public void addAllToPlaylist() {
        System.out.print("Playlist name: ");
        String name = scan.nextLine();
        System.out.print("artist: ");
        String artist = scan.nextLine();
        //int id = Integer.parseInt(scan.nextLine());

        try {Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement("select id from songs where artist=? ", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, artist);

            ResultSet rs = st.executeQuery();
            ArrayList<Integer>id_s=new ArrayList<Integer>();
            PreparedStatement ps = conn.prepareStatement("insert into playlist(name,id_s) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
            boolean noBreak=true;
            while(rs.next()){

                String id=rs.getString("id");

                id_s.add(Integer.valueOf(id)) ;
                noBreak=false;
            }
            if(noBreak==false){
            ps.setString(1, name);
            ps.setString(2, String.valueOf(id_s));

            int affectedRows = ps.executeUpdate();
            System.out.println("Songs added!");}
            else System.out.println("Artist doesn't exist!");

            ;
            closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();}
    }

    @Override
    public void playSongs() {

    }

    public Connection getConnection () throws SQLException
    {
        return DriverManager.getConnection(CONNECTION_URL, "root", "");
    }


    public void closeConnection (Connection conn) throws SQLException
    {
        conn.close();
    }
}
