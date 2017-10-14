package lvge.com.myapp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import lvge.com.myapp.R;
import lvge.com.myapp.base.BaseActivity;
import lvge.com.myapp.util.ToastUtil;


public class KeyBoardActivity extends BaseActivity {

    @BindView(R.id.key_board_down)
    ImageView mKeyBoardDown;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.key_1)
    LinearLayout mKey1;
    @BindView(R.id.key_2)
    LinearLayout mKey2;
    @BindView(R.id.key_3)
    LinearLayout mKey3;
    @BindView(R.id.key_4)
    LinearLayout mKey4;
    @BindView(R.id.key_5)
    LinearLayout mKey5;
    @BindView(R.id.key_6)
    LinearLayout mKey6;
    @BindView(R.id.key_7)
    LinearLayout mKey7;
    @BindView(R.id.key_8)
    LinearLayout mKey8;
    @BindView(R.id.key_9)
    LinearLayout mKey9;
    @BindView(R.id.key_0)
    LinearLayout mKey0;
    @BindView(R.id.key_board_validation)
    Button mBtnVerify;
    private String code = "";

    @Override
    public void initDatas() {
         code = getIntent().getStringExtra("code");
    }

    @Override
    public void configViews() {
        mEtNumber.setText(code);
    }

    @Override
    public int getLayoutId() {
        return R.layout.popu_keybord_view;
    }

    @OnClick({R.id.key_0,R.id.key_1,R.id.key_2,R.id.key_3,R.id.key_4,R.id.key_5,R.id.key_6,
            R.id.key_7,R.id.key_8,R.id.key_9})
    public void onClick(View v){
        int key = 0;
        switch (v.getId()){
            case R.id.key_0:
                key = 0;
                break;
            case R.id.key_1:
                key = 1;
                break;
            case R.id.key_2:
                key = 2;
                break;
            case R.id.key_3:
                key = 3;
                break;
            case R.id.key_4:
                key = 4;
                break;
            case R.id.key_5:
                key = 5;
                break;
            case R.id.key_6:
                key = 6;
                break;
            case R.id.key_7:
                key = 7;
                break;
            case R.id.key_8:
                key = 8;
                break;
            case R.id.key_9:
                key = 9;
                break;

        }
        String s = mEtNumber.getText().toString();
        mEtNumber.setText(s+key);
    }

    @OnClick(R.id.key_board_validation)
    public void verfiy(){
        if (TextUtils.isEmpty(code)){
            ToastUtil.show(this,"验证码不能为空");
            return;
        }
        // TODO: 2017/10/14 请求验证二维码

    }

    @OnClick(R.id.iv_delete)
    public void delete(){
        String text = mEtNumber.getText().toString();
        if (TextUtils.isEmpty(text)){
            return;
        }
        if (text.length() ==1){
            text = "";
        }else {
            text = text.substring(0,text.length()-1);
        }
        mEtNumber.setText(text);
    }
}
