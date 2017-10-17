package lvge.com.myapp.ui.activity;

import android.app.Dialog;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wx.wheelview.widget.WheelView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseActivity;
import lvge.com.myapp.http.DefaultSubscriber;
import lvge.com.myapp.http.api.OrderService;
import lvge.com.myapp.manager.CacheManager;
import lvge.com.myapp.model.order.LogisticCompanyModel;
import lvge.com.myapp.model.order.OrderDetailModel;
import lvge.com.myapp.ui.adapter.LogisticWheelAdapter;
import lvge.com.myapp.util.GsonUtil;
import lvge.com.myapp.view.LoadingLayout;

public class OrderShippingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ll_delvier_way)
    LinearLayout mLlDelvierWay;
    @BindView(R.id.tv_deliver_order_number)
    EditText mTvDeliverOrderNumber;
    @BindView(R.id.tv_contact)
    TextView mTvContact;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_delvier)
    TextView mTvDelvier;
    @BindView(R.id.ll_loading)
    LoadingLayout mLlLoading;
    private OrderDetailModel model;
    private List<LogisticCompanyModel> logisticCompany;
    private LogisticCompanyModel selectedLogistic;
    @Override
    public void initDatas() {
        model = (OrderDetailModel) getIntent().getSerializableExtra("OrderDetailModel");
        logisticCompany = CacheManager.getLogisticCompany();
        if (logisticCompany == null){
            getLogistic();
        }
    }

    @Override
    public void configViews() {
        mTvTitle.setText("发货");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_shipping;
    }

    @OnClick(R.id.ll_delvier_way)
    public void selectDelivery() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(this).inflate(R.layout.dailog_deliver_pick, null);

        final WheelView wheelView = (WheelView) view.findViewById(R.id.wheelView);
        wheelView.setSkin(WheelView.Skin.Holo);
        wheelView.setWheelAdapter(new LogisticWheelAdapter(this,logisticCompany));
        wheelView.setWheelSize(3);

        //取消
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                selectedLogistic = logisticCompany.get(wheelView.getCurrentPosition());


            }
        });

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.setContentView(view);
        dialog.show();
    }
    @OnClick(R.id.btn_send)
    public void sureSend(){

        Map<String,Object> map = new HashMap<>(3);
        map.put("logisticCompanyID",selectedLogistic.getCompanyCode());
        map.put("orderNO",model.getOrderNO());
        map.put("logisticNO",selectedLogistic.getLogisticID());
        OrderService.saveLogistics(map)
                .subscribe(new DefaultSubscriber<Object>(){

                    @Override
                    public void onSucced(Object result) {
                        // TODO: 2017/10/17 提交发送成功
                    }
                });
    }
    private void getLogistic() {
        OrderService.getLogisticCompany()
                .subscribe(new DefaultSubscriber<List<LogisticCompanyModel>>(mLlLoading) {
                    @Override
                    public void onSucced(List<LogisticCompanyModel> result) {
                        if (result != null){
                            logisticCompany = result;
                            CacheManager.saveLogisticCompany(GsonUtil.toJson(result));
                        }
                    }
                });
    }
}
