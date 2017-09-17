package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-09-17.
 */

public class HistoryValidationModel {

    private int GOODS_NUM = 0;
    private String CREATE_TIME = "";
    private double ORDER_PRICE = 0.0;
    private int OrderNO = 0;
    private String GoodsName = "";
    private String OPERATOR_NAME = "";


    public int getGOODS_NUM() {
        return GOODS_NUM;
    }

    public void setGOODS_NUM(int GOODS_NUM) {
        this.GOODS_NUM = GOODS_NUM;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public double getORDER_PRICE() {
        return ORDER_PRICE;
    }

    public void setORDER_PRICE(double ORDER_PRICE) {
        this.ORDER_PRICE = ORDER_PRICE;
    }

    public int getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(int orderNO) {
        OrderNO = orderNO;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getOPERATOR_NAME() {
        return OPERATOR_NAME;
    }

    public void setOPERATOR_NAME(String OPERATOR_NAME) {
        this.OPERATOR_NAME = OPERATOR_NAME;
    }


}
