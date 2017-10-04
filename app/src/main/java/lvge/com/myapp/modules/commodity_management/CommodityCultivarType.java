package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lvge.com.myapp.R;

public class CommodityCultivarType extends AppCompatActivity {

    private ListView listView;
    private CommodityCultivarTypeAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<String>(){
        {
            add("导航安装提成");
            add("一键启动/手机启动安装提成");
            add("全景360可视系统安装提成");
            add("智能升窗器安装提成");
            add("倒车雷达系统安装提成");
            add("隐藏式行车记录仪安装提成");
            add("大灯总成安装提成");
            add("日间行车灯安装提成");
        }
    };

    private ArrayList<String> list2 = new ArrayList<String>(){
        {
            add("工时提成100元");
            add("工时提成300元");
            add("工时提成50元");
            add("工时提成200元");
            add("工时提成30元");
            add("工时提成100元");
            add("工时提成100元");
            add("工时提成100元");
        }
    };

    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();  //判断单选位置

    private String str = "";
    private String str2 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_cultivar_type);

        initListview();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_install_list);
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

        TextView commodity_newgoods_Commoditytype_finish = (TextView)findViewById(R.id.commodity_cultivar_Explain);
        commodity_newgoods_Commoditytype_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inputcommoditychoose",str);
                intent.putExtra("inputhowmonet",str2);
                setResult(13,intent);
                finish();
            }
        });
    }

    public void checkPosition(int position){
        str = list.get(position);
        str2 = list2.get(position);
        for (int i=0; i<checkList.size();i++){
            if (position == i){
                checkList.set(i,true);
            }else {
                checkList.set(i,false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void initListview(){
        listView = (ListView)findViewById(R.id.commodity_newgoods_cultivartype_listview);
        for (int i=0;i<list.size();i++){
            checkList.add(false);
        }
        mAdapter = new CommodityCultivarTypeAdapter(CommodityCultivarType.this,list,list2);
        listView.setAdapter(mAdapter);
    }

    private class CommodityCultivarTypeAdapter extends BaseAdapter {
        ArrayList<String> list;
        ArrayList<String> list2;
        private Context mContext;

        public CommodityCultivarTypeAdapter(Context context,ArrayList<String> list,ArrayList<String> list2){
            this.mContext = context;
            this.list = list;
            this.list2 = list2;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.commodity_cultivar_type_list, null);
                holder = new ViewHolder();
                holder.commodity_install_textview7 = (TextView)convertView.findViewById(R.id.commodity_install_textview7);
                holder.commodity_cultivartype_edittext7 = (TextView)convertView.findViewById(R.id.commodity_cultivartype_edittext7);
                holder.checkBox = (ImageView)convertView.findViewById(R.id.commodity_install_imagebutton7);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            String str = list.get(position);
            holder.commodity_install_textview7.setText(list.get(position));
            holder.commodity_cultivartype_edittext7.setText(list2.get(position));
            if (checkList.get(position)){
                holder.checkBox.setImageResource(R.mipmap.checkbox_checked);
            }else {
                // holder.checkBox.setChecked(false);
                holder.checkBox.setImageResource(R.color.background_gray);
            }

            convertView.setOnClickListener(new View.OnClickListener() {  //item进行单选设置
                @Override
                public void onClick(View v) {
                    checkPosition(position);
                }
            });

            return convertView;
        }

        public  class ViewHolder{
            public TextView commodity_install_textview7;
            public TextView commodity_cultivartype_edittext7;
            public ImageView checkBox;
        }
    }
}
