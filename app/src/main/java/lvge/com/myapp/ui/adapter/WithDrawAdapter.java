package lvge.com.myapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.WithDrawModel;
import lvge.com.myapp.view.MenuAdapter;

/**
 * Created by JGG on 2017-10-17.
 */

public class WithDrawAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private List<WithDrawModel> WithDrawModelList;

    public WithDrawAdapter(Context mContext,List<WithDrawModel> list) {
        this.mContext = mContext;
        WithDrawModelList = list;
    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_with_draw_item, null);

            TextView with_drawal_date = (TextView) convertView.findViewById(R.id.with_drawal_date);
            TextView with_drawal_status = (TextView) convertView.findViewById(R.id.with_drawal_status);
            TextView with_drawal_cash = (TextView) convertView.findViewById(R.id.with_drawal_cash);

            with_drawal_date.setText("");
            with_drawal_status.setText("");
            with_drawal_cash.setText("");

        } else {

        }

        return convertView;

    }
}
