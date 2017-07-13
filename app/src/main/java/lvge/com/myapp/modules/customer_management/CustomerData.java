package lvge.com.myapp.modules.customer_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.CustomerDetail;
import okhttp3.Response;

public class CustomerData extends AppCompatActivity implements LocationSource {
    private Marker attentionMark = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器

    private CameraUpdate cameraUpdate;
    private double lng;
    private double lat;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_client_information);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        /**
         * 地图定位
         */
        //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.firetwo));
        myLocationStyle.radiusFillColor(android.R.color.holo_orange_dark);
        myLocationStyle.strokeColor(android.R.color.holo_orange_dark);
        aMap.setMyLocationStyle(myLocationStyle);
        //开始定位
        //设置定位监听


        OkHttpUtils.get()//get 方法
                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/detail") //地址
                .addParams("id", id) //需要传递的参数
                .build()
                .execute(new Callback() {//通用的callBack

                    //从后台获取成功后，对相应进行类型转化
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {

                        String string = response.body().string();//获取相应中的内容Json格式
                        //把json转化成对应对象
                        //LoginResultModel是和后台返回值类型结构一样的对象
                        CustomerDetail customerDetail = new Gson().fromJson(string, CustomerDetail.class);
                        return customerDetail;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(Object object, int i) {

                        //object 是 parseNetworkResponse的返回值
                        if (null != object) {
                            CustomerDetail customerDetail = (CustomerDetail) object;//把通用的Object转化成指定的对象

                            //车牌号
                            TextView client_car_plate_number = (TextView) findViewById(R.id.client_car_plate_number);
                            String str_client_car_plate_number = customerDetail.getMarketEntity().getPlatenumber();
                            client_car_plate_number.setText(str_client_car_plate_number);

                            //车主姓名
                            TextView txt_customer_data_car_holder = (TextView) findViewById(R.id.txt_customer_data_car_holder);
                            txt_customer_data_car_holder.setText(customerDetail.getMarketEntity().getName());

                            //车主电话
                            TextView txt_customer_data_car_holder_phone = (TextView) findViewById(R.id.txt_customer_data_car_holder_phone);
                            txt_customer_data_car_holder_phone.setText(customerDetail.getMarketEntity().getPhone());

                            //车型
                            TextView txt_customer_data_car_type = (TextView) findViewById(R.id.txt_customer_data_car_type);
                            txt_customer_data_car_type.setText(customerDetail.getMarketEntity().getbName());

                            //车架号
                            TextView txt_customer_data_car_vin_no = (TextView) findViewById(R.id.txt_customer_data_car_vin_no);
                            txt_customer_data_car_vin_no.setText(customerDetail.getMarketEntity().getVin());

                            TextView txt_customer_data_car_imei_no = (TextView) findViewById(R.id.txt_customer_data_car_imei_no);
                            txt_customer_data_car_imei_no.setText(customerDetail.getMarketEntity().getImei());
                            lng = customerDetail.getMarketEntity().getLng();
                            lat = customerDetail.getMarketEntity().getLat();

                            initLoc();

                        }
                    }

                });
    }

    //定位
    private void initLoc() {
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        //将地图移动到定位点
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));

        //添加图钉
        aMap.addMarker(getMarkerOptions());
    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions() {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(options.getIcon());
        // options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin));

        options.position(new LatLng(lat, lng));
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
