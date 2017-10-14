package lvge.com.myapp.model.order;

import java.io.Serializable;

/**
 * Created by android on 2017/10/12.
 */

public class OrderItemModel implements Serializable {

    /**
     * freight : 14544
     * orderListGoods : {"goodsID":71514,"goodsName":"wt7NG5Pya1","goodsNum":94612,"goodsPrice":"YHDdP32ugk","imgPath":"ddgbt6JMO9"}
     * orderNO : cZlDID42R6
     * statusMemo : Xht5Dp7Cgz
     * totalPrice : 77213
     */
//    ALL(0, "全 部"),
//    WAIT_VERIFY(1, "待验证"),
//    WAIT_SEND(3, "待发货"),
//    WAIT_INSTALL(-1, "待安装"),
//    CASH_REGISTER(-2, "收银台"),
//    AFTER_SALE(-3, "退款/售后"),
//    SENDING(4, "派送中"),
//    FINISHED(-4, "已完成");

    public static final int WAIT_VERIFY = 1;
    public static final int WAIT_SEND = 3;
    public static final int WAIT_INSTALL = -2;
    public static final int CASH_REGISTER = -3;
    public static final int SENDING = 4;
    public static final int FINISHED = -4;
    public int orderStatus;
    private int freight;
    private OrderGoodsModel orderListGoods;
    private String orderNO;
    private String statusMemo;
    private double totalPrice;

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public OrderGoodsModel getOrderListGoods() {
        return orderListGoods;
    }

    public void setOrderListGoods(OrderGoodsModel orderListGoods) {
        this.orderListGoods = orderListGoods;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getStatusMemo() {
        return statusMemo;
    }

    public void setStatusMemo(String statusMemo) {
        this.statusMemo = statusMemo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
