package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;
import okhttp3.Call;

/**
 * Created by JGG on 2017/7/12.
 */

public class ClientsAdapter extends BaseAdapter {
    private Context mContext;

    public ClientsAdapter(Context context) {
        this.mContext = context;
    }

    public ClientResultModel getClients() {
        return clients;
    }

    public void setClients(ClientResultModel clients) {
        this.clients = clients;
    }

    private ClientResultModel clients;

    @Override
    public int getCount() {
        return this.clients.getPageResult().getEntityList().size();
    }

    @Override
    public Object getItem(int position) {
        return clients.getPageResult().getEntityList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return clients.getPageResult().getEntityList().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.clients_customers_item, null);
        }
        if (clients != null) {

            TextView phone = (TextView) convertView.findViewById(R.id.customer_phone);
            phone.setText(clients.getPageResult().getEntityList().get(position).getPhone());

            final TextView car_no = (TextView) convertView.findViewById(R.id.customer_car_no);
            String str_car_no = "车架号：" + clients.getPageResult().getEntityList().get(position).getVin();
            car_no.setText(str_car_no);

            final ImageView headimge = (ImageView) convertView.findViewById(R.id.sale_consultant_iamage);

            if (!clients.getPageResult().getEntityList().get(position).getHeadImg().equals("")) {

                final int int_position = position;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpUtils.get()
                                .url(clients.getPageResult().getEntityList().get(int_position).getHeadImg())
                                .build()
                                .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                                .execute(new BitmapCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {

                                    }

                                    @Override
                                    public void onResponse(Bitmap bitmap, int i) {
                                        headimge.setImageBitmap(bitmap);
                                    }
                                });
                    }
                }).start();


            }

            TextView type = (TextView) convertView.findViewById(R.id.customer_listview_type);
            if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("1")) {
                type.setText("已绑定硬件");
            } else {
                type.setText("未绑定硬件");
            }

            TextView length = (TextView) convertView.findViewById(R.id.customer_listview_length);
            String str_length = clients.getPageResult().getEntityList().get(position).getMileAge() + "公里";
            length.setText(str_length);
        }

        return convertView;
    }
}
