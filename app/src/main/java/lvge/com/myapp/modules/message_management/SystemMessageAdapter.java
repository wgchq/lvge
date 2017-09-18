package lvge.com.myapp.modules.message_management;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import lvge.com.myapp.R;

/**
 * Created by mac on 2017/9/17.
 */

public class SystemMessageAdapter extends BaseAdapter {

    private Context mContext;
    private SwipeMenuListView swipeMenuListView;
    public SystemMessageAdapter(Context context) {
        this.mContext = context;

       /* int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };*/
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.system_message_item, null);
        }
        if(swipeMenuListView == null){
            swipeMenuListView = (SwipeMenuListView)parent;
        }
        return null;
    }
}
