package lvge.com.myapp.modules.ValidationHistory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.WelcomePageActivity;
import lvge.com.myapp.model.HistoryValidationListEntity;
import lvge.com.myapp.view.LoadListView;
import okhttp3.Response;

public class ValidationHistoryActivity extends AppCompatActivity {

    private HistoryValidationListAdapter adapter = null;
    private TabLayout tabLayout = null;
    private CustomProgressDialog progressDialog = null;
    private String PageSize = "1000";
    HistoryValidationListEntity historyValidationListEntity = null;
    LoadListView history_validation_list = null;
    private String type = "0";

    ImageView history_validation_calendar;
    ImageView history_validation_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_validation_history);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new HistoryValidationListAdapter(ValidationHistoryActivity.this);

        tabLayout = (TabLayout) findViewById(R.id.title);
        init();
        initView();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        type = "0";//今日
                        break;
                    case 1:
                        type = "1";//本周
                        break;
                    case 2:
                        type = "2";//本月
                        break;
                    case 3:
                        type = "3";//全部
                        break;
                }
                try {
                    getData(type);
                } catch (Exception e) {
                    stopProgressDialog();
                    Toast.makeText(ValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initView()
    {
        history_validation_calendar =(ImageView)findViewById(R.id.history_validation_calendar);
        history_validation_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidationHistoryActivity.this,HistoryValidationSearchActivity.class);
                intent.putExtra("calendar","true");
                startActivity(intent);
            }
        });

        history_validation_search = (ImageView)findViewById(R.id.history_validation_search);
        history_validation_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidationHistoryActivity.this,SearchValidationHistoryActivity.class);

                startActivity(intent);
            }
        });
    }

    private void getData(String stype) {
        try {
            startProgerssDialog();
            final String type = stype;
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/code/useList") //地址
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", PageSize)
                    .addParams("type", type)
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            HistoryValidationListEntity result = new Gson().fromJson(string, HistoryValidationListEntity.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object object, final int i) {
                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                historyValidationListEntity = (HistoryValidationListEntity) object;//把通用的Object转化成指定的对象
                                if (historyValidationListEntity.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    adapter.setHistoryValidationModels(historyValidationListEntity.getPageResult().getEntityList());
                                    TextView record_total_count = (TextView) findViewById(R.id.record_total_count);
                                    record_total_count.setText(Integer.toString(historyValidationListEntity.getPageResult().getTotalCount()));
                                    history_validation_list = (LoadListView) findViewById(R.id.history_validation_list);

                                    stopProgressDialog();
                                    history_validation_list.setInterface(new LoadListView.IloadListener() {
                                        @Override
                                        public void onLoad() {

                                            // 刷新太快 所以新启一个线程延迟两秒
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {

                                                        OkHttpUtils.get()//get 方法
                                                                .url("http://www.lvgew.com/obdcarmarket/sellerapp/code/useList") //地址
                                                                .addParams("pageIndex", Integer.toString(historyValidationListEntity.getPageResult().getPageIndex() + 1)) //需要传递的参数
                                                                .addParams("pageSize", PageSize)
                                                                .addParams("type", type)
                                                                .build()
                                                                .execute(new Callback() {//通用的callBack

                                                                    //从后台获取成功后，对相应进行类型转化
                                                                    @Override
                                                                    public Object parseNetworkResponse(Response response, int i) throws Exception {

                                                                        String string = response.body().string();//获取相应中的内容Json格式
                                                                        //把json转化成对应对象
                                                                        //LoginResultModel是和后台返回值类型结构一样的对象
                                                                        HistoryValidationListEntity result = new Gson().fromJson(string, HistoryValidationListEntity.class);
                                                                        return result;
                                                                    }

                                                                    @Override
                                                                    public void onError(okhttp3.Call call, Exception e, int i) {
                                                                        stopProgressDialog();

                                                                    }

                                                                    @Override
                                                                    public void onResponse(Object object, int i) {

                                                                        int a = 0;
                                                                        //object 是 parseNetworkResponse的返回值
                                                                        if (null != object) {
                                                                            HistoryValidationListEntity resultModel = (HistoryValidationListEntity) object;//把通用的Object转化成指定的对象
                                                                            if (resultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录

                                                                                if (resultModel.getPageResult().getEntityList().size() == 0) {
                                                                                    history_validation_list.loadComplete();
                                                                                }

                                                                                historyValidationListEntity.getPageResult().getEntityList().addAll(resultModel.getPageResult().getEntityList());
                                                                                historyValidationListEntity.getPageResult().setPageIndex(resultModel.getPageResult().getPageIndex());

                                                                                adapter.setHistoryValidationModels(historyValidationListEntity.getPageResult().getEntityList());
                                                                                LoadListView history_validation_list = (LoadListView) findViewById(R.id.history_validation_list);

                                                                                history_validation_list.setAdapter(adapter);
                                                                                history_validation_list.setSelection(history_validation_list.getBottom());
                                                                                history_validation_list.loadComplete();
                                                                                stopProgressDialog();

                                                                            } else {
                                                                                stopProgressDialog();

                                                                                Toast.makeText(ValidationHistoryActivity.this, "登录状态监测到有异常，请重新登录", Toast.LENGTH_SHORT).show();

                                                                                Intent intent = new Intent(ValidationHistoryActivity.this, WelcomePageActivity.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        } else {//当没有返回对象时，表示网络没有联通
                                                                            stopProgressDialog();
                                                                            Toast.makeText(ValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } catch (Exception e) {
                                                        stopProgressDialog();
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, 2000);
                                        }
                                    });
                                    history_validation_list.setAdapter(adapter);

                                } else {
                                    // Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ValidationHistoryActivity.this);
                                    builder.setMessage("网络异常，请重新登陆");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ValidationHistoryActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                stopProgressDialog();
                                Toast.makeText(ValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            stopProgressDialog();
            Toast.makeText(ValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
/*
        tabLayout.getChildAt(0).setSelected(true);
*/
        getData("0");
    }

    private void startProgerssDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            // progressDialog.setMessage("正在加载中。。");
        }
        progressDialog.show();
    }

    private void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
