package lvge.com.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.PostFileRequest;

import org.json.JSONObject;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;

import static android.R.attr.id;


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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("userName","admin");
                            params.put("pwd","1");
                           // JSONObject jsonObject = new JSONObject(params);

                            OkHttpUtils.get()
                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/login.do?userName=admin &pwd=1")
                                    //.mediaType(MediaType.parse("application/json;charset=utf-8"))
                                   // .content(new Gson().toJson(params))
                                    .build()
                                    .execute(new MyStringCallback());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
    }
     public  class MyStringCallback extends StringCallback{

            public void onError(okhttp3.Call call, Exception e, int id) {
             e.printStackTrace();
             //mTv.setText("onError:" + e.getMessage());
             }


             public void onResponse(String response,int id){
                 //mTv.setText("onResponse:" + decodeUnicodeToString(response));
                 }
     }

}
