package lvge.com.myapp.ui.fragment;

import android.os.Bundle;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.util.LogUtil;
import lvge.com.myapp.view.LoadingLayout;

public class OrderItemFragment extends BaseFragment implements XRecyclerView.LoadingListener{
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.fl_loading)
    LoadingLayout mFlLoading;

    public static OrderItemFragment newInstance() {

        Bundle args = new Bundle();

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
        LogUtil.i(TAG,"isVisibleToUser: "+isVisibleToUser);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {

    }
}
