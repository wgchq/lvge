package lvge.com.myapp.modules.commodity_management;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.model.CommodityListEntitylist;
import lvge.com.myapp.model.CommodityListMode;
import lvge.com.myapp.util.NetworkUtil;
import okhttp3.Response;

public class CommodityHomepagemSoldout extends Fragment {
    private View view;
    CommodityHomepagemSoldoutAdapter adapter;
    private BroadcastReceiver receiver;
    private int Netstatus = 0;
    private CustomProgressDialog progressDialog = null;

    private CommodityListMode commodityListMode;

    private SwipeMenuListView system_message_listview;

    private List<CommodityListEntitylist> contentList = new ArrayList<CommodityListEntitylist>();
    private List<CommodityListEntitylist>  onSaleList = new LinkedList<CommodityListEntitylist>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_system_message, container, false);
        adapter = new CommodityHomepagemSoldoutAdapter(getActivity());
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Netstatus = NetworkUtil.getConnectivityStatusString(context);

                if (Netstatus == 3) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(receiver, intentFilter);

        system_message_listview = (SwipeMenuListView) view.findViewById(R.id.system_message_listview);

        initview();

        return  view;
    }

    private void initview() {
        contentList.clear();
        onSaleList.clear();
        startProgerssDialog();
        try {
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/goods/goodsList") //地址
                    .addParams("status", "4")
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", "10")
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            CommodityListMode result = new Gson().fromJson(string, CommodityListMode.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, final int i) {
                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                commodityListMode = (CommodityListMode) object;//把通用的Object转化成指定的对象
                                if (commodityListMode.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    stopProgressDialog();

                                    contentList = commodityListMode.getPageResult().getEntityList();

                                }

                                adapter.setClients(contentList);

                                system_message_listview.setAdapter(adapter);
                                system_message_listview.setSelection(system_message_listview.getBottom());

                            } else {
                                stopProgressDialog();
                                //Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
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
                        }
                    });
        } catch (Exception e) {
            stopProgressDialog();
            Log.i("系统信息加载异常：", e.getMessage());

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
