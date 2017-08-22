package lvge.com.myapp.util.MapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Marker;

import lvge.com.myapp.R;

/**
 * Created by JGG on 2017-08-22.
 */

public class CustomInfoWindowAdapter implements AMap.InfoWindowAdapter {

    private Context context;
    private String Information;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        Information = marker.getTitle();
        View view = LayoutInflater.from(context).inflate(R.layout.map_info_window, null);
        setViewContent(marker, view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    //这个方法根据自己的实体信息来进行相应控件的赋值
    private void setViewContent(Marker marker, View view) {
        TextView tv_window_information = (TextView) view.findViewById(R.id.window_information);
        tv_window_information.setText(this.Information);
    }
}
