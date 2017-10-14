package lvge.com.myapp.http.api;

import java.util.Map;

import io.reactivex.Flowable;
import lvge.com.myapp.http.NetworkConfig;
import lvge.com.myapp.http.HttpFactory;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.model.base.PageResultModel;

/**
 * Created by android on 2017/10/12.
 */

public class OrderService {
    public static Flowable<PageResultModel<OrderItemModel>> getOrderList(Map<String,Object> map){
        return getOrderService().getOderList(map)
                .compose(HttpFactory.<PageResultModel<OrderItemModel>>applaySchedulers());

    }

    private static IOrder getOrderService() {
        return HttpFactory.getInstance().build(NetworkConfig.BASE_URL)
                .create(IOrder.class);
    }
}
