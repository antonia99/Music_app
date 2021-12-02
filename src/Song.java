import java.util.ArrayList;

public class Song {
    private int id;
    private String artist;
    private String title;
    private float duration;
    private ArrayList<String> type=new ArrayList<>();
    private String link;
    public Song(int id, String artist, String title, float duration, ArrayList<String> type, String link) {
        super();
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.type = type;
        this.link = link;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getArtist() {
        return artist;
    }


    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public float getDuration() {
        return duration;
    }


    public void setDuration(float duration) {
        this.duration = duration;
    }


    public ArrayList<String> getType() {
        return type;
    }


    public void setType(ArrayList<String> type) {
        this.type = type;
    }


    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public String toString() {
        return "Song [id=" + id + ", artist=" + artist + ", title=" + title + ", duration=" + duration + ", type=" + type
                + ", link=" + link + "]";
    }
}
