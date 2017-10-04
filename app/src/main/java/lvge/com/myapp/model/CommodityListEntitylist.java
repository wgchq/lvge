package lvge.com.myapp.model;

/**
 * Created by mac on 2017/9/26.
 */

public class CommodityListEntitylist {
    private String goodsId;
    private int stock;
    private double price;
    private String name;
    private int saleCount;
    private String mainPicPath;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public String getMainPicPath() {
        return mainPicPath;
    }

    public void setMainPicPath(String mainPicPath) {
        this.mainPicPath = mainPicPath;
    }
}
