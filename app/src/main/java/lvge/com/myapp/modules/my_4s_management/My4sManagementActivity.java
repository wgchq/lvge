package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;

import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.shop_management.ShopManageShopImgActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;
import okhttp3.Response;

public class My4sManagementActivity extends AppCompatActivity {

    private File cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_management);

        ImageView my4s_manage_back = (ImageView)findViewById(R.id.my4s_management_back);   //返回图片
        TextView my4s_manage_finish = (TextView)findViewById(R.id.my4s_finish);

        try{

            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4s/detail") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                            return null;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 2) {//当返回值为2时不可登录
                                    //Toast.makeText(MainActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    // startActivity(intent);
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
             } catch (Exception e) {
            Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }

        my4s_manage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        my4s_manage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
