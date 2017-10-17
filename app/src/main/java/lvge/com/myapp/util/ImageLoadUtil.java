package lvge.com.myapp.util;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import lvge.com.myapp.R;

import static android.R.attr.fragment;

/**
 * Created by android on 2017/10/17.
 */

public class ImageLoadUtil {
    public static void loadHeader(Fragment fragment, String url, ImageView view){
        Glide.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .placeholder(R.drawable.default_header)
                .error(R.drawable.default_header)
                .into(view);
    }
    public static void loadHeader(AppCompatActivity activity, String url, ImageView view){
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .placeholder(R.drawable.default_header)
                .error(R.drawable.default_header)
                .into(view);
    }
}
