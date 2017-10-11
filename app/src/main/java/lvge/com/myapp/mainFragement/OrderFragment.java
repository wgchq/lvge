package lvge.com.myapp.mainFragement;


import android.app.Fragment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.model.OrderTabItem;
import lvge.com.myapp.ui.adapter.SimpleFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.toolbar_order_management)
    Toolbar mToolbarOrderManagement;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<OrderTabItem> mTabItemList = new ArrayList<>(10);
    private String[] test = {"全部","待验证","待发货","待安装","收银台","退款/售后","派送中","已完成"};
    private SimpleFragmentAdapter mAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        for (String s:test){
            mTabItemList.add(new OrderTabItem(s,0));
        }
    }

    @Override
    public void configViews() {
        mAdapter = new SimpleFragmentAdapter(mActivity, mTabItemList, getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);
//        mViewPager.requestDisallowInterceptTouchEvent(true);
//        mTabLayout.requestDisallowInterceptTouchEvent(true);
        mTabLayout.setupWithViewPager(mViewPager);
        /*mTabLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                LogUtil.i(TAG,"event.getX(): "+event.getX());
                return true;
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
