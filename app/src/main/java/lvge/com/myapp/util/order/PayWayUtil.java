package lvge.com.myapp.util.order;

/**
 * Created by android on 2017/10/17.
 */

public class PayWayUtil {
    public static String getPayWay(int type){
        switch (type){
            case 1:
                return "微信支付";
            default:
                return "支付宝支付";
        }
    }
}
