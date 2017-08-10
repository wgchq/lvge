package lvge.com.myapp.mainFragement;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;

import lvge.com.myapp.modules.customer_management.ClientsAdapter;

import lvge.com.myapp.modules.customer_management.CustomerData;
import lvge.com.myapp.modules.customer_management.CustomerSearch;
import lvge.com.myapp.ui.LoadListView;
import okhttp3.Call;
import okhttp3.Response;

import static lvge.com.myapp.R.id.appBarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private ClientResultModel clientResultModel;
    private Context mcontext;
    private LoadListView listview;
    ClientsAdapter adapter;
    private int count = 0;
     String status = "0";
    private boolean search_show = false;

    private String query;

    private boolean isSearch_show = false;

    public ClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_client, container, false);
        adapter = new ClientsAdapter(getActivity());
        final  TabLayout tabLayout = (TabLayout) view.findViewById(R.id.title);


        final AppBarLayout appBarLayout = (AppBarLayout)view.findViewById(R.id.search_edit_frame);
        final SearchView search_view = (SearchView)view.findViewById(R.id.search_view);

        search_view.setIconifiedByDefault(false);
        search_view.setSubmitButtonEnabled(true);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                if(tab != null){
                    tab.select();
                }
                search(query,view,"1");
                query = query;
                appBarLayout.setVisibility(view.GONE);
                search_show = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        iniPage(view);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                isSearch_show = false;
                try {
                    switch (tab.getPosition()) {
                        case 0:
                            status = "0";//所有
                            break;
                        case 1:
                             status = "1";//有硬件
                            break;
                        case 2:
                            status = "2";//无硬件
                            break;
                        case 3:
                            status = "3";//离线
                            break;
                    }

                    OkHttpUtils.get()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                            .addParams("pageIndex", "1") //需要传递的参数
                            .addParams("pageSize", "10")
                            .addParams("status",status)
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
                                public void onResponse(Object object, final int i) {
                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        clientResultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                        if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                            adapter.setClients(clientResultModel);

                                            LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                                            client_lst.setInterface(new LoadListView.IloadListener() {
                                                @Override
                                                public void onLoad() {

                                                    // 刷新太快 所以新启一个线程延迟两秒
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {

                                                        @Override
                                                        public void run() {

                                                            try {
                                                                if(isSearch_show){
                                                                    search(query,view,Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1));
                                                                    return;
                                                                }

                                                                OkHttpUtils.get()//get 方法
                                                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                        .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
                                                                        .addParams("pageSize", "10")
                                                                        .addParams("status",status)
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

                                                                                int a = 0;
                                                                                //object 是 parseNetworkResponse的返回值
                                                                                if (null != object) {
                                                                                    ClientResultModel resultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                                                                    if (resultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                                                                        clientResultModel.getPageResult().getEntityList().addAll(resultModel.getPageResult().getEntityList());
                                                                                        clientResultModel.getPageResult().setPageIndex(resultModel.getPageResult().getPageIndex());

                                                                                        adapter.setClients(clientResultModel);
                                                                                        LoadListView client = (LoadListView) view.findViewById(R.id.clients_list);

                                                                                        client.setAdapter(adapter);
                                                                                        client.setSelection(client.getBottom());
                                                                                        client.loadComplete();

                                                                                    } else {
                                                                                        Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {//当没有返回对象时，表示网络没有联通
                                                                                    Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
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

        LoadListView client_lsts = (LoadListView) view.findViewById(R.id.clients_list);
        client_lsts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), CustomerData.class);
                String clientId = Integer.toString(clientResultModel.getPageResult().getEntityList().get(position).getId());
                intent.putExtra("id", clientId);
                startActivity(intent);
            }
        });

        final ImageView search_client = (ImageView) view.findViewById(R.id.search_client);
        search_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getActivity(), CustomerSearch.class);
               // startActivity(intent);
                isSearch_show = true;
                if(search_show){
                   appBarLayout.setVisibility(v.GONE);
                    search_show = false;
                }else {
                    appBarLayout.setVisibility(v.VISIBLE);
                    search_view.setFocusable(true);
                    search_view.requestFocusFromTouch();
                    search_show = true;
                }
            }
        });


        return view;
    }

    void iniPage(final View view) {
        try {
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", "10")
                    .addParams("status","0")
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
                        public void onResponse(Object object, final int i) {
                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                clientResultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    adapter.setClients(clientResultModel);
                                    LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                                    client_lst.setInterface(new LoadListView.IloadListener() {
                                        @Override
                                        public void onLoad() {

                                            // 刷新太快 所以新启一个线程延迟两秒
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {
                                                        OkHttpUtils.get()//get 方法
                                                                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
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

                                                                        int a = 0;

                                                                    }

                                                                    @Override
                                                                    public void onResponse(Object object, int i) {

                                                                        int a = 0;
                                                                        //object 是 parseNetworkResponse的返回值
                                                                        if (null != object) {
                                                                            ClientResultModel resultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                                                            if (resultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                                                                clientResultModel.getPageResult().getEntityList().addAll(resultModel.getPageResult().getEntityList());
                                                                                clientResultModel.getPageResult().setPageIndex(resultModel.getPageResult().getPageIndex());

                                                                                adapter.setClients(clientResultModel);
                                                                                LoadListView client = (LoadListView) view.findViewById(R.id.clients_list);

                                                                                client.setAdapter(adapter);
                                                                                client.setSelection(client.getBottom());
                                                                                client.loadComplete();

                                                                            } else {
                                                                                Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {//当没有返回对象时，表示网络没有联通
                                                                            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

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

    private void search(String str, final View view , String index){
        try {
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                    .addParams("pageIndex", index) //需要传递的参数
                    .addParams("pageSize", "10")
                    .addParams("status", "0")
                    .addParams("keyword",str)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            ClientResultModel result = new Gson().fromJson(string, ClientResultModel.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                                //object 是 parseNetworkResponse的返回值
                                if (null != o) {
                                    clientResultModel = (ClientResultModel) o;//把通用的Object转化成指定的对象
                                    if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                        adapter.setClients(clientResultModel);
                                        LoadListView client_lst = (LoadListView)view.findViewById(R.id.clients_list);
                                        client_lst.setInterface(new LoadListView.IloadListener() {
                                            @Override
                                            public void onLoad() {

                                                // 刷新太快 所以新启一个线程延迟两秒
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {

                                                    @Override
                                                    public void run() {

                                                        try {
                                                            OkHttpUtils.get()//get 方法
                                                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                    .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
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

                                                                            int a = 0;

                                                                        }

                                                                        @Override
                                                                        public void onResponse(Object object, int i) {

                                                                            int a = 0;
                                                                            //object 是 parseNetworkResponse的返回值
                                                                            if (null != object) {
                                                                                ClientResultModel resultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                                                                if (resultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                                                                    clientResultModel.getPageResult().getEntityList().addAll(resultModel.getPageResult().getEntityList());
                                                                                    clientResultModel.getPageResult().setPageIndex(resultModel.getPageResult().getPageIndex());

                                                                                    adapter.setClients(clientResultModel);
                                                                                    LoadListView client = (LoadListView) view.findViewById(R.id.clients_list);

                                                                                    client.setAdapter(adapter);
                                                                                    client.setSelection(client.getBottom());
                                                                                    client.loadComplete();

                                                                                } else {
                                                                                    Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {//当没有返回对象时，表示网络没有联通
                                                                                Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }, 2000);
                                            }
                                        });
                                        client_lst.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }


}
