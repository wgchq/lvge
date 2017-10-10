package lvge.com.myapp.modules.ValidationHistory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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

public class SearchValidationHistoryActivity extends AppCompatActivity {

    private HistoryValidationListAdapter adapter = null;
    private TabLayout tabLayout = null;
    private CustomProgressDialog progressDialog = null;
    private String PageSize = "1000";
    HistoryValidationListEntity historyValidationListEntity = null;
    LoadListView history_validation_list = null;
    private String type = "0";
    private EditText validation_search_edit;
    private TextView cancel_search;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_validation_history);
        validation_search_edit = (EditText) findViewById(R.id.validation_search_edit);
        validation_search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                  /*  ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchValidationHistoryActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    EditText validation_search_edit = (EditText) findViewById(R.id.validation_search_edit);
                    orderNo = validation_search_edit.getText().toString();
                    getData();
                }
                return false;
            }
        });

        cancel_search = (TextView) findViewById(R.id.cancel_search);

        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void getData() {
        try {
            startProgerssDialog();
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/code/useList") //地址
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", PageSize)
                    .addParams("type", "3")
                    .addParams("orderNo", orderNo)
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
                                adapter = new HistoryValidationListAdapter(SearchValidationHistoryActivity.this);
                                historyValidationListEntity = (HistoryValidationListEntity) object;//把通用的Object转化成指定的对象
                                if (historyValidationListEntity.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    adapter.setHistoryValidationModels(historyValidationListEntity.getPageResult().getEntityList());
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
                                                                .addParams("type", "3")
                                                                .addParams("orderNo", orderNo)
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

                                                                                Toast.makeText(SearchValidationHistoryActivity.this, "登录状态监测到有异常，请重新登录", Toast.LENGTH_SHORT).show();

                                                                                Intent intent = new Intent(SearchValidationHistoryActivity.this, WelcomePageActivity.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        } else {//当没有返回对象时，表示网络没有联通
                                                                            stopProgressDialog();
                                                                            Toast.makeText(SearchValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchValidationHistoryActivity.this);
                                    builder.setMessage("网络异常，请重新登陆");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SearchValidationHistoryActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                stopProgressDialog();
                                Toast.makeText(SearchValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            stopProgressDialog();
            Toast.makeText(SearchValidationHistoryActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
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
