package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.http.NetworkConfig;
import lvge.com.myapp.model.CommodityNewgoodsCarTypeChooseMode;
import lvge.com.myapp.model.CommodityNewgoodsGiftMode;
import okhttp3.Call;
import okhttp3.Response;

public class CommodityNewgoods_CarType_Choose extends AppCompatActivity {

    private ExpandableListView listView;
    private SortAdapter sortAdapter;
    private List<CommodityNewgoodsCarTypeChooseMode.MarketEntity> data;
    private SideBar sideBar;
    private TextView diaglog;

    private View view;
    private SortAdapter.ChildHolder holder ;

    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();  //判断单选位置
    private String str = "";
    private CustomProgressDialog progressDialog = null;

    private  int[][] checkBoxSelect;


    public int groupPositionChecked = -1;
    public int childPositionChecked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_newgoods__car_type__choose);
        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_cartype);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                // startActivity(intent);
                finish();
            }
        });


        TextView commodity_newgoods_Commoditytype_finish = (TextView)findViewById(R.id.commodity_newgoods_cartype_finish);
        commodity_newgoods_Commoditytype_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inputcarchoose",str);
                setResult(14,intent);
                finish();
            }
        });
    }

    private void  init(){
        sideBar = (SideBar)findViewById(R.id.sidebar);
        listView = (ExpandableListView) findViewById(R.id.car_choose_type_listview);
        diaglog = (TextView)findViewById(R.id.dialog);
        sideBar.setTextView(diaglog);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for(int i=0;i<listView.getExpandableListAdapter().getGroupCount();i++){
                    if(groupPosition != i){
                        listView.collapseGroup(i);
                    }
                }
               // listView.expandGroup(groupPosition);
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                groupPositionChecked = groupPosition;
                childPositionChecked = childPosition;
                return true;
            }
        });

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                  int position = sortAdapter.getPositionForSelection(s.charAt(0));
                if(position != -1){
                    listView.setSelection(position);
                }
            }
        });

        startProgerssDialog();
        try {
            OkHttpUtils.get()
                    .url(NetworkConfig.BASE_URL+"sellerapp/goods/carBrandList")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            CommodityNewgoodsCarTypeChooseMode result = new Gson().fromJson(string, CommodityNewgoodsCarTypeChooseMode.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                stopProgressDialog();
                                CommodityNewgoodsCarTypeChooseMode result = (CommodityNewgoodsCarTypeChooseMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                   data = result.getMarketEntity();
                                    for (int j=0;j<data.size();j++){
                                        checkList.add(false);
                                    }

                                    checkBoxSelect = new int[data.size()][];
                                    for(int m =0;m<data.size();m++)
                                    {
                                        checkBoxSelect[m] = new int[data.get(m).getSeries().size()];
                                    }

                                    // 数据在放在adapter之前需要排序
                                    Collections.sort(data, new Comparator<CommodityNewgoodsCarTypeChooseMode.MarketEntity>() {
                                        @Override
                                        public int compare(CommodityNewgoodsCarTypeChooseMode.MarketEntity o1, CommodityNewgoodsCarTypeChooseMode.MarketEntity o2) {

                                            if(o1.getFirstLetter().hashCode() > o2.getFirstLetter().hashCode()){
                                                return 1;
                                            }
                                            if(o1.getFirstLetter().hashCode() == o2.getFirstLetter().hashCode()){
                                                return 0;
                                            }
                                            return -1;
                                        }
                                    });
                                    sortAdapter = new SortAdapter(CommodityNewgoods_CarType_Choose.this, data,checkList);
                                    listView.setAdapter(sortAdapter);
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            stopProgressDialog();
            Toast.makeText(CommodityNewgoods_CarType_Choose.this,"获取失败！",Toast.LENGTH_LONG).show();
        }

    }



    public class SortAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<CommodityNewgoodsCarTypeChooseMode.MarketEntity> cars;
        private List<CommodityNewgoodsCarTypeChooseMode.MarketEntity.Series> series;
        private LayoutInflater inflater;
        private List<Boolean> list;

        public String string = "";  //第一个字母

        public SortAdapter(Context context, List<CommodityNewgoodsCarTypeChooseMode.MarketEntity> car, List<Boolean> list) {
            this.context = context;
            this.cars = car;
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getGroupCount() {
            return cars.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return cars.get(groupPosition).getSeries().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return cars.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return cars.get(groupPosition).getSeries().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder = null;
            if(view == null){
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.list_item, null);
                holder.tv_tag = (TextView) view.findViewById(R.id.tv_lv_item_tag);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_lv_item_name);
                holder.iv_lv_item_head = (ImageView) view.findViewById(R.id.iv_lv_item_head);
                holder.commodity_newgoods_commodity_type_checkbox = (ImageView) view.findViewById(R.id.commodity_newgoods_commodity_type_checkbox);

                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }

            if(groupPosition == 0){
                holder.tv_tag.setVisibility(View.VISIBLE);
                holder.tv_tag.setText(cars.get(groupPosition).getFirstLetter());
            }else if(cars.get(groupPosition).getFirstLetter().equals(cars.get(groupPosition-1).getFirstLetter())){
                holder.tv_tag.setVisibility(View.GONE);
            }else {
                holder.tv_tag.setVisibility(View.VISIBLE);
                holder.tv_tag.setText(cars.get(groupPosition).getFirstLetter());
            }

           /* if (cars.get(groupPosition).getFirstLetter().equals(string) ) {

            } else {
                string = cars.get(groupPosition).getFirstLetter();

            }*/
            if(isExpanded){
                holder.commodity_newgoods_commodity_type_checkbox.setImageResource(R.mipmap.commodity_list_xiala_right);
            }else {
                holder.commodity_newgoods_commodity_type_checkbox.setImageResource(R.mipmap.commodity_list_gre_xiala);
            }

            holder.tv_name.setText(cars.get(groupPosition).getName());

            return view;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            view = convertView;
            holder = null;
            if(view == null){
                holder = new ChildHolder();

                view = inflater.inflate(R.layout.commodity_choosecommodity_list,null);
                holder.textView = (TextView)view.findViewById(R.id.commodity_newgoods_commodity_type_textview);
                holder.checkBox = (ImageView)view.findViewById(R.id.commodity_newgoods_commodity_type_checkbox);
                holder.commodity_newgoods_type_relayout_fictitious = (RelativeLayout)view.findViewById(R.id.commodity_newgoods_type_relayout_fictitious);
                view.setTag(holder);
            }else {
                holder = (ChildHolder)view.getTag();
            }
            if(childPosition == childPositionChecked && groupPosition == groupPositionChecked){
                holder.checkBox.setImageResource(R.mipmap.checkbox_checked);
            }else {
                holder.checkBox.setImageResource(R.color.background_gray);
            }
            holder.commodity_newgoods_type_relayout_fictitious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBoxSelect[groupPosition][childPosition] == 0)
                    {
                        holder.checkBox.setImageResource(R.mipmap.checkbox_checked);
                        checkBoxSelect[groupPosition][childPosition] = 1;
                    }
                    else
                    {
                        holder.checkBox.setImageResource(R.color.background_gray);
                        checkBoxSelect[groupPosition][childPosition] = 0;
                    }

                }
            });

           holder.textView.setText(cars.get(groupPosition).getSeries().get(childPosition).getName());
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public  class ChildHolder{
            public TextView textView;
            public ImageView checkBox;
            public RelativeLayout commodity_newgoods_type_relayout_fictitious;
        }

        class ViewHolder {
            ImageView iv_lv_item_head;
            ImageView commodity_newgoods_commodity_type_checkbox;
            TextView tv_tag;
            TextView tv_name;
        }


        public int getPositionForSelection(int selection) {
            for (int i = 0; i < cars.size(); i++) {
                String Fpinyin = cars.get(i).getFirstLetter();
                char first = Fpinyin.toUpperCase().charAt(0);
                if (first == selection) {
                    return i;
                }
            }
            return -1;

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

}
