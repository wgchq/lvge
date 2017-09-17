package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Call;
import okhttp3.Response;

public class EmployeeInformationPostAdd extends AppCompatActivity {

    private TextView employee_information_post_compltant;
    private EditText employee_information_add_post_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_post_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_information_add_post);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        employee_information_post_compltant = (TextView)findViewById(R.id.employee_information_post_compltant);
        employee_information_add_post_input = (EditText) findViewById(R.id.employee_information_add_post_input);

        employee_information_post_compltant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{
                   OkHttpUtils.get()
                           .url("http://www.lvgew.com/obdcarmarket/sellerapp/job/save.do")
                           .addParams("JOB_NAME",employee_information_add_post_input.getText().toString())
                           .build()
                           .execute(new Callback() {
                               @Override
                               public Object parseNetworkResponse(Response response, int i) throws Exception {
                                   String string = response.body().string();//获取相应中的内容Json格式
                                   //把json转化成对应对象
                                   //LoginResultModel是和后台返回值类型结构一样的对象
                                   LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                   return result;
                               }

                               @Override
                               public void onError(Call call, Exception e, int i) {

                               }

                               @Override
                               public void onResponse(Object o, int i) {
                                   LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                   if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                       Toast.makeText(EmployeeInformationPostAdd.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(EmployeeInformationPostAdd.this,EmployeeInformationAddPost.class);
                                       startActivity(intent);
                                       finish();
                                   }else {
                                       Toast.makeText(EmployeeInformationPostAdd.this, "添加失败！", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }catch (Exception e){
                   Toast.makeText(EmployeeInformationPostAdd.this, "网络异常！", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}
