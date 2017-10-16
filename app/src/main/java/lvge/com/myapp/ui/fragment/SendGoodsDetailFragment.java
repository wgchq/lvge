package lvge.com.myapp.ui.fragment;

import android.widget.TextView;

import butterknife.BindView;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.view.GoodsDesView;

/**
 * Created by android on 2017/10/11.
 */

public class SendGoodsDetailFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
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


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sendgoods;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

}
