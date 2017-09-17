package lvge.com.myapp.modules.validationhistory;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.HistoryValidationListEntity;
import lvge.com.myapp.model.HistoryValidationModel;

/**
 * Created by JGG on 2017-09-17.
 */

public class HistoryValidationListAdapter extends BaseAdapter {

    private Context mContext;

    private List<HistoryValidationModel> historyValidationModels;

    public HistoryValidationListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<HistoryValidationModel> getHistoryValidationModels() {
        return historyValidationModels;
    }
    public void setHistoryValidationModels(List<HistoryValidationModel> historyValidationModels) {
        this.historyValidationModels = historyValidationModels;
    }


    @Override
    public int getCount() {
        return historyValidationModels.size();
    }

    @Override
    public HistoryValidationModel getItem(int position) {
        return historyValidationModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historyValidationModels.get(position).getOrderNO();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_validation_list_item, null);
        }
        if (historyValidationModels!=null)
        {
            TextView history_order_no = (TextView)convertView.findViewById(R.id.history_order_no);
            int order_no = historyValidationModels.get(position).getOrderNO();
            history_order_no.setText(order_no);
            TextView history_order_date = (TextView) convertView.findViewById(R.id.history_order_no);
            history_order_date.setText(historyValidationModels.get(position).getCREATE_TIME());
            TextView car_clear_service = (TextView)convertView.findViewById(R.id.car_clear_service);
            car_clear_service.setText(historyValidationModels.get(position).getGOODS_NUM());
            TextView sum_price = (TextView)convertView.findViewById(R.id.sum_price);
            sum_price.setText(Double.toString(getItem(position).getORDER_PRICE()));
            TextView operation_person = (TextView) convertView.findViewById(R.id.operation_person);
            operation_person.setText(getItem(position).getOPERATOR_NAME());
            TextView service_name = (TextView)convertView.findViewById(R.id.service_name);
            service_name.setText(getItem(position).getGoodsName());
        }


        return convertView;
    }
}
