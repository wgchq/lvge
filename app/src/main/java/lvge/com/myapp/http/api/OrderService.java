package lvge.com.myapp.http.api;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import lvge.com.myapp.http.HttpFactory;
import lvge.com.myapp.http.NetworkConfig;
import lvge.com.myapp.model.base.PageResultModel;
import lvge.com.myapp.model.order.LogisticCompanyModel;
import lvge.com.myapp.model.order.OrderDetailModel;
import lvge.com.myapp.model.order.OrderItemModel;

/**
 * Created by android on 2017/10/12.
 */

public class OrderService {
    /**
     * 获取订单列表
     * @param map
     * @return
     */
    public static Flowable<PageResultModel<OrderItemModel>> getOrderList(Map<String,Object> map){
        return getOrderService().getOderList(map)
                .compose(HttpFactory.<PageResultModel<OrderItemModel>>applaySchedulers());

    }

    /**
     * 获取订单详情
     * @param orderNo
     * @return
     */
    public static Flowable<OrderDetailModel> getOrderDetail(@NonNull String orderNo){
        return getOrderService().getOrderDetail(orderNo)
                .compose(HttpFactory.<OrderDetailModel>applaySchedulers());

    }

    /**
     * 获取快递公司列表
     * @return
     */
    public static Flowable<List<LogisticCompanyModel>> getLogisticCompany(){
        return getOrderService().getLogisticCompany()
                .compose(HttpFactory.<List<LogisticCompanyModel>>applaySchedulers());

    }

    public static Flowable<Object> saveLogistics(Map<String,Object> map){
        return getOrderService().saveLogistics(map)
                .compose(HttpFactory.<Object>applaySchedulers());

    }

    private static IOrder getOrderService() {
        return HttpFactory.getInstance().build(NetworkConfig.BASE_URL)
                .create(IOrder.class);
    }
}
