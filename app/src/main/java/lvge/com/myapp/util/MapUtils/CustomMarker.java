package lvge.com.myapp.util.MapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import lvge.com.myapp.R;

/**
 * Created by JGG on 2017-08-23.
 */

public class CustomMarker {
    private Context context;

    public CustomMarker(Context context) {
        this.context = context;
    }

    public View getMyView(String photoUrl) {
        View view = LayoutInflater.from(context).inflate(R.layout.map_marker, null);
        ImageView map_marker_user_photo = (ImageView) view.findViewById(R.id.map_marker_user_photo);
        map_marker_user_photo.setImageResource(R.mipmap.menu_logo);
        return view;
    }

}
