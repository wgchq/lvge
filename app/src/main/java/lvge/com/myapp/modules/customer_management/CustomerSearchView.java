package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;
import lvge.com.myapp.view.LoadListView;
import okhttp3.Call;
import okhttp3.Response;

public class CustomerSearchView extends LinearLayout{

    private Context context;

    private EditText etInput;   //输入框
    private ImageView ivDelete;  //删除键
    private TextView tv_clear;
    private TextView tv_tip;

    private Button btnBack;   //返回建

    private ClientResultModel clientResultModel;


    private CustomerSearchListview listview;

    ClientsAdapter adapter;

    private SearchViewListener mListener;

    public interface SearchViewListener {
        void onRefreshAutoComplete(String text);

        void onSearch(String text);
    }

    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public CustomerSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        context = context;
        //LayoutInflater.from(context).inflate(R.layout.activity_customer_search_view, this);
        LayoutInflater.from(context).inflate(R.layout.activity_customer_search_view,this);
        initViews();
    }

    public CustomerSearchView(Context context){
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.activity_customer_search_view,this);
        initViews();
    }

    public CustomerSearchView(Context context,AttributeSet attributeSet,int defStyleAtr){
        super(context,attributeSet,defStyleAtr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.activity_customer_search_view,this);
        initViews();
    }

    private void initViews() {


        etInput = (EditText) findViewById(R.id.customer_search_input);   //输入框
       // ivDelete = (ImageView) findViewById(R.id.search_custmer);
        btnBack = (Button) findViewById(R.id.search_customer_button);
        tv_clear = (TextView)findViewById(R.id.tv_clear);
        tv_tip = (TextView)findViewById(R.id.tv_tip);
        listview = (CustomerSearchListview)findViewById(R.id.customer_search_listview);
        // lvTips = (ListView) findViewById(R.id.search_custmer_listview);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tempName = etInput.getText().toString();
            }
        });

        etInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    OkHttpUtils.post()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/list")
                            .addParams("keyword",etInput.getText().toString().trim())
                            .addParams("pageIndex", "1") //需要传递的参数
                            .addParams("pageSize", "10")
                            .addParams("totalPage","1")
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
                                public void onResponse(Object object, final int i) {
                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        clientResultModel = (ClientResultModel) object;//把通用的Object转化成指定的对象
                                        if (clientResultModel.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                            adapter.setClients(clientResultModel);
                                            LoadListView client_lst = (LoadListView) findViewById(R.id.clients_list);
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
                                                                                        LoadListView client = (LoadListView)findViewById(R.id.clients_list);

                                                                                        client.setAdapter(adapter);
                                                                                        client.setSelection(client.getBottom());
                                                                                        client.loadComplete();

                                                                                    } else {
                                                                                        Toast.makeText(getContext(), "数据结束！", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {//当没有返回对象时，表示网络没有联通
                                                                                    Toast.makeText(getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(getContext(), "数据结束！", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){

                }
                return false;
            }
        });

    }


}
