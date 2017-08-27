package lvge.com.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Timer;
import java.util.TimerTask;

import lvge.com.myapp.model.CarImagViewLoadMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.util.NetworkUtil;
import okhttp3.Call;
import okhttp3.Response;

public class WelcomePageActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String username = "";
    private String password = "";
    BroadcastReceiver receiver;
    private final static String FILE_NAME = "login_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
         receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int status = NetworkUtil.getConnectivityStatusString(context);

                TextView txt_network_check_message = (TextView) findViewById(R.id.txt_network_check_message);
                if (status == 3) {
                    Toast.makeText(WelcomePageActivity.this,"您处于离线状态，请检查网络！",Toast.LENGTH_SHORT).show();

                } else {
                    Login();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=receiver)
        {
            unregisterReceiver(receiver);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null!=receiver)
        {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    void Login() {
        try {
            preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

            username = preferences.getString("username", "");
            password = preferences.getString("password", "");
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    OkHttpUtils.get()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/login.do") //地址
                            .addParams("userName", username) //需要传递的参数
                            .addParams("pwd", password)
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
                                    Toast.makeText(WelcomePageActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(Object object, int i) {

                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
                                        if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                            Intent intent = new Intent(WelcomePageActivity.this, MainPageActivity.class);
                                            Bundle bundle = new Bundle();

                                            bundle.putString("name", username);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        } else {
                                            Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }

                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(WelcomePageActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 2000);//3秒后执行

        } catch (Exception e) {
            Toast.makeText(WelcomePageActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    /***
    protected void onRestart(){
        super.onRestart();
        Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
        startActivity(intent);
    }
     ***/
}

