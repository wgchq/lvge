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
//    订单状态
//0、待付款
//1、待验证
//2、交易关闭
//3、待发货
//4、待收货
//5、退款中
//6、退款拒绝
//7、已退款
//8、取消订单
//9、待安装


    public static final int WAIT_PAY = -1;
    public static final int WAIT_VERIFY = 0;
    public static final int FACKED_OF = 2;
    public static final int WAIT_SEND = 1;
    public static final int WAIT_RECEIVE = 4;
    public static final int WAIT_INSTALL = 2;
    public static final int REFUNDING = 7; //退款中
    public static final int REFUSED = 8; //拒绝退款
    public static final int REFUNDED = 9; //已退款
//    public static final int CANCELED = 8; //取消订单
    public static final int SENDING = 5;
    public static final int FINISHED = 6;
    public static final int CASH_REGISTER = 3;

    public int status;
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
