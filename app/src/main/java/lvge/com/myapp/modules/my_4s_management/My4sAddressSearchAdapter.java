package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.AddressModel;

/**
 * Created by JGG on 2017/7/15.
 */

public class My4sAddressSearchAdapter extends BaseAdapter {
    private List<AddressModel> data;
    private LayoutInflater li;

    public My4sAddressSearchAdapter(Context context) {
        li = LayoutInflater.from(context);
    }

    /**
     * 设置数据集
     *
     * @param data
     */
    public void setData(List<AddressModel> data) {
        this.data = data;
    }
    public void clearData(){
        this.data.clear();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = li.inflate(R.layout.shop_4s_shop_address_item, null);
            vh.title = (TextView) convertView.findViewById(R.id.item_title);
            vh.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(data.get(position).getTitle());
        vh.text.setText(data.get(position).getText());
        return convertView;
    }

    private class ViewHolder {
        public TextView title;
        public TextView text;
    }
}