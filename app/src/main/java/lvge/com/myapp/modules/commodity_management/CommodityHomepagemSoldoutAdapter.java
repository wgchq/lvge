package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.CommodityListEntitylist;
import lvge.com.myapp.util.L;

/**
 * Created by mac on 2017/9/27.
 */

public class CommodityHomepagemSoldoutAdapter extends BaseAdapter {
    private Context mContext;
    private SwipeMenuListView swipeMenuListView;

    private LruCache<String, BitmapDrawable> mImageCache;

    private ViewHolder holder;
    private int expandPosition = -1;  //记录展开项索引

    public CommodityHomepagemSoldoutAdapter(Context context) {
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

    public List<CommodityListEntitylist> getClients() {
        return clients;
    }

    public void setClients(List<CommodityListEntitylist> clients) {
        this.clients = clients;
    }

    private List<CommodityListEntitylist> clients;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.commodity_list_layout, null);
            holder = new CommodityHomepagemSoldoutAdapter.ViewHolder();

            holder.headimge = (ImageView) convertView.findViewById(R.id.commodity_list_ImageView);
            holder.commodity_list_service = (TextView) convertView.findViewById(R.id.commodity_list_service);
            holder.commodity_list_Price = (TextView) convertView.findViewById(R.id.commodity_list_Price);
            holder.commodity_list_Stock = (TextView) convertView.findViewById(R.id.commodity_list_Stock);
            holder.commodity_list_Monthly_sales = (TextView) convertView.findViewById(R.id.commodity_list_Monthly_sales);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.commodity_list_Relayout);
            holder.commodity_list_hide = (ImageView) convertView.findViewById(R.id.commodity_list_hide);
            holder.commodity_list_edit = (TextView) convertView.findViewById(R.id.commodity_list_edit);
            holder.commodity_list_offsale = (TextView) convertView.findViewById(R.id.commodity_list_offsale);
            holder.commodity_list_delete = (TextView) convertView.findViewById(R.id.commodity_list_delete);
            holder.commodity_list_Extension = (TextView) convertView.findViewById(R.id.commodity_list_Extension);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        if (swipeMenuListView == null) {
            swipeMenuListView = (SwipeMenuListView) parent;
        }
        if (clients != null) {

            if (!clients.get(position).getMainPicPath().equals("")) {
                final int int_position = position;
                if (mImageCache.get(clients.get(int_position).getMainPicPath()) != null) {
                    holder.headimge.setImageDrawable(mImageCache.get(clients.get(int_position).getMainPicPath()));
                } else {
                    holder.headimge.setTag(clients.get(int_position).getMainPicPath());
//                    headimge.setImageDrawable(null);
                    ImageTask it = new ImageTask(int_position);
                    it.execute(clients.get(int_position).getMainPicPath());
                    //                   L.d(clients.getPageResult().getEntityList().get(int_position).getHeadImg());
                }
            }
            holder.commodity_list_service.setText(clients.get(position).getName());
            holder.commodity_list_Price.setText("￥：" + String.valueOf(clients.get(position).getPrice()));
            holder.commodity_list_Stock.setText("库存：" + String.valueOf(clients.get(position).getStock()));
            holder.commodity_list_Monthly_sales.setText("总销量：" + String.valueOf(clients.get(position).getSaleCount()));

            holder.commodity_list_hide.setOnClickListener(new onImageViewClickListener(position));
            if (expandPosition == position) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                holder.commodity_list_hide.setImageResource(R.mipmap.commodity_list_xiala_right);
            } else {
                holder.relativeLayout.setVisibility(View.GONE);
                holder.commodity_list_hide.setImageResource(R.mipmap.commodity_list_gre_xiala);
            }
            holder.commodity_list_edit.setOnClickListener(new onTextViewClickListener());
            holder.commodity_list_delete.setOnClickListener(new onTextViewClickListener());
            holder.commodity_list_Extension.setOnClickListener(new onTextViewClickListener());
            holder.commodity_list_offsale.setOnClickListener(new onTextViewClickListener());
        }
        return convertView;
    }

    class onTextViewClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.commodity_list_offsale:
                    break;
                case R.id.commodity_list_edit:
                    break;
                case R.id.commodity_list_delete:
                    break;
                case R.id.commodity_list_Extension:
                    break;
            }
        }
    }
    class onImageViewClickListener implements View.OnClickListener{

        private int position;
        public onImageViewClickListener(int position){
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(expandPosition == position){
                expandPosition = -1;
            }else {
                expandPosition = position;
            }
            notifyDataSetChanged();
        }
    }

    public class ViewHolder{
        public ImageView headimge;
        public TextView commodity_list_service;
        public TextView commodity_list_Price;
        public TextView commodity_list_Stock;
        public TextView commodity_list_Monthly_sales;
        public RelativeLayout relativeLayout;
        public ImageView commodity_list_hide;
        public TextView commodity_list_edit;
        public TextView commodity_list_offsale;
        public TextView commodity_list_delete;
        public TextView commodity_list_Extension;
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
            BitmapDrawable db = new BitmapDrawable(swipeMenuListView.getResources(), bitmap);
            // 如果本地还没缓存该图片，就缓存
            if (mImageCache.get(imageUrl) == null) {
                mImageCache.put(imageUrl, db);
            }
            return db;
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) swipeMenuListView.findViewWithTag(imageUrl);

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
