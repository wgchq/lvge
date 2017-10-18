package lvge.com.myapp.ui.activity;

import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseActivity;
import lvge.com.myapp.model.order.OrderDetailModel;

/**
 * Created by android on 2017/10/18.
 */

public class VerifySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_return_main)
    Button mBtnReturnMain;
    @BindView(R.id.btn_check_detail)
    Button mBtnCheckDetail;

    private OrderDetailModel model;
    @Override
    public void initDatas() {
        model = (OrderDetailModel) getIntent().getSerializableExtra("OrderDetailModel");

    }

    @Override
    public void configViews() {
        mTvTitle.setText("验证成功");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_success;
    }

    @OnClick(R.id.btn_return_main)
    public void returnMain(){
        finish();
    }

    @OnClick(R.id.btn_check_detail)
    public void checkDetail(){

    }

}
