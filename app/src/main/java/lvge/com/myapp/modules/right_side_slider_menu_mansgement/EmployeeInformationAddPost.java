package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
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
import lvge.com.myapp.model.SalesConsutantListViewData;
import lvge.com.myapp.model.SellerImgs;
import lvge.com.myapp.modules.my_4s_management.SalesConsultant;
import okhttp3.Call;
import okhttp3.Response;

public class EmployeeInformationAddPost extends AppCompatActivity {

    private TextView employee_information_post_add;
    private SwipeMenuListView employee_information_post_listview;
    private List<PostListMode> contentList = new ArrayList<PostListMode>();

    private String clientString = "";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_add_post);

        context = this;

        employee_information_post_listview = (SwipeMenuListView)findViewById(R.id.employee_information_post_listview);
        employee_information_post_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {

                SwipeMenuItem openItem = new SwipeMenuItem(context);
                openItem.setBackground(new ColorDrawable(Color.RED));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("删除");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                swipeMenu.addMenuItem(openItem);
            }
        };
        employee_information_post_listview.setMenuCreator(creator);
        employee_information_post_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*view.findViewById(R.id.post_list_item_imagevie)*/
                clientString = contentList.get(position).getJOB_NAME();

                CheckedTextView ct;
                CheckedTextView multipe;
                int count = employee_information_post_listview.getChildCount();
                for(int i=0; i< count;i++){
                    ct = (CheckedTextView)employee_information_post_listview.getChildAt(i).findViewById(R.id.post_list_item_check);
                    ct.setChecked(false);
                }
                multipe = (CheckedTextView)view.findViewById(R.id.post_list_item_check);
                multipe.toggle();
                //employee_information_post_listview.setAdapter(new EmployeeInformationAddPostAdapter(EmployeeInformationAddPost.this, contentList));
            }
        });

        employee_information_post_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                PostListMode item = contentList.get(i);
                switch (i1) {
                    case 0:
                        //removeConsultant(item.getJOB_ID());
                        String sdr = String.valueOf(item.getJOB_ID());
                        // getListItem();
                        try{
                            OkHttpUtils.post()//get 方法
                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/job/delete.do") //地址
                                    .addParams("JOB_ID", sdr)
                                    .build()
                                    .execute(new Callback() {//通用的callBack

                                        //从后台获取成功后，对相应进行类型转化
                                        @Override
                                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                                            String string = response.body().string();//获取相应中的内容Json格式
                                            //把json转化成对应对象
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
                                                //当返回值为2时不可登录
                                                if (result.getOperationResult().getResultCode() == 0) {
                                                    loadAddpost();
                                                } else {
                                                    Toast.makeText(EmployeeInformationAddPost.this, "删除失败！", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {//当没有返回对象时，表示网络没有联通
                                                Toast.makeText(EmployeeInformationAddPost.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } catch (Exception e) {
                            Toast.makeText(EmployeeInformationAddPost.this, "网络异常！", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        employee_information_post_add = (TextView)findViewById(R.id.employee_information_post_add);


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
                if(clientString.equals("")){
                    Toast.makeText(EmployeeInformationAddPost.this,"未选择岗位，请重新选择",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("inputpost",clientString);
                setResult(11,intent);
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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
