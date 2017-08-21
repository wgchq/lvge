package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-08-22.
 */


public class SosInfor {
    private String meliage; //公里数
    private String voltage; //电压
    private String speed; //速度
    private String engine; //引擎 0-未知，1-启动，2-熄火
    private String gps; //gps卫星星数
    private String lng; //22.111111,
    private String lat; //11.111111,
    private String acc;//acc状态 0-未知，1-打开，2-关闭

    public String getMeliage() {
        return meliage;
    }

    public void setMeliage(String meliage) {
        this.meliage = meliage;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

}