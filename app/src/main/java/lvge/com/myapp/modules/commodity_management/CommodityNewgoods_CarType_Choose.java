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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lvge.com.myapp.R;

public class CommodityNewgoods_CarType_Choose extends AppCompatActivity {

    private ListView listView;
    private SortAdapter sortAdapter;
    private List<CarBean> data;
    private SideBar sideBar;
    private TextView diaglog;

    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();  //判断单选位置
    private String str = "";

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
        listView = (ListView)findViewById(R.id.car_choose_type_listview);
        diaglog = (TextView)findViewById(R.id.dialog);
        sideBar.setTextView(diaglog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                  int position = sortAdapter.getPositionForSelection(s.charAt(0));
                if(position != -1){
                    listView.setSelection(position);
                }
            }
        });

        data = getData(getResources().getStringArray(R.array.listcar));

        for (int i=0;i<data.size();i++){
            checkList.add(false);
        }
        // 数据在放在adapter之前需要排序
        Collections.sort(data, new PinyinComparator());
        sortAdapter = new SortAdapter(this, data,checkList);
        listView.setAdapter(sortAdapter);
    }

    private List<CarBean> getData(String[] data) {
        List<CarBean> listarray = new ArrayList<CarBean>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = PinyinUtils.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();

            CarBean person = new CarBean();
            person.setName(data[i]);
            person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setFirstPinYin(Fpinyin);
            } else {
                person.setFirstPinYin("#");
            }

            listarray.add(person);
        }
        return listarray;
    }

    //设置选中位置，将其他位置设置未选
    public void checkPosition(int position) {
        str = data.get(position).getName();

        for (int i = 0; i < checkList.size(); i++) {
            if (position == i) {
                checkList.set(i, true);
            } else {
                checkList.set(i, false);
            }
        }
        sortAdapter.notifyDataSetChanged();
    }

    public class SortAdapter extends BaseAdapter {
        private Context context;
        private List<CarBean> cars;
        private LayoutInflater inflater;
        private List<Boolean> list;

        public SortAdapter(Context context, List<CarBean> car, List<Boolean> list) {
            this.context = context;
            this.cars = car;
            this.list = list;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return cars.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            ImageView iv_lv_item_head;
            ImageView commodity_newgoods_commodity_type_checkbox;
            TextView tv_tag;
            TextView tv_name;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            CarBean car = cars.get(position);

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder.tv_tag = (TextView) convertView.findViewById(R.id.tv_lv_item_tag);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_lv_item_name);
                viewHolder.iv_lv_item_head = (ImageView) convertView.findViewById(R.id.iv_lv_item_head);
                viewHolder.commodity_newgoods_commodity_type_checkbox = (ImageView) convertView.findViewById(R.id.commodity_newgoods_commodity_type_checkbox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            int selection = car.getFirstPinYin().charAt(0);
            int positionForSelection = getPositionForSelection(selection);

            if (position == positionForSelection) {
                viewHolder.tv_tag.setVisibility(View.VISIBLE);
                viewHolder.tv_tag.setText(car.getFirstPinYin());
            } else {
                viewHolder.tv_tag.setVisibility(View.GONE);
            }
            viewHolder.tv_name.setText(car.getName());

            if (list.get(position)) {
                viewHolder.commodity_newgoods_commodity_type_checkbox.setImageResource(R.mipmap.checkbox_checked);
            } else {
                // holder.checkBox.setChecked(false);
                viewHolder.commodity_newgoods_commodity_type_checkbox.setImageResource(R.color.background_gray);
            }

            convertView.setOnClickListener(new View.OnClickListener() {  //item进行单选设置
                @Override
                public void onClick(View v) {
                    checkPosition(position);
                }
            });

            switch (car.getName()) {
                case "吉普":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a50);
                    break;
                case "MG":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a79);
                    break;
                case "mini":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a80);
                    break;
                case "奥迪":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a2);
                    break;
                case "宝骏":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a4);
                    break;
                case "宝马":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a5);
                    break;
                case "保时捷":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a6);
                    break;
                case "北汽":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a9);
                    break;
                case "奔驰":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a10);
                    break;
                case "奔腾":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a11);
                    break;
                case "本田":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a12);
                    break;
                case "比亚迪":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a13);
                    break;
                case "标致":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a14);
                    break;
                case "别克":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a15);
                    break;
                case "大迪":
                    break;
                case "大众":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a23);
                    break;
                case "帝豪":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a51);
                    break;
                case "东风风行":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a26);
                    break;
                case "东风日产":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a92);
                    break;
                case "菲亚特":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a32);
                    break;
                case "丰田":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a33);
                    break;
                case "福迪":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a34);
                    break;
                case "福特":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a35);
                    break;
                case "广汽":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a39);
                    break;
                case "海马":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a44);
                    break;
                case "瑞麒":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a95);
                    break;
                case "威旺":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a8);
                    break;
                case "五菱":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a110);
                    break;
                case "现代":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a113);
                    break;
                case "雪弗兰":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a115);
                    break;
                case "雪铁龙":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a116);
                    break;
                case "英菲尼迪":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a120);
                    break;
                case "英伦汽车":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a53);
                    break;
                case "永源":
                    break;
                case "长安":
                    viewHolder.iv_lv_item_head.setImageResource(R.mipmap.a19);
                    break;
                case "长安铃木":
                    break;
                case "长安商用":
                    break;
                case "长城汽车":
                    break;
                case "中华":
                    break;
                case "中兴":
                    break;
                case "众泰":
                    break;
                case "华普":
                    break;
                case "黄海":
                    break;
                case "汇总":
                    break;
                case "吉奥":
                    break;
                case "江淮":
                    break;
                case "江铃":
                    break;
                case "江南":
                    break;
                case "九龙":
                    break;
                case "解放":
                    break;
                case "卡迪拉克":
                    break;
                case "开瑞":
                    break;
                case "兰博基尼":
                    break;
                case "雷克萨斯":
                    break;
                case "雷诺":
                    break;
                case "理念":
                    break;
                case "莲花":
                    break;
                case "陆风":
                    break;
                case "路虎":
                    break;
                case "马自达":
                    break;
                case "纳智捷":
                    break;
                case "奇瑞":
                    break;
                case "启辰":
                    break;
                case "起亚":
                    break;
                case "全球鹰":
                    break;
                case "荣威":
                    break;
                case "三菱":
                    break;
            }

            return convertView;
        }

        public int getPositionForSelection(int selection) {
            for (int i = 0; i < cars.size(); i++) {
                String Fpinyin = cars.get(i).getFirstPinYin();
                char first = Fpinyin.toUpperCase().charAt(0);
                if (first == selection) {
                    return i;
                }
            }
            return -1;

        }

    }

}
