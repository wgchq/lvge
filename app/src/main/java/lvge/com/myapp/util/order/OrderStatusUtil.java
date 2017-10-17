package lvge.com.myapp.util.order;

/**
 * Created by android on 2017/10/17.
 */

public class OrderStatusUtil {
    public static String getStatus(int status){
        switch (status){
            case 1:
                return "已完成";
            default:
                return "代发货";
        }
    }
}
