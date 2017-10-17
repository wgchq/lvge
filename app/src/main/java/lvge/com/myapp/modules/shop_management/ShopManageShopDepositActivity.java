package lvge.com.myapp.modules.shop_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;

import lvge.com.myapp.model.WinXinModel;

import okhttp3.Response;

public class ShopManageShopDepositActivity extends AppCompatActivity {

    private TextView shop_deposit_argue;
    private Button btn_shop_deposit_get;

    private String tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage_shop_deposit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_deposit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shop_deposit_argue = (TextView) findViewById(R.id.shop_deposit_argue);
        shop_deposit_argue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManageShopDepositActivity.this, ShopManagementDepositArgueActivity.class);
                startActivity(intent);
            }
        });

        btn_shop_deposit_get = (Button) findViewById(R.id.btn_shop_deposit_get);
        //暂时屏蔽支付功能
        btn_shop_deposit_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getWeiXinToken();

           /*     Intent intent = new Intent(ShopManageShopDepositActivity.this, PayMainActivity.class);
                startActivity(intent);*/
            }
        });

    }


    public void getWeiXinToken() {
        try {

            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/sellerPay/getTokenId") //地址
                    .addParams("out_trade_no", "1231231") //需要传递的参数
                    .addParams("body", "1231231") //需要传递的参数
                    .addParams("total_fee", "1") //需要传递的参数
                    .build()
                    .execute(new Callback() {//通用的callBack

                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            WinXinModel result = new Gson().fromJson(string, WinXinModel.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                WinXinModel result = (WinXinModel) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录

                                    tokenId = result.getMarketEntity().getToken_id();
                                    gotoWinxin();


                                } else {//当没有返回对象时，表示网络没有联通
                                    Toast.makeText(ShopManageShopDepositActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(ShopManageShopDepositActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }

    }

    private void gotoWinxin()
    {
        RequestMsg msg = new RequestMsg();
        msg.setTokenId(tokenId);//token_id为服务端预下单返回
        msg.setTradeType(MainApplication. WX_APP_TYPE); //app支付类型
        msg.setAppId("wx101b93bcd0c34a79");//appid为商户自己在微信开放平台的应用appid
        PayPlugin.unifiedAppPay (ShopManageShopDepositActivity.this, msg);

    }

}
