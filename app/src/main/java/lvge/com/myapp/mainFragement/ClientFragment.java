package lvge.com.myapp.mainFragement;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.WelcomePageActivity;
import lvge.com.myapp.model.ClientResultModel;

import lvge.com.myapp.modules.customer_management.ClientsAdapter;

import lvge.com.myapp.modules.customer_management.CustomerData;
import lvge.com.myapp.modules.customer_management.CustomerSearch;
import lvge.com.myapp.modules.search_tools_page.SearchToolsPage;
import lvge.com.myapp.ui.LoadListView;
import lvge.com.myapp.util.NetworkUtil;
import okhttp3.Call;
import okhttp3.Response;

import static lvge.com.myapp.R.id.appBarLayout;
import static lvge.com.myapp.R.id.default_activity_button;
import static lvge.com.myapp.R.id.financial_management_callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private ClientResultModel clientResultModel;
    private Context mcontext;
    private LoadListView listview;
    ClientsAdapter adapter;
    private int count = 0;
    private String PageSize = "1000";
    String status = "0";
    private boolean search_show = false;
    private int Netstatus = 0;

    private String query;
    private BroadcastReceiver receiver;

    private boolean isSearch_show = false;

    private final static int RESULT_OK = 1001;
    private TabLayout tabLayout;
    private View view;

    private CustomProgressDialog progressDialog = null;

    public ClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_client, container, false);
        adapter = new ClientsAdapter(getActivity());
        tabLayout = (TabLayout) view.findViewById(R.id.title);


        final AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.search_edit_frame);
        // final SearchView search_view = (SearchView)view.findViewById(R.id.search_view);

        /**
         search_view.setIconifiedByDefault(false);
         search_view.setSubmitButtonEnabled(true);
         search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
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

        @Override public boolean onQueryTextChange(String newText) {
        return false;
        }
        });
         **/

        iniPage(view);


        //监测断网progressDialog 停止
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


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(Netstatus == 3){
                    LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                  //  ImageView no_client_img = (ImageView) view.findViewById(R.id.no_client_img);
                    client_lst.setVisibility(View.GONE);
                   // no_client_img.setBackground(null);
                    return;
                }
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
                    startProgerssDialog();
                    OkHttpUtils.get()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                            .addParams("pageIndex", "1") //需要传递的参数
                            .addParams("pageSize", PageSize)
                            .addParams("status", status)
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
                                    stopProgressDialog();
                                }

                                @Override
                                public void onResponse(Object object, final int i) {
                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        clientResultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                        if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                            adapter.setClients(clientResultModel);

                                            LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                                            ImageView no_client_img = (ImageView) view.findViewById(R.id.no_client_img);

                                            if (clientResultModel.getPageResult().getEntityList().size() == 0) {
                                                client_lst.setVisibility(View.GONE);
                                                no_client_img.setBackgroundResource(R.mipmap.client_manage_no_client);
                                            } else {
                                                client_lst.setVisibility(View.VISIBLE);
                                                no_client_img.setBackground(null);
                                            }
                                            stopProgressDialog();
                                            client_lst.setInterface(new LoadListView.IloadListener() {
                                                @Override
                                                public void onLoad() {

                                                    // 刷新太快 所以新启一个线程延迟两秒
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {

                                                        @Override
                                                        public void run() {

                                                            try {
                                                                if (isSearch_show) {
                                                                    search(query, view, Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1));
                                                                    return;
                                                                }
                                                                startProgerssDialog();
                                                                OkHttpUtils.get()//get 方法
                                                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                        .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
                                                                        .addParams("pageSize", PageSize)
                                                                        .addParams("status", status)
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
                                                                                stopProgressDialog();

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
                                            client_lst.setAdapter(adapter);
                                            stopProgressDialog();
                                        } else {
                                            stopProgressDialog();
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
                /**
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
                 **/
                Intent search_tools_page_intent = new Intent();
                search_tools_page_intent.setClass(getActivity(), SearchToolsPage.class);
                startActivityForResult(search_tools_page_intent, 0);
            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle b = data.getExtras();
                String str = b.getString("SearchResult");
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                if (tab != null) {
                    tab.select();
                }
                search(str, view, "1");
                break;
            default:
                break;
        }
    }

    void iniPage(final View view) {
        startProgerssDialog();
        try {
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                    .addParams("pageIndex", "1") //需要传递的参数
                    .addParams("pageSize", PageSize)
                    .addParams("status", "0")
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
                                    ImageView no_client_img = (ImageView) view.findViewById(R.id.no_client_img);

                                    if (clientResultModel.getPageResult().getEntityList().size() == 0) {
                                        client_lst.setVisibility(View.GONE);
                                        no_client_img.setBackgroundResource(R.mipmap.client_manage_no_client);
                                    } else {
                                        client_lst.setVisibility(View.VISIBLE);
                                        no_client_img.setBackground(null);
                                    }
                                    stopProgressDialog();
                                    client_lst.setInterface(new LoadListView.IloadListener() {
                                        @Override
                                        public void onLoad() {

                                            // 刷新太快 所以新启一个线程延迟两秒
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {
                                                        startProgerssDialog();
                                                        OkHttpUtils.get()//get 方法
                                                                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
                                                                .addParams("pageSize", PageSize)
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
                                                                        stopProgressDialog();
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
                                                                                stopProgressDialog();

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
                                    client_lst.setAdapter(adapter);
                                    stopProgressDialog();
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
                            } else {//当没有返回对象时，表示网络没有联通
                                stopProgressDialog();
                                Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            stopProgressDialog();
            Log.i("客户列表加载异常：", e.getMessage());

            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    private void search(String str, final View view, String index) {
        try {
            startProgerssDialog();
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                    .addParams("pageIndex", index) //需要传递的参数
                    .addParams("pageSize", PageSize)
                    .addParams("status", "0")
                    .addParams("keyword", str)
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
                            stopProgressDialog();
                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            //object 是 parseNetworkResponse的返回值
                            if (null != o) {
                                clientResultModel = (ClientResultModel) o;//把通用的Object转化成指定的对象
                                if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    LoadListView client_lst = (LoadListView) view.findViewById(R.id.clients_list);
                                    ImageView no_client_img = (ImageView) view.findViewById(R.id.no_client_img);

                                    if (clientResultModel.getMarketEntity() == null || clientResultModel.getMarketEntity().length() == 0) {
                                        client_lst.setVisibility(View.GONE);
                                        no_client_img.setBackgroundResource(R.mipmap.client_manage_no_client);
                                    } else {
                                        client_lst.setVisibility(View.VISIBLE);
                                        no_client_img.setBackground(null);
                                    }

                                    adapter.setClients(clientResultModel);

                                    stopProgressDialog();
                                    client_lst.setInterface(new LoadListView.IloadListener() {
                                        @Override
                                        public void onLoad() {

                                            // 刷新太快 所以新启一个线程延迟两秒
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {
                                                        startProgerssDialog();
                                                        OkHttpUtils.get()//get 方法
                                                                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list") //地址
                                                                .addParams("pageIndex", Integer.toString(clientResultModel.getPageResult().getPageIndex() + 1)) //需要传递的参数
                                                                .addParams("pageSize", PageSize)
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
                                                                        stopProgressDialog();
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
                                                                                stopProgressDialog();

                                                                            } else {
                                                                               // Toast.makeText(getActivity(), "数据结束！", Toast.LENGTH_SHORT).show();
                                                                                stopProgressDialog();
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
                                                                            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                                                                            stopProgressDialog();
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
                                    client_lst.setAdapter(adapter);
                                    stopProgressDialog();
                                } else {
                                    stopProgressDialog();
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
