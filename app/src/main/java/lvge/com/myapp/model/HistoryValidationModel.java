package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-09-17.
 */

public class HistoryValidationModel {

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

    public int getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(int ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getSELLER_ID() {
        return SELLER_ID;
    }

    public void setSELLER_ID(int SELLER_ID) {
        this.SELLER_ID = SELLER_ID;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }

    public String getCODE_STATUS() {
        return CODE_STATUS;
    }

    public void setCODE_STATUS(String CODE_STATUS) {
        this.CODE_STATUS = CODE_STATUS;
    }

    public String getOPERATOR_NAME() {
        return OPERATOR_NAME;
    }

    public void setOPERATOR_NAME(String OPERATOR_NAME) {
        this.OPERATOR_NAME = OPERATOR_NAME;
    }

    public String getUSE_TIME() {
        return USE_TIME;
    }

    public void setUSE_TIME(String USE_TIME) {
        this.USE_TIME = USE_TIME;
    }

    public int getORDER_GOODS_ID() {
        return ORDER_GOODS_ID;
    }

    public void setORDER_GOODS_ID(int ORDER_GOODS_ID) {
        this.ORDER_GOODS_ID = ORDER_GOODS_ID;
    }

    private int GOODS_NUM = 0;
    private String CREATE_TIME = "";
    private double ORDER_PRICE = 0.0;
    private int ORDER_ID = 0;
    private String GoodsName = "";
    private int SELLER_ID = 0;
    private String OrderNO = "";
    private String CODE_STATUS = "";
    private String OPERATOR_NAME = "";
    private String USE_TIME = "";
    private int ORDER_GOODS_ID = 0;


}
