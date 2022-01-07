public class Playlist {
    // members
    private int id;
    private String name;
    private int duration;

    // constructor
    public Playlist(int id, String name, int duration) {
        super();
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    // methods
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public double getDuration() {
        return duration;
    }
}
