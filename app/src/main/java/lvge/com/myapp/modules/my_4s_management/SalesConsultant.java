package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.SalesConsultantGson;
import okhttp3.Response;

public class SalesConsultant extends AppCompatActivity {

    private SalesConsultantListview customListView;

    private SalesConsutantListViewAdapter mAdapter ;

    private List<SalesConsutantListViewData> contentList = new ArrayList<SalesConsutantListViewData>();

    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant);


        ImageView sale_consultant_back = (ImageView) findViewById(R.id.sale_consultant_back);
        TextView sale_consultant_toadd = (TextView) findViewById(R.id.sale_consultant_toadd);
        customListView = (SalesConsultantListview) findViewById(R.id.sales_consultant_listview);
        customListView.setDelButtonClickListener(new SalesConsultantListview.DelButtonClickListener() {
            @Override
            public void clickHappend(int position) {
                contentList.remove(position);
                mAdapter.notifyDataSetChanged();
            }


        });

        sale_consultant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sale_consultant_toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesConsultant.this, SaleConsultantTwo.class);
                startActivity(intent);
            }
        });

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {    //点击listView事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        getListItem();


    }

    private void  getListItem() {
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

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                SalesConsultantGson result = (SalesConsultantGson) object;//把通用的Object转化成指定的对象
                                //当返回值为2时不可登录
                                if (result.getOperationResult().getResultCode() == 0) {
                                    SalesConsutantListViewData item = null;
                                    for (int j = 0; j < result.getMarketEntity().size(); j++) {
                                        item = new SalesConsutantListViewData();
                                        item.setName(result.getMarketEntity().get(j).getName());
                                        item.setPhone(result.getMarketEntity().get(j).getPhone());
                                        item.setMemo(result.getMarketEntity().get(j).getMemo());
                                        item.setHeadlmg(result.getMarketEntity().get(j).getHeadImg());
                                        contentList.add(item);
                                }
                                    if (contentList != null) {
                                        customListView.setAdapter(new SalesConsutantListViewAdapter(SalesConsultant.this,contentList));
                                    }

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

}
