package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
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
import lvge.com.myapp.model.EmployeeInformationList;
import lvge.com.myapp.model.EmployeeInformationMode;
import lvge.com.myapp.model.LoadRightSideMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.SalesConsutantListViewData;
import lvge.com.myapp.model.SellerImgs;
import lvge.com.myapp.modules.my_4s_management.SalesConsultant;
import lvge.com.myapp.modules.my_4s_management.SalesConsutantListViewAdapter;
import okhttp3.Call;
import okhttp3.Response;

public class EmployeeInformation extends AppCompatActivity {

    private TextView employee_information_toadd;
    private SwipeMenuListView employee_information_listview;

    private SharedPreferences preferences;
    private final static String FILE_NAME = "login_file";

    private EmployeeInformationAdapter adapter;

    private SwipeMenuListView customListView;
    private List<EmployeeInformationList> contentList = new ArrayList<EmployeeInformationList>();

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information);
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_emplyee_information_list);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                // startActivity(intent);
                finish();
            }
        });

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String string = preferences.getString("right_data","");
        LoadRightSideMode result = new Gson().fromJson(string, LoadRightSideMode.class);
      //  contentList = result.getMarketEntity().getSeller().getSellerImgs();

        initEmployeeInformation();

        customListView = (SwipeMenuListView) findViewById(R.id.employee_information_listview);

        /**
        SellerImgs item = null;
        for(int i=0;i<result.getMarketEntity().getSeller().getSellerImgs().size();i++){
            item = new SellerImgs();
            item.setImgPath(result.getMarketEntity().getSeller().getSellerImgs().get(i).getImgPath());
        }
         **/


        employee_information_toadd = (TextView)findViewById(R.id.employee_information_toadd);
        employee_information_listview = (SwipeMenuListView)findViewById(R.id.employee_information_listview);

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
        customListView.setMenuCreator(creator);

        employee_information_toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeInformation.this,EmployeeInformationAdd.class);
                startActivity(intent);
            }
        });

        employee_information_listview.setMenuCreator(creator);

        //添加删除事件
        employee_information_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                EmployeeInformationList item = contentList.get(i);
                switch (i1) {
                    case 0:
                        String adv = item.getUSER_ID();
                        removeEmployeeInformation(adv);
                        // getListItem();
                }
            }
        });
    }

    private void removeEmployeeInformation(String str){
            try {
                OkHttpUtils.get()
                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/deleteStaff.do")
                        .addParams("userId",str)
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
                                if (null != o) {
                                    LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                    if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                        Toast.makeText(EmployeeInformation.this,"删除成功！",Toast.LENGTH_LONG).show();
                                        contentList.remove(result);
                                        new EmployeeInformationAdapter(EmployeeInformation.this, contentList).notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(EmployeeInformation.this,"删除失败！",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

            } catch (Exception e) {
                // stopProgressDialog();
                Toast.makeText(EmployeeInformation.this,"删除失败！",Toast.LENGTH_LONG).show();
            }

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initEmployeeInformation() {
        try {
            OkHttpUtils.get()
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/queryStaff.do")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            //stopProgressDialog();
                            EmployeeInformationMode result = new Gson().fromJson(string, EmployeeInformationMode.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                EmployeeInformationMode result = (EmployeeInformationMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                    contentList = result.getMarketEntity();
                                    customListView.setAdapter(new EmployeeInformationAdapter(EmployeeInformation.this, contentList));
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            // stopProgressDialog();
            // Toast.makeText(EmployeeInformationAdd.this,"上传失败！",Toast.LENGTH_LONG).show();
        }
    }

}
