package lvge.com.myapp.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lvge.com.myapp.R;
import lvge.com.myapp.http.DefaultSubscriber;
import lvge.com.myapp.http.ErrorChecker;
import lvge.com.myapp.http.api.OrderService;
import lvge.com.myapp.manager.CacheManager;
import lvge.com.myapp.model.order.LogisticCompanyModel;
import lvge.com.myapp.ui.activity.OrderShippingActivity;
import lvge.com.myapp.util.GsonUtil;
import lvge.com.myapp.util.ImageLoadUtil;
import lvge.com.myapp.util.order.OrderStatusUtil;
import lvge.com.myapp.util.order.PayWayUtil;
import lvge.com.myapp.view.CircleImageView;
import lvge.com.myapp.view.GoodsDesView;
import lvge.com.myapp.view.LoadingLayout;

/**
 * Created by android on 2017/10/11.
 */

public class SendGoodsDetailFragment extends BaseOrderDetailFragment {
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
    @BindView(R.id.ll_loading)
    LoadingLayout mLlLoading;
    @BindView(R.id.iv_header)
    CircleImageView mIvHeader;
    @BindView(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @BindView(R.id.btn_send)
    Button mBtnSend;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sendgoods;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        super.initDatas();
        getLogistic();
    }

    @NonNull
    @Override
    public LoadingLayout getLoadingLayout() {
        return mLlLoading;
    }

    @Override
    public void configViews() {
        ImageLoadUtil.loadHeader(this, model.getCustomerHeadIconPath(), mIvHeader);
        mTvName.setText(model.getCustomerName());
        mTvPhone.setText(model.getCustomerPhone());
        mTvAddress.setText(String.format("收货地址：%s", model.getDetailAddress()));

        mGoodsDesView.setHeader(model.getPICTURE_PATH());
//        mGoodsDesView.setMoney(model.get);//缺少商品单价
        mGoodsDesView.setCount(model.getGOODS_NUM());
        mGoodsDesView.setPriceDes(model.getORDER_PAY_PRICE(), model.getGOODS_NUM(), model.getFreight());

        mTvPayMoney.setText("￥" + model.getORDER_PAY_PRICE());
        mTvOrderNumber.setText(model.getOrderNO());
        mTvOrderTime.setText(model.getCREATE_TIME());
//        mTvInstallType.setText();//缺少安装类型
        mTvRemark.setText(model.getMemo());
        mTvBillInfo.setText(model.getReceipt_Title());
        mTvStatus.setText(OrderStatusUtil.getStatus(model.getORDER_STATUS()));//状态未定义
        mTvPayWay.setText(PayWayUtil.getPayWay(Integer.parseInt(model.getPAY_TYPE())));
    }

    @OnClick(R.id.btn_send)
    public void send() {
        Intent intent = new Intent(getActivity(), OrderShippingActivity.class);
        intent.putExtra("OrderDetailModel",model);
        startActivity(intent);
    }

    /**
     * 静静地获取快递公司列表
     */
    private void getLogistic() {
        OrderService.getLogisticCompany()
                .subscribe(new DefaultSubscriber<List<LogisticCompanyModel>>(ErrorChecker.SILENCE) {
                    @Override
                    public void onSucced(List<LogisticCompanyModel> result) {
                        if (result != null){
                            CacheManager.saveLogisticCompany(GsonUtil.toJson(result));
                        }
                    }
                });
    }
}
