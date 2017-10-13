package lvge.com.myapp.model.order;

import java.io.Serializable;

/**
 * Created by android on 2017/10/13.
 */

public class OrderGoodsModel implements Serializable{
    /**
     * goodsID : 71514
     * goodsName : wt7NG5Pya1
     * goodsNum : 94612
     * goodsPrice : YHDdP32ugk
     * imgPath : ddgbt6JMO9
     */

    private int goodsID;
    private String goodsName;
    private int goodsNum;
    private double goodsPrice;
    private String imgPath;

    public int getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(int goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
