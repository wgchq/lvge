package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
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

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.http.NetworkConfig;
import lvge.com.myapp.model.CommodityNewgoodsGiftMode;
import lvge.com.myapp.model.EmployeeInformationList;
import lvge.com.myapp.model.EmployeeInformationMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.PostListMode;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformation;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationAdapter;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationAddPost;
import okhttp3.Call;
import okhttp3.Response;

public class CommodityNewgoodsGift extends AppCompatActivity {

    private TextView commodity_newgoods_gift_add;
    private SwipeMenuListView commodity_newgoods_gift_listview;

    private Context context;
    private CustomProgressDialog progressDialog = null;
    private String gift = "";
    private int giftid;

    private CommodityNewgoodsGiftAdapter commodityNewgoodsGiftAdapter;

    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();  //判断单选位置
    private List<CommodityNewgoodsGiftMode.MarketEntity.EntityList> contentList = new ArrayList<CommodityNewgoodsGiftMode.MarketEntity.EntityList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_newgoods_gift);
        context = this;

        commodity_newgoods_gift_add = (TextView)findViewById(R.id.commodity_newgoods_gift_add);
        commodity_newgoods_gift_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommodityNewgoodsGift.this,CommodityNewgoodsGiftAdd.class);
                startActivity(intent);
            }
        });
        commodity_newgoods_gift_listview = (SwipeMenuListView)findViewById(R.id.commodity_newgoods_gift_listview);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem openItem = new SwipeMenuItem(context);
                openItem.setBackground(new ColorDrawable(Color.RED));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("删除");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                swipeMenu.addMenuItem(openItem);
            }
        };

        commodity_newgoods_gift_listview.setMenuCreator(creator);

        commodity_newgoods_gift_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                CommodityNewgoodsGiftMode.MarketEntity.EntityList item = contentList.get(i);
                switch (i1) {
                    case 0:
                        //removeConsultant(item.getJOB_ID());
                        String sdr = String.valueOf(item.getGiftID());
                        // getListItem();
                        try{
                            OkHttpUtils.post()//get 方法
                                    .url(NetworkConfig.BASE_URL+"sellerapp/goods/giftDelete") //地址
                                    .addParams("GIFT_ID", sdr)
                                    .build()
                                    .execute(new Callback() {//通用的callBack

                                        //从后台获取成功后，对相应进行类型转化
                                        @Override
                                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                                            String string = response.body().string();//获取相应中的内容Json格式
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
                                                    initgiftInformation();
                                                } else {
                                                    Toast.makeText(CommodityNewgoodsGift.this, "删除失败！", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {//当没有返回对象时，表示网络没有联通
                                                Toast.makeText(CommodityNewgoodsGift.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } catch (Exception e) {
                            Toast.makeText(CommodityNewgoodsGift.this, "网络异常！", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        commodity_newgoods_gift_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkPosition(position);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_newgoods_gift);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();

                Intent intent = new Intent();
                intent.putExtra("inputgift",gift);
                intent.putExtra("inputid",giftid);
                setResult(17,intent);
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    });

}

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected void onResume(){
        super.onResume();
        initgiftInformation();
    }

    private void initgiftInformation() {
        Log.d("3","获取赠品信息");
        startProgerssDialog();
        contentList.clear();
        try {
            OkHttpUtils.get()
                    .url(NetworkConfig.BASE_URL+"sellerapp/goods/giftQuery")
                    .addParams("pageIndex","1")
                    .addParams("pageSize","10")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            CommodityNewgoodsGiftMode result = new Gson().fromJson(string, CommodityNewgoodsGiftMode.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                CommodityNewgoodsGiftMode result = (CommodityNewgoodsGiftMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                    contentList = result.getMarketEntity().getEntityList();
                                    for (int j=0;j<contentList.size();j++){
                                        checkList.add(false);
                                    }
                                    //commodity_newgoods_gift_listview.setAdapter(new CommodityNewgoodsGiftAdapter(CommodityNewgoodsGift.this, contentList,checkList));
                                    commodityNewgoodsGiftAdapter = new CommodityNewgoodsGiftAdapter(CommodityNewgoodsGift.this, contentList,checkList);
                                    commodity_newgoods_gift_listview.setAdapter(commodityNewgoodsGiftAdapter);
                                    stopProgressDialog();
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            stopProgressDialog();
            Toast.makeText(CommodityNewgoodsGift.this,"获取失败！",Toast.LENGTH_LONG).show();
        }
    }
    private void startProgerssDialog(){
        if(progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            // progressDialog.setMessage("正在加载中。。");
        }
        progressDialog.show();
    }

    private void stopProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void checkPosition(int position) {
        giftid = contentList.get(position).getGiftID();
        gift = contentList.get(position).getName();

        for (int i = 0; i < checkList.size(); i++) {
            if (position == i) {
                checkList.set(i, true);
            } else {
                checkList.set(i, false);
            }
        }
        commodityNewgoodsGiftAdapter.notifyDataSetChanged();
    }

    public class CommodityNewgoodsGiftAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;  //视图容器
        private List<CommodityNewgoodsGiftMode.MarketEntity.EntityList>  listItems;   //商品信息集合
        private List<Boolean> list;

        public CommodityNewgoodsGiftAdapter(Context context, List<CommodityNewgoodsGiftMode.MarketEntity.EntityList> contentList,List<Boolean> list) {
            this.context = context;
            this.list = list;
            layoutInflater = LayoutInflater.from(context);
            this.listItems = contentList;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public final class ListItemView{
            private TextView textView;
            private ImageView checkedTextView;
        }

        @Override
        public View getView(final  int position, View convertView, ViewGroup parent) {

            final ListItemView listItemView ;
            View view;
            if(convertView == null){
                listItemView = new ListItemView();
                view = layoutInflater.inflate(R.layout.commodity_newgoods_gift_item,null);

                listItemView.textView = (TextView)view.findViewById(R.id.commodity_newgoods_gift_item_textview);
                listItemView.checkedTextView = (ImageView)view.findViewById(R.id.gift_list_item_check);

                view.setTag(listItemView);
            }else {
                view = convertView;
                listItemView = (ListItemView)view.getTag();
            }
            listItemView.textView.setText(listItems.get(position).getName());

            if (list.get(position)) {
                listItemView.checkedTextView.setImageResource(R.mipmap.checkbox_checked);
            } else {
                // holder.checkBox.setChecked(false);
                listItemView.checkedTextView.setImageResource(R.color.background_gray);
            }

/*
            view.setOnClickListener(new View.OnClickListener() {  //item进行单选设置
                @Override
                public void onClick(View v) {
                    checkPosition(position);
                }
            });
*/

            return view;
        }
    }
}
