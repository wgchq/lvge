package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.EmployeeInformationAddPostMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.PostListMode;
import lvge.com.myapp.model.SellerImgs;
import okhttp3.Call;
import okhttp3.Response;

public class EmployeeInformationAddPost extends AppCompatActivity {

    private TextView employee_information_post_add;
    private SwipeMenuListView employee_information_post_listview;
    private List<PostListMode> contentList = new ArrayList<PostListMode>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_add_post);

        employee_information_post_add = (TextView)findViewById(R.id.employee_information_post_add);
        employee_information_post_listview = (SwipeMenuListView)findViewById(R.id.employee_information_post_listview);

        employee_information_post_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeInformationAddPost.this,EmployeeInformationPostAdd.class);
                startActivityForResult(intent,10);
            }
        });

        loadAddpost();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_information_post);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (10 == requestCode && data != null) {
            String inputpost = data.getStringExtra("inputpost");

        }
    }

    private void loadAddpost(){
        try {
            OkHttpUtils.get()
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/job/findAll.do")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            EmployeeInformationAddPostMode result = new Gson().fromJson(string, EmployeeInformationAddPostMode.class);
                            return result;

                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                EmployeeInformationAddPostMode result = (EmployeeInformationAddPostMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                    contentList = result.getMarketEntity();
                                    employee_information_post_listview.setAdapter(new EmployeeInformationAddPostAdapter(EmployeeInformationAddPost.this, contentList));
                                }
                            }
                        }
                    });
        }catch (Exception e){

        }
    }
}
