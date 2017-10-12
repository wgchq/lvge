package lvge.com.myapp.http.api;

import java.util.Map;

import io.reactivex.Flowable;
import lvge.com.myapp.model.OrderItemModel;
import lvge.com.myapp.model.base.BaseResponse;
import lvge.com.myapp.model.base.PageResultModel;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by android on 2017/10/12.
 */

public interface IOrder {
    @POST("/sellerapp/order/queryPage")
    Flowable<BaseResponse<PageResultModel<OrderItemModel>>> getOderList(@Body Map<String, Object> map);
}
