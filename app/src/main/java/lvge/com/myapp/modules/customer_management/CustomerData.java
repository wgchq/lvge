package lvge.com.myapp.modules.customer_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.CustomerDetail;
import okhttp3.Response;

public class CustomerData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data);

        Intent intent = new Intent() ;
        intent = getIntent();
        String id = intent.getStringExtra("custumerId");


        OkHttpUtils.get()//get 方法
                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/detail") //地址
                .addParams("id", intent.getStringExtra("custumerId")) //需要传递的参数
                .build()
                .execute(new Callback() {//通用的callBack

                    //从后台获取成功后，对相应进行类型转化
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {

                        String string = response.body().string();//获取相应中的内容Json格式
                        //把json转化成对应对象
                        //LoginResultModel是和后台返回值类型结构一样的对象
                        CustomerDetail result = new Gson().fromJson(string, CustomerDetail.class);
                        return result;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(Object object, int i) {

                        //object 是 parseNetworkResponse的返回值
                        if (null != object) {
                            CustomerDetail result = (CustomerDetail) object;//把通用的Object转化成指定的对象

                        }
                    }

                });
    }
}
