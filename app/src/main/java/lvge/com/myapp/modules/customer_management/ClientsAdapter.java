package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;
import lvge.com.myapp.util.L;
import okhttp3.Call;

/**
 * Created by JGG on 2017/7/12.
 */

public class ClientsAdapter extends BaseAdapter {
    private Context mContext;

    private ListView listview;
    private LruCache<String, BitmapDrawable> mImageCache;

    public ClientsAdapter(Context context) {
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

        if (listview == null) {
            listview = (ListView) parent;
        }

        final ImageView headimge = (ImageView) convertView.findViewById(R.id.sale_consultant_iamage);
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

            if (!clients.getPageResult().getEntityList().get(position).getHeadImg().equals("")) {

                final int int_position = position;
                if (mImageCache.get(clients.getPageResult().getEntityList().get(int_position).getHeadImg()) != null) {
                    headimge.setImageDrawable(mImageCache.get(clients.getPageResult().getEntityList().get(int_position).getHeadImg()));
                } else {
                    headimge.setTag(clients.getPageResult().getEntityList().get(int_position).getHeadImg());
//                    headimge.setImageDrawable(null);
                    ImageTask it = new ImageTask(int_position);
                    it.execute(clients.getPageResult().getEntityList().get(int_position).getHeadImg());
                    //                   L.d(clients.getPageResult().getEntityList().get(int_position).getHeadImg());
                }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        OkHttpUtils.get()
//                                .url(clients.getPageResult().getEntityList().get(int_position).getHeadImg())
//                                .build()
//                                .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
//                                .execute(new BitmapCallback() {
//                                    @Override
//                                    public void onError(Call call, Exception e, int i) {
//
//                                    }
//
//                                    @Override
//                                    public void onResponse(Bitmap bitmap, int i) {
//                                        headimge.setImageBitmap(bitmap);
//                                    }
//                                });
//                    }
//                }).start();
            }else{
                headimge.setImageResource(R.mipmap.menu_logo);
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

//        headimge.setTag(clients.getPageResult().getEntityList().get(position).getHeadImg());
//        if (!clients.getPageResult().getEntityList().get(position).getHeadImg().equals(""))
//            L.d(String.valueOf(position)+":"+clients.getPageResult().getEntityList().get(position).getHeadImg());
//        else
//            L.d(String.valueOf(position));
        return convertView;
    }

    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {

        private String imageUrl;
        private int position;
        private ImageView img;

        public ImageTask(int position){
            this.position = position;
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(listview.getResources(), bitmap);
            // 如果本地还没缓存该图片，就缓存
            if (mImageCache.get(imageUrl) == null) {
                mImageCache.put(imageUrl, db);
            }
            return db;
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) listview.findViewWithTag(imageUrl);

            L.d(String.valueOf(position));
            if (iv != null && result != null) {
                iv.setImageDrawable(result);
            }
        }

        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }

            return bitmap;
        }

    }
}
