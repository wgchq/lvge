package lvge.com.myapp.modules.my_4s_management;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import java.util.ArrayList;


import lvge.com.myapp.R;
import lvge.com.myapp.model.AddressModel;

public class My4sAddressActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    private Boolean isOnItemClick = false;
    private Marker attentionMark = null;
    private AddressModel comfirmaAddress = new AddressModel(0, 0, "", "");
    TextView current_address = null;
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

    private EditText edit;
    private ListView lv;
    private My4sAddressSearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_address);

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        lng = Double.parseDouble(bundle.getString("lng"));
        lat = Double.parseDouble(bundle.getString("lat"));
        address = bundle.getString("address");
        this.edit = (EditText) findViewById(R.id.search_edit);
         current_address = (TextView)findViewById(R.id.current_address);

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
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.shop_manage_shop_address));
        myLocationStyle.radiusFillColor(android.R.color.holo_orange_dark);
        myLocationStyle.strokeColor(android.R.color.holo_orange_dark);
        aMap.setMyLocationStyle(myLocationStyle);
        //开始定位
        //设置定位监听
        initLoc();
        // initView();

    }

    public void drawMarker() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(address)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true)

        );
        marker.showInfoWindow();
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
        mLocationOption.setNeedAddress(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void initView() {
        try {
            edit = (EditText) findViewById(R.id.search_edit);
            lv = (ListView) findViewById(R.id.search_list);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AddressModel temp = (AddressModel) parent.getAdapter().getItem(position);

                    comfirmaAddress.setTitle(temp.getTitle());
                    comfirmaAddress.setText(temp.getText());
                    comfirmaAddress.setLatitude(temp.getLatitude());
                    comfirmaAddress.setLongitude(temp.getLongitude());

                    edit.setText(comfirmaAddress.getTitle());
                    mAdapter.clearData();
                    mAdapter.notifyDataSetChanged();
                    isOnItemClick = true;

                    searchLocation(comfirmaAddress.getLatitude(), comfirmaAddress.getLongitude());
                    cameraUpdate = CameraUpdateFactory.newCameraPosition(
                            new CameraPosition(new LatLng(lat, lng), 15, 0, 30)
                    );
                    aMap.moveCamera(cameraUpdate);
                    initView();
                }
            });

            mAdapter = new My4sAddressSearchAdapter(this);
            ArrayList<AddressModel> addressList = new ArrayList<AddressModel>();
            mAdapter.setData(addressList);
            lv.setAdapter(mAdapter);

            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!isOnItemClick) {
                        InputTask.getInstance(My4sAddressActivity.this, mAdapter).onSearch(s.toString(), "");
                    }
                    isOnItemClick = false;

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int a = 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void searchLocation(Double lat, Double longi) {
        this.lat = lat;
        this.lng = longi;
        drawMarker();
    }

    //定位回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置

                this.address = amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum();
                this.lng = amapLocation.getLongitude();
                this.lat = amapLocation.getLatitude();

             //   this.current_address.setText("sss");

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

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(Double lat, Double longi) {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(options.getIcon());
        // options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin));

        options.position(new LatLng(lat, longi));
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
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
