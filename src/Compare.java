import java.util.Comparator;

public class Compare implements Comparator<Song> {

    @Override
    public int compare(Song o1, Song o2) {
        if(o1.getId()>o2.getId())
            return 1;
        if(o1.getId()<o2.getId())
            return -1;
        return 0;
    }
}
