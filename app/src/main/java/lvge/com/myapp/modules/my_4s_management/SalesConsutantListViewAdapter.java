package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import antlr.debug.MessageAdapter;
import lvge.com.myapp.R;

/**
 * Created by mac on 2017/7/4.
 */

public class SalesConsutantListViewAdapter extends BaseAdapter {

    private Context context;
    private List<SalesConsutantListViewData>  listItems;   //商品信息集合
    private LayoutInflater layoutInflater;  //视图容器

    public final class ListItemView{
        public ImageView imageView;
        public TextView sname;
        public TextView phone;
        public TextView memo;
    }

    public SalesConsutantListViewAdapter(Context context , List<SalesConsutantListViewData> listItems){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    public int getCount(){
        return listItems.size();
    }

    public Object getItem(int arg0){
        return null;
    }

    public long getItemId(int arg1){
        return 0;
    }

    public View getView(int position,View convertView, ViewGroup parent){
        ListItemView listItemView = null;
        if(convertView == null){
            listItemView = new ListItemView();
            convertView = layoutInflater.inflate(R.layout.sales_consultant_listview,null);

            //获取控件对象
            listItemView.imageView = (ImageView)convertView.findViewById(R.id.sale_consultant_iamage);
            listItemView.sname = (TextView)convertView.findViewById(R.id.sales_consultant_liatview_name);
            listItemView.phone = (TextView)convertView.findViewById(R.id.sales_consultant_liatview_number);
            listItemView.memo = (TextView)convertView.findViewById(R.id.sales_consultant_liatview_text);

            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }

        SalesConsutantListViewData item =listItems.get(position);
        //listItemView.imageView.setImageBitmap(item.getHeadlmg());
        listItemView.sname.setText(item.getName());
        listItemView.phone.setText(item.getPhone());
        listItemView.memo.setText(item.getMemo());

        return convertView;
    }

}
