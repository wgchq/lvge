package lvge.com.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.http.DefaultSubscriber;
import lvge.com.myapp.http.api.OrderService;
import lvge.com.myapp.model.base.PageResultModel;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.ui.binder.OrderItemCashBinder;
import lvge.com.myapp.ui.binder.OrderItemCommonBinder;
import lvge.com.myapp.util.LogUtil;
import lvge.com.myapp.view.LoadingLayout;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class OrderItemFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.fl_loading)
    LoadingLayout mFlLoading;
    private OrderType orderType;
    private PageResultModel<OrderItemModel> mPageResultModel = new PageResultModel<>();
    private List<OrderItemModel> mList;
    private MultiTypeAdapter mAdapter;

    //    "全 部","待验证","待发货","待安装","收银台,"退款/售后","派送中","已完成"
    public enum OrderType implements Serializable {

        ALL(0, "全 部"),
        WAIT_VERIFY(1, "待验证"),
        WAIT_SEND(3, "待发货"),
        WAIT_INSTALL(-1, "待安装"),
        CASH_REGISTER(-2, "收银台"),
        AFTER_SALE(-3, "退款/售后"),
        SENDING(4, "派送中"),
        FINISHED(-4, "已完成");

        public int orderListStatus;
        public String name;

        OrderType(int orderListStatus, String name) {
            this.orderListStatus = orderListStatus;
            this.name = name;
        }

    }

    public static OrderItemFragment newInstance(OrderType orderType) {

        Bundle args = new Bundle();
        args.putSerializable("orderType", orderType);
        OrderItemFragment fragment = new OrderItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order_item_page;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        orderType = (OrderType) getArguments().getSerializable("orderType");
        getData();
    }

    @Override
    public void configViews() {

        if (mList == null)
            return;
        if(mList.size() == 0){
            mFlLoading.setStatus(LoadingLayout.Empty);
        }else {
            mRecyclerView.setLoadingListener(this);
            mAdapter = new MultiTypeAdapter();
            mAdapter.register(OrderItemModel.class)
                    .to(new OrderItemCommonBinder(mActivity),new OrderItemCashBinder())
                    .withClassLinker(new ClassLinker<OrderItemModel>() {
                        @NonNull
                        @Override
                        public Class<? extends ItemViewBinder<OrderItemModel, ?>> index(@NonNull OrderItemModel orderItemModel) {
                            if (orderItemModel.status == OrderItemModel.CASH_REGISTER){
                                return OrderItemCashBinder.class;
                            }
                            return OrderItemCommonBinder.class;
                        }
                    });
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    public void getData() {
        Map<String,Object> map = new HashMap<>(4);
        map.put("status","0");
        map.put("pageIndex",1);
        map.put("pageSize",10);

        OrderService.getOrderList(map)
                .subscribe(new DefaultSubscriber<PageResultModel<OrderItemModel>>(mFlLoading) {
                    @Override
                    public void onSucced(PageResultModel<OrderItemModel> result) {
                        mPageResultModel.loadRecords(result);
                        OrderItemFragment.this.mList = mPageResultModel.getEntityList();
                        configViews();
                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i(TAG, "isVisibleToUser: " + isVisibleToUser);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {

    }
}
