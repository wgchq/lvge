package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.SalesConsultantGson;
import lvge.com.myapp.model.SalesConsultantResultModel;
import lvge.com.myapp.model.SalesConsutantListViewData;
import lvge.com.myapp.modules.customer_management.CustomerData;
import okhttp3.Response;

public class SalesConsultant extends AppCompatActivity {

    private SwipeMenuListView customListView;

    private SalesConsutantListViewAdapter mAdapter;

    private List<SalesConsutantListViewData> contentList = new ArrayList<SalesConsutantListViewData>();

    private String string;

    private SalesConsultantResultModel salesConsultantResultModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_4s_sales_consultant);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                startActivity(intent);
            }
        });

       // ImageView sale_consultant_back = (ImageView) findViewById(R.id.sale_consultant_back);
        TextView sale_consultant_toadd = (TextView) findViewById(R.id.sale_consultant_toadd);
        customListView = (SwipeMenuListView) findViewById(R.id.sales_consultant_listview);


        sale_consultant_toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesConsultant.this, SaleConsultantTwo.class);
                startActivity(intent);
            }
        });

        getListItem();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.mipmap.delete);
                swipeMenu.addMenuItem(deleteItem);
            }
        };

        customListView.setMenuCreator(creator);

        customListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                SalesConsutantListViewData item = contentList.get(i);
                switch (i1) {
                    case 0:
                        removeConsultant(item.getId());
                        getListItem();
                }
            }
        });

        customListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int i) {

            }

            @Override
            public void onSwipeEnd(int i) {

            }
        });

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SalesConsultant.this, SaleConsultantTwo.class);
                String clientId = contentList.get(position).getId();
                intent.putExtra("id", clientId);
                startActivity(intent);
            }
        });

        customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    private void getListItem() {

        try {

            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/list") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            SalesConsultantGson result = new Gson().fromJson(string, SalesConsultantGson.class);

                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {
                            contentList.clear();
                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                SalesConsultantGson result = (SalesConsultantGson) object;//把通用的Object转化成指定的对象
                                //当返回值为2时不可登录
                                if (result.getOperationResult().getResultCode() == 0) {
                                    //  salesConsultantResultModel.getMarketEntity().addAll(result.getMarketEntity());
                                    SalesConsutantListViewData item = null;
                                    for (int j = 0; j < result.getMarketEntity().size(); j++) {
                                        item = new SalesConsutantListViewData();
                                        item.setName(result.getMarketEntity().get(j).getName());
                                        item.setPhone(result.getMarketEntity().get(j).getPhone());
                                        item.setMemo(result.getMarketEntity().get(j).getMemo());
                                        item.setHeadlmg(result.getMarketEntity().get(j).getHeadImg());
                                        item.setId(result.getMarketEntity().get(j).getId());
                                        contentList.add(item);
                                    }
                                    if (contentList != null) {
                                        customListView.setAdapter(new SalesConsutantListViewAdapter(SalesConsultant.this, contentList));

                                    }
                                    //  mAdapter.setSalesConsultantResultModel(salesConsultantResultModel);
                                    //  customListView.setAdapter(mAdapter);

                                } else {
                                    // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    // startActivity(intent);
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(SalesConsultant.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }

    }

    private void removeConsultant(String id) {
        try {

            OkHttpUtils.post()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/delete") //地址
                    .addParams("id", id)
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            string = response.body().string();//获取相应中的内容Json格式
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
                                    //  salesConsultantResultModel.getMarketEntity().addAll(result.getMarketEntity());
                                    //  mAdapter.setSalesConsultantResultModel(salesConsultantResultModel);
                                    //  customListView.setAdapter(mAdapter);
                                    Toast.makeText(SalesConsultant.this, "删除成功！", Toast.LENGTH_SHORT).show();

                                } else {
                                    // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    // startActivity(intent);
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(SalesConsultant.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume(){
        super.onResume();
        getListItem();
    }

}
