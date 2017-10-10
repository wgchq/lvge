package lvge.com.myapp.view;

/**
 * Created by JGG on 2017/7/12.
 */

public class ListviewItem {

    private String name;
    private int imageid;

    public ListviewItem(String name, int imageid) {
        super();
        this.name = name;
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public int getImageid() {
        return imageid;
    }

}
