package lvge.com.myapp.mainFragement;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;

import lvge.com.myapp.modules.customer_management.ClientsAdapter;
import lvge.com.myapp.ui.ListviewItem;
import lvge.com.myapp.ui.LoadListView;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private Context mcontext;
    private LoadListView listview;

    private int count = 0;

    public ClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_client, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.title);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            ClientsAdapter adapter = new ClientsAdapter(getActivity());
            String Url = "";

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    switch (tab.getPosition()) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }

                    OkHttpUtils.get()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
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
                                    ClientResultModel result = new Gson().fromJson(string, ClientResultModel.class);
                                    return result;
                                }

                                @Override
                                public void onError(okhttp3.Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(Object object, int i) {
                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        ClientResultModel result = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                        if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                            adapter.setClients(result);
                                            LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                                            client_lst.setInterface(new LoadListView.IloadListener() {
                                                @Override
                                                public void onLoad() {
                                                    // 刷新太快 所以新启一个线程延迟两秒
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {

                                                        @Override
                                                        public void run() {
                                                            // TODO Auto-generated method stub
                                                            for (int i = 0; i < 3; i++) {
                                                                ListviewItem listviewitem1 = new ListviewItem("加载的" + count, R.drawable.ic_client);

                                                                count++;
                                                            } // 通知listview加载完毕
                                                            listview.loadComplete();
                                                        }
                                                    }, 2000);
                                                }
                                            });
                                            client_lst.setAdapter(adapter);

                                        } else {
                                            Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
