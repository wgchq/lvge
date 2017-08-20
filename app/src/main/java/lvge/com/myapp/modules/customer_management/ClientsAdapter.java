package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
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
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.car_logo_default);
           /* Float width = convertView.getResources().getDimension(R.dimen.x34);
            Integer int_width = Integer.parseInt( width.toString());*/
            drawable.setBounds(0,0,34,34);
            car_no.setCompoundDrawables(drawable,null,null,null);


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
            TextView length = (TextView) convertView.findViewById(R.id.customer_listview_length);
            String str_length = clients.getPageResult().getEntityList().get(position).getMileAge()+"";
            length.setText(str_length);
            Drawable leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer);
            TextView type = (TextView) convertView.findViewById(R.id.customer_listview_type);
            if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("1")) {
                type.setText("已绑定硬件");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer);
            } else if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("0")){
                type.setText("未绑定硬件");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer_not_on_line);
            }
            else if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("2")){
                type.setText("离线/欠费");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer_not_on_line);
            }
            leftDrawable.setBounds(0,0,leftDrawable.getIntrinsicWidth(),leftDrawable.getIntrinsicHeight());
            length.setCompoundDrawables(leftDrawable,null,null,null);

        }

        return convertView;
    }
}
