package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import lvge.com.myapp.R;

/**
 * Created by mac on 2017/7/4.
 */

public class SalesConsutantListViewAdapter extends ArrayAdapter<String> {

    public SalesConsutantListViewAdapter(Context context, int textViewResourceId, List<String> objects){
        super(context,textViewResourceId,objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.sales_consultant_listview,null);
        }else {
            view = convertView;
        }

        return  view;
    }
}
