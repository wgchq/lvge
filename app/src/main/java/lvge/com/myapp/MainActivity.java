package lvge.com.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;
import java.util.HashMap;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_submit =  (Button) findViewById(R.id.login_submit);

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // startActivity(new Intent(MainActivity.this,MainPageActivity.class));

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userName","admin");
                params.put("pwd","1");
                JSONObject jsonObject = new JSONObject(params);

                OkHttpUtils.postString()
                           .url("http://www.lvgew.com/obdcarmarket/sellerapp/login.do?userName=1&pwd=2")
                           .content(new Gson().toJson(jsonObject)).build()
                           .execute(new MyStringCallback());
            }
        });
    }

    public class MyStringCallback extends StringCallback{

        @Override
        public void onError(okhttp3.Call call, Exception e, int i) {

        }

        public void onResponse(String response, int id){


        }
    }

}
