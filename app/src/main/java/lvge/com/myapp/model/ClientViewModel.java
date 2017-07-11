package lvge.com.myapp.model;

/**
 * Created by JGG on 2017/7/12.
 */

public class ClientViewModel {

    private int id ;
    private String phone;
    private String headImg;
    private String vin;
    private String hasTerminalID;
    private double mileAge;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getHasTerminalID() {
        return hasTerminalID;
    }

    public void setHasTerminalID(String hasTerminalID) {
        this.hasTerminalID = hasTerminalID;
    }

    public double getMileAge() {
        return mileAge;
    }

    public void setMileAge(double mileAge) {
        this.mileAge = mileAge;
    }





}
