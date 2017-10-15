package lvge.com.myapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseFragment;
import lvge.com.myapp.http.DefaultSubscriber;
import lvge.com.myapp.http.api.OrderService;
import lvge.com.myapp.model.order.OrderDetailModel;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.modules.validationtypescanqr.MipcaActivityCaptureActivity;
import lvge.com.myapp.view.GoodsDesView;
import lvge.com.myapp.view.LoadingLayout;


public class ScanVerifyFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.goodsDesView)
    GoodsDesView mGoodsDesView;
    @BindView(R.id.tv_memo)
    TextView mTvMemo;
    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.ll_loading)
    LoadingLayout mLlLoading;
    @BindView(R.id.ll_scan)
    LinearLayout mLlScan;
    Unbinder unbinder;
    private OrderItemModel m;
    private OrderDetailModel model;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scanverify;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
        m = (OrderItemModel) bundle.getSerializable("OrderItemModel");
        getData();
    }

    @Override
    public void configViews() {
        mTvName.setText(model.getCUSTOMER_NAME());
        mTvPhone.setText(model.getPHONE());
        mGoodsDesView.setHeader(model.getGoodsName());
        mGoodsDesView.setCount(model.getGOODS_NUM());
        mGoodsDesView.setMoney(model.getGOODS_PRICE());
        mGoodsDesView.setPriceDes(model.getORIGINAL_PRICE(), model.getGOODS_NUM(), model.getFreight());
        //        mTvMemo.setText();
        mTvOrderNo.setText(model.getOrderNO());
        //        mTvDate.setText();

    }

    public void getData() {
        OrderService.getOrderDetail(m.getOrderNO())
                .subscribe(new DefaultSubscriber<OrderDetailModel>(mLlLoading) {
                    @Override
                    public void onSucced(OrderDetailModel result) {
                        model = result;
                        configViews();
                    }
                });
    }

    @OnClick(R.id.ll_scan)
    public void scan() {
        scanBarcode();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {

            if (intentResult.getContents() == null) {
                if(resultCode ==-1){
                    Toast.makeText(getActivity(), "无法获取内容", Toast.LENGTH_SHORT).show();
                }
                else if (resultCode==2) {
//                    validation_keyboard.show();

                }

            } else {
                String str_qr =  intentResult.getContents();
//                EditText validation_code_input = (EditText)view.findViewById(R.id.validation_code_input);
//                validation_code_input.setText(str_qr);
            }
        }
    }
    public void scanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setPrompt("请对准二维码进行扫描"); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        // integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(MipcaActivityCaptureActivity.class); // 设置自定义的activity是CustomActivity

        integrator.initiateScan();// 初始化扫描

    }
}
