package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.CommodityNewgoodsGiftMode;
import lvge.com.myapp.model.EmployeeInformationList;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationAdapter;

/**
 * Created by mac on 2017/10/14.
 */

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
    public View getView(int position, View convertView, ViewGroup parent) {

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

        view.setOnClickListener(new View.OnClickListener() {  //item进行单选设置
            @Override
            public void onClick(View v) {
                //checkPosition(position);
            }
        });

        return view;
    }
}
