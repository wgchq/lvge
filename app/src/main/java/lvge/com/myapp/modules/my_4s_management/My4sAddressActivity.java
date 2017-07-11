package lvge.com.myapp.modules.my_4s_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lvge.com.myapp.R;

public class My4sAddressActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_address);

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

        initLoc();

    }


    //定位
    private void initLoc() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
            return;
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
    /*    //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);*/

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    //定位回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间*/
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                amapLocation.getFloor();//获取当前室内定位的楼层
                amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态


                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));


                    //添加图钉
                    //    aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息

                    isFirstLoc = false;
                    Toast.makeText(My4sAddressActivity.this, "定位失败!", Toast.LENGTH_SHORT).show();

                }

                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(amapLocation);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("地图错误", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                if (isFirstLoc) {
                    isFirstLoc = false;
                    Toast.makeText(My4sAddressActivity.this, "未开启定位功能!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions() {
     //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
         options.icon(options.getIcon());
       // options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin));

     //   options.position(new LatLng(attentionModel.Latitude, attentionModel.Longitude));
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener ) {
        mListener = onLocationChangedListener;
        initLoc();
    }

    @Override
    public void deactivate() {
        mListener = null;
        mListener = null;
        if (this.mLocationClient != null) {
            this.mLocationClient.stopLocation();
            this.mLocationClient.onDestroy();
        }
        this.mLocationClient = null;
    }
}
