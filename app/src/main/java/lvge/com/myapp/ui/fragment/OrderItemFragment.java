package lvge.com.myapp.ui.fragment;

import android.os.Bundle;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.util.LogUtil;
import lvge.com.myapp.view.LoadingLayout;

public class OrderItemFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.fl_loading)
    LoadingLayout mFlLoading;
    private OrderType orderType;

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
        mFlLoading.setStatus(LoadingLayout.Loading);
        mRecyclerView.setLoadingListener(this);
    }

    public void getData() {


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
