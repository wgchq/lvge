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

public class CommodityCultivarTypeChoice extends AppCompatActivity {

    private ListView commodity_newgoods_chosoetype_listview;
    private CommodityCultivarTypeChoiceAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<String>(){
        {
            add("去洗车");
            add("去保养");
            add("汽车内饰");
            add("汽车电子");
            add("轮胎");
            add("雨刮片");
        }
    };

    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();  //判断单选位置

    private String str = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_cultivar_type_choice);

        initListview();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_cultivar_typechoose);
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

        TextView commodity_newgoods_Commoditytype_finish = (TextView)findViewById(R.id.commodity_cultivar_typechoose_finish);
        commodity_newgoods_Commoditytype_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inputcultivarchoose",str);
                setResult(11,intent);
                finish();
            }
        });
    }

    public void initListview(){
        commodity_newgoods_chosoetype_listview = (ListView)findViewById(R.id.commodity_newgoods_chosoetype_listview);
        for (int i=0;i<list.size();i++){
            checkList.add(false);
        }
        mAdapter = new CommodityCultivarTypeChoiceAdapter(CommodityCultivarTypeChoice.this,list);
        commodity_newgoods_chosoetype_listview.setAdapter(mAdapter);
    }

    public void checkPosition(int position){
        str = list.get(position);

        for (int i=0; i<checkList.size();i++){
            if (position == i){
                checkList.set(i,true);
            }else {
                checkList.set(i,false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private class CommodityCultivarTypeChoiceAdapter extends BaseAdapter {
        ArrayList<String> list;
        private Context mContext;

        public CommodityCultivarTypeChoiceAdapter(Context context,ArrayList<String> list){
            this.mContext = context;
            this.list = list;
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.commodity_choosecommodity_list, null);
                holder = new ViewHolder();
                holder.textView = (TextView)convertView.findViewById(R.id.commodity_newgoods_commodity_type_textview);
                holder.checkBox = (ImageView)convertView.findViewById(R.id.commodity_newgoods_commodity_type_checkbox);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            String str = list.get(position);
            holder.textView.setText(list.get(position));
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
            public TextView textView;
            public ImageView checkBox;
        }
    }
}
