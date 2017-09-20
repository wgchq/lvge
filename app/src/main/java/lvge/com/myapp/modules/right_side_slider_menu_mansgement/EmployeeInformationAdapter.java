package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.EmployeeInformationList;
import lvge.com.myapp.model.SalesConsutantListViewData;
import lvge.com.myapp.model.SellerImgs;
import lvge.com.myapp.modules.my_4s_management.SalesConsutantListViewAdapter;
import okhttp3.Call;

/**
 * Created by mac on 2017/9/16.
 */

public class EmployeeInformationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;  //视图容器
    private List<EmployeeInformationList>  listItems;   //商品信息集合

    public EmployeeInformationAdapter(Context context , List<EmployeeInformationList> listItems){
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

        public TextView sname;

        public TextView getPhone() {
            return phone;
        }

        public void setPhone(TextView phone) {
            this.phone = phone;
        }

        public TextView phone;

        public TextView getMemo() {
            return memo;
        }

        public void setMemo(TextView memo) {
            this.memo = memo;
        }

        public TextView memo;

        public ImageView reImageview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListItemView listItemView ;
        View view;
        if(convertView == null){
            listItemView = new ListItemView();
            view = layoutInflater.inflate(R.layout.sales_consultant_listview,null);

            //获取控件对象
            listItemView.imageView = (ImageView)view.findViewById(R.id.sale_consultant_iamage);
            listItemView.sname = (TextView)view.findViewById(R.id.sales_consultant_liatview_name);
            listItemView.phone = (TextView)view.findViewById(R.id.sales_consultant_liatview_number);
            listItemView.memo = (TextView)view.findViewById(R.id.sales_consultant_liatview_text);
            listItemView.reImageview= (ImageView)view.findViewById(R.id.imageView);

            view.setTag(listItemView);

        }else {
            view = convertView;
            listItemView = (EmployeeInformationAdapter.ListItemView)view.getTag();
        }

        final EmployeeInformationList item =listItems.get(position);
        if(item.getHeadImg() != null && !item.getHeadImg().equals("")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpUtils.get()
                            .url(item.getHeadImg())
                            .build()
                            .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                            .execute(new BitmapCallback() {
                                @Override
                                public void onError(Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(Bitmap bitmap, int i) {
                                    // headimge.setImageBitmap(bitmap);
                                    listItemView.imageView.setImageBitmap(bitmap);
                                }
                            });
                }
            }).start();
        }
        listItemView.sname.setText(item.getNAME());
        listItemView.phone.setText(item.getPHONE());
        listItemView.phone.setCompoundDrawables(null,null,null,null);
        listItemView.memo.setText(item.getJob());
        listItemView.reImageview.setImageBitmap(null);

        Log.d("4",item.getUSER_ID() + "获取成功");
        return view;
    }
}
