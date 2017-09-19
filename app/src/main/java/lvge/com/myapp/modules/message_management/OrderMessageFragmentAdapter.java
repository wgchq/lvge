package lvge.com.myapp.modules.message_management;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.EntityList;

/**
 * Created by mac on 2017/9/19.
 */

public class OrderMessageFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private SwipeMenuListView swipeMenuListView;
    private LruCache<String, BitmapDrawable> mImageCache;

    public OrderMessageFragmentAdapter(Context context) {
        this.mContext = context;

        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }

    private List<EntityList> clients;

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
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
        if(clients != null){
            if(clients.get(position).getNOTICE_TYPE() == 1){
                TextView customer_phone = (TextView)convertView.findViewById(R.id.customer_phone);
                if(clients.get(position).getIS_READ() == 0){
                    customer_phone.setTextColor(Color.BLACK);
                }else {
                    customer_phone.setTextColor(Color.LTGRAY);
                }
                customer_phone.setText(clients.get(position).getNOTICE_TITLE());
                TextView client_kilometer = (TextView)convertView.findViewById(R.id.client_kilometer);
                client_kilometer.setText(clients.get(position).getNOTICE_TITLE());
                TextView customer_car_no = (TextView)convertView.findViewById(R.id.customer_car_no);
                customer_car_no.setText(clients.get(position).getNOTICE_MESSAGE());
            }

        }
        return convertView;
    }

    public List<EntityList> getClients() {
        return clients;
    }

    public void setClients(List<EntityList> clients) {
        this.clients = clients;
    }
}
