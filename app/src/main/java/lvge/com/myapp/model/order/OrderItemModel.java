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

    private int freight;
    private OrderGoodsModel orderListGoods;
    private String orderNO;
    private String statusMemo;
    private int totalPrice;

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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
