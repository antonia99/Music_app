public class Playlist {
    public int id_pl;
    public String name;
    public int id_s;

    public Playlist(int id_pl, String name, int id_s) {
        this.id_pl = id_pl;
        this.name = name;
        this.id_s = id_s;
    }

    public int getId_pl() {
        return id_pl;
    }

    public String getName() {
        return name;
    }

    public int getId_s() {
        return id_s;
    }

    @Override
    public String toString() {
        return "Playlist:" + name + '\'' +
                "id song:" + id_s ;
    }

    public void setId_pl(int id_pl) {
        this.id_pl = id_pl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_s(int id_s) {
        this.id_s = id_s;
    }
}
