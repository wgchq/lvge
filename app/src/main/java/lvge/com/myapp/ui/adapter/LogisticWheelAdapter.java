package lvge.com.myapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.widget.WheelItem;

import java.util.List;

import lvge.com.myapp.model.order.LogisticCompanyModel;

/**
 * Created by android on 2017/10/17.
 */

public class LogisticWheelAdapter extends BaseWheelAdapter<LogisticCompanyModel> {

    private Context mContext;

    public LogisticWheelAdapter(Context context, List<LogisticCompanyModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new WheelItem(mContext);
        }
        WheelItem item = (WheelItem) convertView;
        item.setText(mList.get(position).getCompanyName());
        return convertView;
    }

}