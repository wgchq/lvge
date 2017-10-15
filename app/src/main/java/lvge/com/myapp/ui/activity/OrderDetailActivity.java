package lvge.com.myapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseActivity;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.ui.fragment.SendGoodsDetailFragment;


public class OrderDetailActivity extends BaseActivity {
    OrderItemModel mModel;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;

    @Override
    public void initDatas() {
        getIntent().getSerializableExtra("OrderItemModel");
    }

    @Override
    public void configViews() {
        mTvTitle.setText("订单详情");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BaseFragment fragment = null;
        switch (mModel.status) {
            case OrderItemModel.WAIT_SEND:
                fragment = new SendGoodsDetailFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("OrderItemModel", mModel);
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

}
