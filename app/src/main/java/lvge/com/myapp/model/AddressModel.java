package lvge.com.myapp.model;

/**
 * Created by JGG on 2017/7/15.
 */

public class AddressModel {
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    private double longitude;//经度
    private double latitude;//纬度
    private String title;//信息标题
    private String text;//信息内容

    public AddressModel(double lon, double lat, String title, String text) {
        this.longitude = lon;
        this.latitude = lat;
        this.title = title;
        this.text = text;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
