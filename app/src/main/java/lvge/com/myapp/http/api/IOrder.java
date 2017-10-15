package lvge.com.myapp.http.api;

import java.util.Map;

import io.reactivex.Flowable;
import lvge.com.myapp.model.order.OrderDetailModel;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.model.base.BaseResponse;
import lvge.com.myapp.model.base.PageResultModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by android on 2017/10/12.
 */

public interface IOrder {
    @POST("sellerapp/order/queryPage")
    Flowable<BaseResponse<PageResultModel<OrderItemModel>>> getOderList(@Body Map<String, Object> map);

    @GET("sellerapp/order/detailByOrderNO")
    Flowable<BaseResponse<OrderDetailModel>> getOrderDetail(@Query("orderNO")String orderNo);
}
