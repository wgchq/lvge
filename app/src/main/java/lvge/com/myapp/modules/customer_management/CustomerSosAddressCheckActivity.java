package lvge.com.myapp.modules.customer_management;

import android.app.Dialog;
import android.graphics.Color;

import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.text.DecimalFormat;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientDetailSosModel;

import lvge.com.myapp.util.MapUtils.CustomInfoWindowAdapter;
import lvge.com.myapp.util.MapUtils.CustomMarker;
import lvge.com.myapp.util.MapUtils.MapOpenUtil;
import lvge.com.myapp.util.MapUtils.PackageManagerUtil;
import okhttp3.Response;

import static java.lang.Math.floor;
import static java.lang.Math.round;


public class CustomerSosAddressCheckActivity extends AppCompatActivity implements AMapLocationListener, View.OnClickListener {

    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private Marker LocationMarker;

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
    private ImageView img_client_manage_switch_map_view;
    private LinearLayout customer_distinct_view;


    private View inflate;
    private TextView gaoMap;
    private TextView baiduMap;
    private TextView webMap;
    private TextView cancelMap;
    private Dialog dialog;
    private String path;
    private int id_iamge;

    private double lat;//client
    private double lng;//client
    private String address;

    private double userlat;//user
    private double userlng;//user
    private String userAddress;

    private boolean show = false;
    private boolean showSatellite = false;


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
                customer_distinct_view.setVisibility(View.VISIBLE);
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
        img_client_manage_switch_map_view = (ImageView) findViewById(R.id.img_client_manage_switch_map_view);

        img_client_manage_switch_map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showSatellite) {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    showSatellite = false;
                } else {
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    showSatellite = true;
                }

            }
        });

        customer_distinct_view = (LinearLayout)findViewById(R.id.customer_distinct_view);
        //显示地图
        mapView = (MapView)

                findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();
        aMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(CustomerSosAddressCheckActivity.this));

        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                img_client_manage_rescue.setVisibility(View.GONE);
                customer_distinct_view.setVisibility(View.GONE);

                if (null != LocationMarker) {
                    LocationMarker.hideInfoWindow();
                }

            }
        });
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
        CustomMarker customMarker = new CustomMarker(CustomerSosAddressCheckActivity.this);
        View myView = customMarker.getMyView("");
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromView(myView));
/*
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.client_manage_user_location));
*/
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
                                String str_speed = speed + "km";
                                client_kilometer_map.setText(str_speed);
                                String voltage = result.getMarketEntity().getVoltage();
                                String str_voltage = voltage + "V";
                                txt_client_manage_battery.setText(str_voltage);
                                if (Double.parseDouble(voltage) > 0) {
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

                                String title = "公里数：" + result.getMarketEntity().getMeliage() + "公里";
                                addMarker(title);
                                GeocodeSearch geocodeSearch = new GeocodeSearch(CustomerSosAddressCheckActivity.this);
                                geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                                    @Override
                                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                                        if (null != regeocodeResult) {
                                            String Province = regeocodeResult.getRegeocodeAddress().getProvince();
                                            String City = regeocodeResult.getRegeocodeAddress().getCity();
                                            String Crossroads = regeocodeResult.getRegeocodeAddress().getTowncode();
                                            String Building = regeocodeResult.getRegeocodeAddress().getBuilding();
                                            String FormatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                                            address = FormatAddress;
                                            TextView client_detail_client_address = (TextView) findViewById(R.id.client_detail_client_address);
                                            client_detail_client_address.setText(address);

                                            LatLng latLng = new LatLng(lat, lng);
                                            LatLng UserLatLng = new LatLng(userlat, userlng);
                                            float distinct = AMapUtils.calculateLineDistance(latLng, UserLatLng);
                                            TextView client_and_user_distinct = (TextView) findViewById(R.id.client_and_user_distinct);
                                            DecimalFormat df = new DecimalFormat("#.0");
                                            Double double_distinct =Double.parseDouble(df.format(distinct)) ;

                                            String str_distinct = double_distinct + "米";
                                            client_and_user_distinct.setText(str_distinct);

                                        }
                                    }

                                    @Override
                                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                                    }
                                });

                                LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
                                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);

                                geocodeSearch.getFromLocationAsyn(query);


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
    private void addMarker(String title) {
        aMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(CustomerSosAddressCheckActivity.this));
        MarkerOptions markerOptions = getMarkerOptions(title);
        LocationMarker = aMap.addMarker(markerOptions);
        LocationMarker.setTitle(title);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

    }

    private MarkerOptions getMarkerOptions(String str) {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.title(str);
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
                userAddress = amapLocation.getAddress();
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


    public void show(View view) {
        id_iamge = view.getId();
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_client_address_go_map_dialog, null);
        //初始化控件
        gaoMap = (TextView) inflate.findViewById(R.id.gao_map);
        baiduMap = (TextView) inflate.findViewById(R.id.baidu_map);
        webMap = (TextView) inflate.findViewById(R.id.web_map);
        cancelMap = (TextView) inflate.findViewById(R.id.cancel);
        gaoMap.setOnClickListener(this);
        baiduMap.setOnClickListener(this);
        webMap.setOnClickListener(this);
        cancelMap.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @Override
    public void onClick(View v) {
        MapOpenUtil mapOpenUtil = new MapOpenUtil(CustomerSosAddressCheckActivity.this);
        mapOpenUtil.setSLAT(userlat);
        mapOpenUtil.setSLNG(userlng);
        mapOpenUtil.setDLAT(lat);
        mapOpenUtil.setDLNG(lng);
        mapOpenUtil.setDName(address);
        mapOpenUtil.setSName(userAddress);
        PackageManagerUtil packageManagerUtil = new PackageManagerUtil(CustomerSosAddressCheckActivity.this);
        switch (v.getId()) {
            case R.id.gao_map:
                if (PackageManagerUtil.haveGaodeMap()) {
                    mapOpenUtil.openGaodeMapToGuide();
                } else {
                    Toast.makeText(CustomerSosAddressCheckActivity.this, "未检测到高德地图", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.baidu_map:
                if (PackageManagerUtil.haveBaiduMap()) {
                    mapOpenUtil.openBaiduMapToGuide();
                } else {
                    Toast.makeText(CustomerSosAddressCheckActivity.this, "未检测到百度地图", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.web_map:
                mapOpenUtil.openBrowserToGuide();
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
        }
        dialog.dismiss();
    }
}
