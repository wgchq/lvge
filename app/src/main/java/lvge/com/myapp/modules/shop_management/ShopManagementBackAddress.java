package lvge.com.myapp.modules.shop_management;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.model.CustomerDetail;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.util.L;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by cnhao on 2017/9/7.
 */

public class ShopManagementBackAddress extends AppCompatActivity {
    private TextView shop_finish_back_address;
    private ShopManagementBackAddressGson result;
    private CustomerDetail result1;


    private String receiver;
    private String detailAddress;
    private String postCode;
    private String mobile;

    private TextView shop_back_address_contact_name;
    private TextView shop_back_address_contact_address;
    private TextView shop_back_address_postcode;
    private TextView shop_back_address_phonenumber;

    private CustomProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management_back_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_back_address);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shop_finish_back_address = (TextView)findViewById(R.id.shop_finish_back_address);
        shop_finish_back_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShopManagementBackAddress.this,"ok",Toast.LENGTH_SHORT).show();
                try {
                    startProgerssDialog();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
//                             */
//                            shop_back_address_contact_name = (TextView)findViewById(R.id.shop_back_address_contact_name);
//                            shop_back_address_contact_address = (TextView)findViewById(R.id.shop_back_address_contact_address);
//                            shop_back_address_postcode = (TextView)findViewById(R.id.shop_back_address_postcode);
//                            shop_back_address_phonenumber = (TextView)findViewById(R.id.shop_back_address_phonenumber);
                            OkHttpUtils.post()//get 方法
                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/returnGoodsSetting/saveOrUpdate") //地址
                                    .addParams("receiver", shop_back_address_contact_name.getText().toString())
                                    .addParams("address",shop_back_address_contact_address.getText().toString())
                                    .addParams("postCode", shop_back_address_postcode.getText().toString())
                                    .addParams("mobile", shop_back_address_phonenumber.getText().toString())
                                    //.addParams("type",type)
                                    .build()
                                    .execute(new Callback() {
                                        @Override
                                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                                            String string = response.body().string();//获取相应中的内容Json格式
                                            //把json转化成对应对象
                                            //LoginResultModel是和后台返回值类型结构一样的对象
                                            result1 = new Gson().fromJson(string, CustomerDetail.class);
                                            return result1;
                                        }

                                        @Override
                                        public void onError(Call call, Exception e, int i) {
                                            stopProgressDialog();
                                        }

                                        @Override
                                        public void onResponse(Object o, int i) {
                                            if (null != o) {
                                                result1 = (CustomerDetail) o;//把通用的Object转化成指定的对象
                                                if (result1.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                                    Toast.makeText(ShopManagementBackAddress.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(ShopManagementBackAddress.this, result1.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {//当没有返回对象时，表示网络没有联通
                                                Toast.makeText(ShopManagementBackAddress.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                            }
                                            stopProgressDialog();
                                        }
                                    });
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule(task, 2000);//3秒后执行



                } catch (Exception e) {
                    stopProgressDialog();
                    Toast.makeText(ShopManagementBackAddress.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resource_init();
        network_init();
    }

    public void resource_init(){
        shop_back_address_contact_name = (TextView)findViewById(R.id.shop_back_address_contact_name);
        shop_back_address_contact_address = (TextView)findViewById(R.id.shop_back_address_contact_address);
        shop_back_address_postcode = (TextView)findViewById(R.id.shop_back_address_postcode);
        shop_back_address_phonenumber = (TextView)findViewById(R.id.shop_back_address_phonenumber);


    }

    public void network_init(){
        try {
            OkHttpUtils.get()
                    //get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/returnGoodsSetting/detail") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            L.d(string);
                            result = new Gson().fromJson(string, ShopManagementBackAddressGson.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                result = (ShopManagementBackAddressGson) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    if(result.getMarketEntity() == null)
                                        return;

                                    receiver = String.valueOf(result.getMarketEntity().getReceiver());
                                    detailAddress = String.valueOf(result.getMarketEntity().getDetailAddress());
                                    postCode = String.valueOf(result.getMarketEntity().getPostCode());
                                    mobile = String.valueOf(result.getMarketEntity().getMobile());

//        private TextView shop_back_address_contact_name;
//        private TextView shop_back_address_contact_address;
//        private TextView shop_back_address_postcode;
//        private TextView shop_back_address_phonenumber;
                                    shop_back_address_contact_name.setText(receiver);
                                    shop_back_address_contact_address.setText(detailAddress);
                                    shop_back_address_postcode.setText(postCode);
                                    shop_back_address_phonenumber.setText(mobile);

                                } else {
                                        // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                        // startActivity(intent);
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                    // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // currentIndex = 0;
        } catch (Exception e) {
            Toast.makeText(ShopManagementBackAddress.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    private void startProgerssDialog(){
        if(progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            // progressDialog.setMessage("正在加载中。。");
        }
        progressDialog.show();
    }

    private void stopProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
