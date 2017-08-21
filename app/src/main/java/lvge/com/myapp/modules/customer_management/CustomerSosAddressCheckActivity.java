package lvge.com.myapp.modules.customer_management;

import android.graphics.Color;

import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientDetailSosModel;

import okhttp3.Response;

public class CustomerSosAddressCheckActivity extends AppCompatActivity implements AMapLocationListener {

    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;


    private String custumerId;

    private TextView client_kilometer_map;
    private TextView txt_client_manage_battery;
    private ImageView img_client_manage_battery;
    private TextView txt_client_manage_gps;
    private ImageView img_client_manage_gps;
    private ImageView img_client_manage_acc_power;
    private ImageView img_client_manage_acc;
    private TextView user_address;
    private ImageView img_user_address;
    private ImageView img_sos_address;
    private ImageView img_show_rescue;
    private ImageView img_client_manage_rescue;
    private ImageView client_manage_location;
    private ImageView back_client_page;


    private double lat;//client
    private double lng;//client

    private double userlat;//user
    private double userlng;//user
    private String userAddress;

    private boolean show = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sos_address_check);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        custumerId = getIntent().getStringExtra("id");
        if (null == custumerId) {
            custumerId = "";
        }

        client_kilometer_map = (TextView) findViewById(R.id.client_kilometer_map);
        txt_client_manage_battery = (TextView) findViewById(R.id.txt_client_manage_battery);
        img_client_manage_battery = (ImageView) findViewById(R.id.img_client_manage_battery);

        img_client_manage_acc_power = (ImageView) findViewById(R.id.img_client_manage_acc_power);
        txt_client_manage_gps = (TextView) findViewById(R.id.txt_client_manage_gps);
        img_client_manage_gps = (ImageView) findViewById(R.id.img_client_manage_gps);
        img_client_manage_acc = (ImageView) findViewById(R.id.img_client_manage_acc);
        user_address = (TextView) findViewById(R.id.client_detail_user_address);
        img_user_address = (ImageView) findViewById(R.id.img_client_manage_person);
        img_user_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(userlat, userlng)));
                //获取定位信息
            }
        });

        img_sos_address = (ImageView) findViewById(R.id.client_manage_car_big);
        img_sos_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
                //获取定位信息
            }
        });
        img_client_manage_rescue = (ImageView) findViewById(R.id.img_client_manage_rescue);

        img_show_rescue = (ImageView) findViewById(R.id.img_client_manage_person_and_car);
        img_show_rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_client_manage_rescue.setVisibility(View.VISIBLE);
            }

        });

        client_manage_location = (ImageView) findViewById(R.id.img_client_manage_location);
        client_manage_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!show) {
                    img_show_rescue.setVisibility(View.VISIBLE);
                    img_sos_address.setVisibility(View.VISIBLE);
                    img_user_address.setVisibility(View.VISIBLE);
                    show = true;
                    client_manage_location.setImageResource(R.mipmap.client_manage_location_on);
                } else {
                    img_show_rescue.setVisibility(View.INVISIBLE);
                    img_sos_address.setVisibility(View.INVISIBLE);
                    img_user_address.setVisibility(View.INVISIBLE);
                    show = false;
                    client_manage_location.setImageResource(R.mipmap.client_manage_location_off);
                }

            }
        });

        back_client_page = (ImageView) findViewById(R.id.back_client_page);
        back_client_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //显示地图
        mapView = (MapView)

                findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                mListener = onLocationChangedListener;
                initLoc();
            }

            @Override
            public void deactivate() {
                mListener = null;
                mListener = null;
                if (mLocationClient != null) {
                    mLocationClient.stopLocation();
                    mLocationClient.onDestroy();
                }
                mLocationClient = null;
            }
        });
        // 是否显示定位按钮
        //settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.client_manage_user_location));
        myLocationStyle.radiusFillColor(android.R.color.holo_orange_dark);
        myLocationStyle.strokeColor(android.R.color.holo_orange_dark);
        aMap.setMyLocationStyle(myLocationStyle);

        initLoc();

        GetClientInfor(custumerId);

    }

    private void GetClientInfor(String customerID) {
        OkHttpUtils.get()//get 方法
                .url("http://www.lvgew.com/obdcarmarket/sellerapp/customer/terminalStatus") //地址
                .addParams("customerID", customerID) //需要传递的参数
                .build()
                .execute(new Callback() {//通用的callBack

                    //从后台获取成功后，对相应进行类型转化
                    @Override
                    public Object parseNetworkResponse(Response response, int i) throws Exception {

                        String string = response.body().string();//获取相应中的内容Json格式
                        //把json转化成对应对象
                        //LoginResultModel是和后台返回值类型结构一样的对象
                        ClientDetailSosModel result = new Gson().fromJson(string, ClientDetailSosModel.class);
                        return result;
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(Object object, int i) {

                        //object 是 parseNetworkResponse的返回值
                        if (null != object) {
                            ClientDetailSosModel result = (ClientDetailSosModel) object;//把通用的Object转化成指定的对象
                            if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                String speed = result.getMarketEntity().getSpeed();
                                client_kilometer_map.setText(speed);
                                String voltage = result.getMarketEntity().getVoltage();
                                txt_client_manage_battery.setText(voltage);
                                if (Integer.parseInt(voltage) > 0) {
                                    img_client_manage_battery.setImageResource(R.mipmap.client_manage_battery_on);
                                } else {
                                    img_client_manage_battery.setImageResource(R.mipmap.client_manage_battery_off);
                                }

                                String engine = result.getMarketEntity().getEngine();
                                if (engine.equals(1)) {
                                    img_client_manage_acc_power.setImageResource(R.mipmap.client_manage_acc_power_on);
                                } else if (engine.equals(2)) {
                                    img_client_manage_acc_power.setImageResource(R.mipmap.client_manage_acc_power_off);
                                } else {
                                    img_client_manage_acc_power.setImageResource(R.mipmap.client_manage_acc_power_off);
                                }

                                String gps = result.getMarketEntity().getGps();
                                txt_client_manage_gps.setText(gps);
                                if (Integer.parseInt(gps) > 0) {
                                    img_client_manage_gps.setImageResource(R.mipmap.client_manage_gps_on);
                                } else {
                                    img_client_manage_gps.setImageResource(R.mipmap.client_manage_gps_off);
                                }

                                String acc = result.getMarketEntity().getAcc();
                                if (acc.equals("1")) {
                                    img_client_manage_acc.setImageResource(R.mipmap.client_manage_acc_on);
                                } else if (acc.equals("2")) {
                                    img_client_manage_acc.setImageResource(R.mipmap.client_manage_acc_off);
                                } else {
                                    img_client_manage_acc.setImageResource(R.mipmap.client_manage_acc_off);
                                }

                                lat = Double.parseDouble(result.getMarketEntity().getLat());
                                lng = Double.parseDouble(result.getMarketEntity().getLng());
                                addMarker();

                            } else {
                                Toast.makeText(CustomerSosAddressCheckActivity.this, "没有数据传输", Toast.LENGTH_SHORT).show();
                            }
                        } else {//当没有返回对象时，表示网络没有联通
                            Toast.makeText(CustomerSosAddressCheckActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void initLoc() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
            return;
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听

        mLocationClient.setLocationListener(this);


        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    /**
     * 往地图上添加marker
     */
    private void addMarker() {
        MarkerOptions markerOptions = getMarkerOptions("");
        aMap.addMarker(markerOptions);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = new LatLng(lat, lng);
                return false;
            }
        });
    }

    private MarkerOptions getMarkerOptions(String str) {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        //  options.icon(options.getIcon());
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.client_manage_car_location));
        options.position(new LatLng(lat, lng));
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }


    //定位回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                userlat = amapLocation.getLatitude();//获取纬度
                userlng = amapLocation.getLongitude();//获取经度
                String userAddress = amapLocation.getAddress();
                user_address.setText(userAddress);

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {

                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //获取定位信息
                    isFirstLoc = false;
                }

                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(amapLocation);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("地图错误", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

}
