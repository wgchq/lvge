package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.SalesConsultantResultModel;
import lvge.com.myapp.model.SalesConsutantListViewData;

/**
 * Created by mac on 2017/7/4.
 */

public class SalesConsutantListViewAdapter extends BaseAdapter {

    private Context context;

    public SalesConsultantResultModel getSalesConsultantResultModel() {
        return salesConsultantResultModel;
    }

    public void setSalesConsultantResultModel(SalesConsultantResultModel salesConsultantResultModel) {
        this.salesConsultantResultModel = salesConsultantResultModel;
    }

    private SalesConsultantResultModel salesConsultantResultModel;

    public List<SalesConsutantListViewData> getListItems() {
        return listItems;
    }

    public void setListItems(List<SalesConsutantListViewData> listItems) {
        this.listItems = listItems;
    }

    private List<SalesConsutantListViewData>  listItems;   //商品信息集合
    private LayoutInflater layoutInflater;  //视图容器

    public final class ListItemView{
        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public ImageView imageView;

        public TextView getSname() {
            return sname;
        }

        public void setSname(TextView sname) {
            this.sname = sname;
        }

        public TextView getPhone() {
            return phone;
        }

        public void setPhone(TextView phone) {
            this.phone = phone;
        }

        public TextView getMemo() {
            return memo;
        }

        public void setMemo(TextView memo) {
            this.memo = memo;
        }

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
        return listItems.get(arg0);
    }

    public long getItemId(int arg1){
        return arg1;
    }

    public View getView(int position,View convertView, ViewGroup parent){
        ListItemView listItemView ;
        View view;
        if(convertView == null){
            listItemView = new ListItemView();
            view = layoutInflater.inflate(R.layout.sales_consultant_listview,null);

            //获取控件对象
            listItemView.imageView = (ImageView)view.findViewById(R.id.sale_consultant_iamage);
            listItemView.sname = (TextView)view.findViewById(R.id.sales_consultant_liatview_name);
            listItemView.phone = (TextView)view.findViewById(R.id.sales_consultant_liatview_number);
            listItemView.memo = (TextView)view.findViewById(R.id.sales_consultant_liatview_text);

            view.setTag(listItemView);
        }else {
            view = convertView;
            listItemView = (ListItemView)view.getTag();
        }

        SalesConsutantListViewData item =listItems.get(position);
        //listItemView.imageView.setImageBitmap(item.getHeadlmg());
        listItemView.sname.setText(item.getName());
        listItemView.phone.setText(item.getPhone());
        listItemView.memo.setText(item.getMemo());

        return view;
    }

}
