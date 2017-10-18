package lvge.com.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lvge.com.myapp.R;
import lvge.com.myapp.view.CircleImageView;
import lvge.com.myapp.view.GoodsDesView;
import lvge.com.myapp.view.LoadingLayout;

/**
 * Created by android on 2017/10/18.
 */

public class OrderSendedDetailFragment extends BaseOrderDetailFragment {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_header)
    CircleImageView mIvHeader;
    @BindView(R.id.goodsDesView)
    GoodsDesView mGoodsDesView;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_order_time)
    TextView mTvOrderTime;
    @BindView(R.id.tv_install_type)
    TextView mTvInstallType;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
    @BindView(R.id.tv_bill_info)
    TextView mTvBillInfo;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_pay_way)
    TextView mTvPayWay;
    @BindView(R.id.tv_sender)
    TextView mTvSender;
    @BindView(R.id.tv_send_type)
    TextView mTvSendType;
    @BindView(R.id.tv_send_no)
    TextView mTvSendNo;
    @BindView(R.id.tv_send_time)
    TextView mTvSendTime;

    @BindView(R.id.ll_loading)
    LoadingLayout mLlLoading;
    @BindView(R.id.ll_send_detail)
    LinearLayout mLlSendDetail;
    Unbinder unbinder;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sended_detail;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        getData(mLlLoading);
    }

    @Override
    public void attachView() {
    }

    @Override
    public void configViews() {

    }

    @NonNull
    @Override
    public LoadingLayout getLoadingLayout() {
        return mLlLoading;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 查看物流详情
     */
    @OnClick(R.id.ll_send_detail)
    public void checkSendingDetail(){

    }



}
