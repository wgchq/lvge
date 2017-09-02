package lvge.com.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.model.LoginResultModel;
import okhttp3.CookieJar;
import okhttp3.Response;

public class MainActivity extends android.support.v7.app.AppCompatActivity {

    private SharedPreferences preferences;
    private String action;
    private final static String FILE_NAME = "login_file";
    private Button login_submit;
    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        login_submit = (Button) findViewById(R.id.login_submit);
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        TextView lg_forget_password = (TextView) findViewById(R.id.lg_forget_password);



     /*   action = "auto_submit";
        Login(action);*/
        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "submit";
                Login(action);
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_submit.setBackgroundResource(R.drawable.lg_main_button_background_input);
                login_submit.setTextColor(getResources().getColor(R.color.buttonTextColor));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_username.getText().toString().equals("")) {
                    login_submit.setBackgroundResource(R.drawable.lg_main_button_background);
                    login_submit.setTextColor(getResources().getColor(R.color.lg_button_text_color));
                }

            }
        });

        lg_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    void Login(String action) {

        final String submit_action = action;
        try {
            preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

            String username = preferences.getString("username", "");
            String password = preferences.getString("password", "");

            if (username.equals("") && password.equals("")) {
                return;
            }
            et_username.setText(username);
            et_password.setText(password);

            login_submit.setBackgroundResource(R.drawable.lg_main_button_background_input);
            login_submit.setTextColor(getResources().getColor(R.color.buttonTextColor));

            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/login.do") //地址
                    .addParams("userName", et_username.getText().toString()) //需要传递的参数
                    .addParams("pwd", et_password.getText().toString())
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
                                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    Bundle bundle = new Bundle();

                                    if (submit_action.equals("submit")) {
                                        SharedPreferences.Editor editor = preferences.edit();
                                        // 设置数据 第一 个参数是key 第二个是相应数据
                                        editor.putString("username", et_username.getText().toString());
                                        editor.putString("password", et_password.getText().toString());
                                        // 提交 不提交设置的数据将无效
                                        editor.apply();
                                    }

                                    bundle.putString("name", et_username.getText().toString());
                                    intent.putExtras(bundle);
                                    TextView clear_lg_return_error = (TextView) findViewById(R.id.lg_return_error);
                                    clear_lg_return_error.setText("");
                                    startActivity(intent);

                                } else {
                                    //Toast.makeText(MainActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                    TextView lg_return_error = (TextView) findViewById(R.id.lg_return_error);
                                    lg_return_error.setText(result.getOperationResult().getResultMsg());
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;

    }

}
