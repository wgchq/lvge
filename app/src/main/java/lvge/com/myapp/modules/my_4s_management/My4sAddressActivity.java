package lvge.com.myapp.modules.my_4s_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.amap.api.maps2d.model.Text;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.AddressModel;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Response;

public class My4sAddressActivity extends AppCompatActivity implements LocationSource, TextWatcher, AMapLocationListener {

    private Double lat;
    private Double lng;
    private String address;
    private String id;
    private String serverPhone;
    private String assistPhone;
    private String notifyDangerPhone;


    private TextView getCurentPoistion;
    private TextView currentPosition;
    private Marker attentionMark = null;

    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    private ProgressDialog progDialog = null;
    private PoiSearch.Query query;
    private String city = "";
    private int currentPage;
    private PoiSearch poiSearch;

    private ListView search_list_view;
    private My4sAddressSearchAdapter adapter;
    private ArrayList<AddressModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_4s_address_management);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(My4sAddressActivity.this, My4sManagementActivity.class);
               // startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        TextView confirm = (TextView) findViewById(R.id.my_4s_address_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCurentPoistion = (TextView) findViewById(R.id.my4s_address_textview);
        getCurentPoistion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
                currentPosition = (TextView) findViewById(R.id.current_position);
                currentPosition.setText(address);
            }
        });

        lng = Double.parseDouble(intent.getStringExtra("lng"));
        lat = Double.parseDouble(intent.getStringExtra("lat"));
        address = intent.getStringExtra("address");
        id = intent.getStringExtra("id");
        serverPhone = intent.getStringExtra("serverPhone");
        assistPhone = intent.getStringExtra("assistPhone");
        notifyDangerPhone = intent.getStringExtra("notifyDangerPhone");

        final EditText current_position = (EditText) findViewById(R.id.current_position);
        current_position.addTextChangedListener(this);
        TextView my_4s_address_confirm = (TextView) findViewById(R.id.my_4s_address_confirm);
        my_4s_address_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.get()//get 方法
                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/update") //地址
                        .addParams("id", id) //需要传递的参数
                        .addParams("serverPhone", serverPhone) //需要传递的参数
                        .addParams("assistPhone", assistPhone) //需要传递的参数
                        .addParams("notifyDangerPhone", notifyDangerPhone) //需要传递的参数
                        .addParams("lat", Double.toString(lat)) //需要传递的参数
                        .addParams("lng", Double.toString(lng)) //需要传递的参数
                        .addParams("address", address) //需要传递的参数
                        .build()
                        .execute(new Callback() {//通用的callBack
                            //从后台获取成功后，对相应进行类型转化
                            @Override
                            public Object parseNetworkResponse(Response response, int i) throws Exception {

                                String string = response.body().string();//获取相应中的内容Json格式
                                //把json转化成对应对象
                                //LoginResultModel是和后台返回值类型结构一样的对象
                                LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                return result;
                            }

                            @Override
                            public void onError(okhttp3.Call call, Exception e, int i) {

                            }

                            @Override
                            public void onResponse(Object object, int i) {

                                //object 是 parseNetworkResponse的返回值
                                if (null != object) {
                                    LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
                                    if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                        Intent intent = new Intent(My4sAddressActivity.this, My4sManagementActivity.class);
                                        Toast.makeText(My4sAddressActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);

                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(My4sAddressActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
/*
        final EditText current_position = (EditText) findViewById(R.id.current_position);
        current_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sAddressActivity.this, My4sAddressSearchActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });*/

        ImageView my4s_address_search_ImageView = (ImageView) findViewById(R.id.my4s_address_search_ImageView);
        my4s_address_search_ImageView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                doSearchQuery(current_position.getText().toString().trim());
            }
        });

        /**
         * 地图定位
         */
        //显示地图
        mapView = (MapView)

                findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        //settings.setMyLocationButtonEnabled(true);
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

    }

    private void initLoc() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
            return;
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(this);

        //设置定位回调监听
/*
        mLocationClient.setLocationListener(this);
*/
        if (address.equals("")) {
            mLocationClient.setLocationListener(this);
        } else {
            //添加图钉
            attentionMark = aMap.addMarker(getMarkerOptions(address));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

            //将地图移动到定位点
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(this.lat, this.lng)));
            currentPosition = (TextView) findViewById(R.id.current_position);
            currentPosition.setText(address);
        }
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
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

    private MarkerOptions getMarkerOptions(String str) {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        //  options.icon(options.getIcon());
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.shop_manage_shop_address));
        options.title(str);
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
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度

                GeocodeSearch geocodeSearch = new GeocodeSearch(this);
                geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        String Province = regeocodeResult.getRegeocodeAddress().getProvince();
                        String City = regeocodeResult.getRegeocodeAddress().getCity();

                        String Crossroads = regeocodeResult.getRegeocodeAddress().getTowncode();
                        String Building = regeocodeResult.getRegeocodeAddress().getBuilding();
                        String FormatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                        FormatAddress = FormatAddress.replace("。", "");
                        address = FormatAddress;

                        currentPosition = (TextView) findViewById(R.id.current_position);
                        currentPosition.setText(address);

                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });

                LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);

                geocodeSearch.getFromLocationAsyn(query);


                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));

                    //获取定位信息
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


    //激活定位
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        initLoc();
    }

    //停止定位
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


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void doSearchQuery(String str) {

        try {
            search_list_view = (ListView) findViewById(R.id.search_list_view);
            search_list_view.setVisibility(View.VISIBLE);
            adapter = new My4sAddressSearchAdapter(this);
            data = new ArrayList<AddressModel>();
            adapter.setData(data);
            search_list_view.setAdapter(adapter);
            InputTask inputTask = new InputTask(this, adapter);
            inputTask.onSearch(str, city);
            search_list_view.bringToFront();

            search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    search_list_view.setVisibility(View.INVISIBLE);
                    //  EditText current_position = (EditText) findViewById(R.id.current_position);
                    lat = adapter.getData().get(position).getLatitude();
                    lng = adapter.getData().get(position).getLongitude();
                    address = adapter.getData().get(position).getText();
                    if (attentionMark != null) {
                        attentionMark.remove();
                    }

                    attentionMark = aMap.addMarker(getMarkerOptions(address));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();
                    }
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
                    currentPosition = (TextView) findViewById(R.id.current_position);
                    currentPosition.setText(address);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showProgressDialog() {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索：。。。");
        progDialog.show();
    }

    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();

        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
