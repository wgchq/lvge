package lvge.com.myapp.modules.customer_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Response;

public class HolderKilometerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder_kilometer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_holder_kilometer);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("edit", "f");
                setResult(5, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        String kilometer = intent.getStringExtra("kilometer");
        final String customerID = intent.getStringExtra("customerID");

        EditText et_hoder_kilometer = (EditText) findViewById(R.id.holder_kilometer);
        et_hoder_kilometer.setText(kilometer);

        TextView complete = (TextView) findViewById(R.id.kilometer_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText holder_kilometer = (EditText) findViewById(R.id.holder_kilometer);
                String str_holder_kilometer = holder_kilometer.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("kilometer", str_holder_kilometer);
                intent.putExtra("edit", "t");

                OkHttpUtils.get()//get 方法
                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/updateMileage") //地址
                        .addParams("mileage", str_holder_kilometer) //需要传递的参数
                        .addParams("customerID", customerID)
                        .build()
                        .execute(new Callback() {//通用的callBack

                            //从后台获取成功后，对相应进行类型转化
                            @Override
                            public Object parseNetworkResponse(Response response, int i) throws Exception {

                                String string = response.body().string();//获取相应中的内容Json格式
                                //把json转化成对应对象
                                //LoginResultModel是和后台返回值类型结构一样的对象
                                LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                return result;
                            }

                            @Override
                            public void onError(okhttp3.Call call, Exception e, int i) {

                            }

                            @Override
                            public void onResponse(Object object, int i) {

                                //object 是 parseNetworkResponse的返回值
                                if (null != object) {
                                    LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
                                    if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                        Toast.makeText(HolderKilometerActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(HolderKilometerActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {//当没有返回对象时，表示网络没有联通
                                    Toast.makeText(HolderKilometerActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                setResult(5, intent);
                finish();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("edit", "f");
            setResult(5, intent);
            finish();
        }

        return false;
    }
}
