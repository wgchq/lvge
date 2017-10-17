package lvge.com.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.http.DefaultSubscriber;
import lvge.com.myapp.http.api.OrderService;
import lvge.com.myapp.model.order.OrderDetailModel;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.view.LoadingLayout;


public abstract class BaseOrderDetailFragment extends BaseFragment {

    protected OrderItemModel m;
    protected OrderDetailModel model;


    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
        m = (OrderItemModel) bundle.getSerializable("OrderItemModel");
        getData(getLoadingLayout());
    }

    protected void getData(LoadingLayout mLlLoading) {
        OrderService.getOrderDetail(m.getOrderNO())
                .subscribe(new DefaultSubscriber<OrderDetailModel>(mLlLoading) {
                    @Override
                    public void onSucced(OrderDetailModel result) {
                        model = result;
                        configViews();
                    }
                });
    }

    public abstract @NonNull LoadingLayout getLoadingLayout();
}
