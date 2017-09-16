package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.PostListMode;
import lvge.com.myapp.model.SellerImgs;

/**
 * Created by mac on 2017/9/16.
 */

public class EmployeeInformationAddPostAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;  //视图容器
    private List<PostListMode> listItems;   //商品信息集合

    public EmployeeInformationAddPostAdapter(Context context , List<PostListMode> listItems){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public final class ListItemView{
        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        private TextView textView;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        private ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListItemView listItemView ;
        View view;

        if(convertView == null){
            listItemView = new EmployeeInformationAddPostAdapter.ListItemView();
            view = layoutInflater.inflate(R.layout.post_list_item,null);

            //获取控件对象
           // listItemView.imageView = (ImageView)view.findViewById(R.id.sale_consultant_iamage);
          //  listItemView.sname = (TextView)view.findViewById(R.id.sales_consultant_liatview_name);
           // listItemView.phone = (TextView)view.findViewById(R.id.sales_consultant_liatview_number);
            listItemView.textView = (TextView)view.findViewById(R.id.post_list_item);
            listItemView.imageView = (ImageView)view.findViewById(R.id.post_list_item_imagevie);

            view.setTag(listItemView);

        }else {
            view = convertView;
            listItemView = (EmployeeInformationAddPostAdapter.ListItemView)view.getTag();
        }

        final PostListMode item =listItems.get(position);

        listItemView.textView.setText(item.getJOB_NAME());
        listItemView.setImageView(null);
        return view;
    }
}
