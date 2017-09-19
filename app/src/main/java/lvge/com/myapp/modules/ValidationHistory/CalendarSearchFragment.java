package lvge.com.myapp.modules.ValidationHistory;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import lvge.com.myapp.ui.LoadListView;
import okhttp3.Response;


public class CalendarSearchFragment extends Fragment {
    private HistoryValidationListAdapter adapter ;
    private TabLayout tabLayout = null;
    private CustomProgressDialog progressDialog = null;
    private String PageSize = "1000";
    HistoryValidationListEntity historyValidationListEntity = null;
    LoadListView history_validation_list = null;
    private String type = "0";

    private View view;

    public CalendarSearchFragment() {
        // Required empty public constructor
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date="";
    private String year="";
    private String month="";
    private String day="";
    private TextView validation_search_year ;
    private TextView validation_search_month ;
    private TextView validation_search_day ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String date = this.getDate();
        view = inflater.inflate(R.layout.fragment_calendar_search, container, false);
        validation_search_year=(TextView) view.findViewById(R.id.validation_search_year);
        validation_search_month=(TextView) view.findViewById(R.id.validation_search_month);
        validation_search_day=(TextView) view.findViewById(R.id.validation_search_day);
        validation_search_year.setText(year);
        validation_search_month.setText(month);
        validation_search_day.setText(day);

        getSearchResult("-1");
        return view;
    }

    private void getSearchResult(String stype) {
        try {
            startProgerssDialog();
            final String type = stype;
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/code/useList") //地址
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", PageSize)
                    .addParams("queryDate", getDate())
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
                                    adapter =   new HistoryValidationListAdapter(getActivity());
                                    adapter.setHistoryValidationModels(historyValidationListEntity.getPageResult().getEntityList());
                                    TextView record_total_count = (TextView) view.findViewById(R.id.record_cal_total_count);
                                    record_total_count.setText(Integer.toString(historyValidationListEntity.getPageResult().getTotalCount()));
                                    history_validation_list = (LoadListView) view.findViewById(R.id.history_validation_calendar_list);
                                    history_validation_list.setAdapter(adapter);
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
                                                                .addParams("queryDate", getDate())
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
                                                                                LoadListView history_validation_list = (LoadListView) view.findViewById(R.id.history_validation_calendar_list);

                                                                                history_validation_list.setAdapter(adapter);
                                                                                history_validation_list.setSelection(history_validation_list.getBottom());
                                                                                history_validation_list.loadComplete();
                                                                                stopProgressDialog();

                                                                            } else {
                                                                                stopProgressDialog();

                                                                                Toast.makeText(getActivity(), "登录状态监测到有异常，请重新登录", Toast.LENGTH_SHORT).show();

                                                                                Intent intent = new Intent(getActivity(), WelcomePageActivity.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        } else {//当没有返回对象时，表示网络没有联通
                                                                            stopProgressDialog();
                                                                            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
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


                                } else {
                                    // Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("网络异常，请重新登陆");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                stopProgressDialog();
                                Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            stopProgressDialog();
            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
        }

    }

    private void startProgerssDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
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
