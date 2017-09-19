package lvge.com.myapp.modules.shop_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.AddressModel;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.my_4s_management.InputTask;
import lvge.com.myapp.modules.my_4s_management.My4sAddressSearchAdapter;
import okhttp3.Response;

public class ShopAddressActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    private String areaID;
    private String sellerID;
    private String authentic;
    private String address;
    private String name;
    private String openService;
    private double lng;
    private String telephone;
    private double lat;
    private String mobile;
    private String noExpense;
    private LatLng locationLatLng;
    private String id;
    private String serverPhone;
    private String assistPhone;
    private String notifyDangerPhone;
    private Marker locationMarker;
    private TextView getCurentPoistion;
    private EditText currentPosition;
    private TextView my_4s_address_detail;
    private Handler handler = new Handler();
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
    private ListView search_list_view;
    private My4sAddressSearchAdapter adapter;
    private ArrayList<AddressModel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_address);

        /*导航条*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_4s_address_management);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*获取页面传值*/
        Intent intent = getIntent();
        name = intent.getStringExtra("name")!=null?intent.getStringExtra("name"):"";
        lng = Double.parseDouble(intent.getStringExtra("lng"));
        lat = Double.parseDouble(intent.getStringExtra("lat"));
        address = intent.getStringExtra("address");
        sellerID = intent.getStringExtra("id");
        areaID =intent.getStringExtra("areaID")!=null?intent.getStringExtra("areaID"):"";
        mobile = intent.getStringExtra("mobile")!=null? intent.getStringExtra("mobile"):"";
        telephone = intent.getStringExtra("telephone")!=null? intent.getStringExtra("telephone"):"";
        noExpense = intent.getStringExtra("noExpense")!=null?intent.getStringExtra("noExpense"):"";



      /* *//*页面点击事件注册*//*
        //当前位置按钮
        getCurentPoistion = (TextView) findViewById(R.id.my4s_address_textview);
        getCurentPoistion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
                currentPosition = (EditText) findViewById(R.id.current_position);
                currentPosition.setText(address);
                my_4s_address_detail = (TextView) findViewById(R.id.my_4s_address_detail);
                my_4s_address_detail.setText(address);
            }
        });*/

        final EditText current_position = (EditText) findViewById(R.id.current_position);
        current_position.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    String s = current_position.getText().toString();
                    doSearchQuery(s);
                }
                return false;
            }
        });
        // 这一步必须要做,否则不会显示.
        final Drawable drawable = getResources().getDrawable(R.mipmap.searcg);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        current_position.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count != 0) {
                    current_position.setCompoundDrawables(null, null, null, null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    currentPosition.setCompoundDrawables(drawable, null, null, null);
                }
                 /*   doSearchQuery(s.toString().trim());*/
            }
        });

        TextView my_4s_address_confirm = (TextView) findViewById(R.id.my_4s_address_confirm);
        my_4s_address_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()//get 方法
                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/seller/saveOrUpdate") //地址
                        .addParams("name",name)
                        .addParams("sellerID",sellerID)
                        .addParams("address", address)
                        .addParams("areaID", areaID)
                        .addParams("lng", lng+"")
                        .addParams("lat", lat+"")
                        .addParams("mobile", mobile)
                        .addParams("telephone", telephone)
                        .addParams("noExpense",noExpense)
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
                                        Intent intent = new Intent(ShopAddressActivity.this, ShopManagementActivity.class);
                                        Toast.makeText(ShopAddressActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);

                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(ShopAddressActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

        /*ImageView my4s_address_search_ImageView = (ImageView) findViewById(R.id.my4s_address_search_ImageView);
        my4s_address_search_ImageView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                doSearchQuery(current_position.getText().toString().trim());
            }
        });*/


        /*地图定位*/
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
        //settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.shop_manage_shop_address));
        myLocationStyle.radiusFillColor(android.R.color.holo_orange_dark);
        myLocationStyle.strokeColor(android.R.color.holo_orange_dark);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnCameraChangeListener(cameraChangeListener);
        //开始定位
        initLoc();

    }

    /**
     * 往地图上添加marker
     */

    private void addMarker() {
        MarkerOptions markerOptions = getMarkerOptions("");
        locationMarker = aMap.addMarker(markerOptions);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = new LatLng(lat, lng);
                jumpPoint(locationMarker, latLng);
                return false;
            }
        });
    }

    AMap.OnCameraChangeListener cameraChangeListener = new AMap.OnCameraChangeListener() {
        private String Location_position = "";

        void setPosition() {
            try {
                currentPosition = (EditText) findViewById(R.id.current_position);
                currentPosition.setText(Location_position);
                my_4s_address_detail = (TextView) findViewById(R.id.my_4s_address_detail);
                my_4s_address_detail.setText(Location_position);
                address = Location_position;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCameraChangeFinish(CameraPosition position) {
            if (locationMarker != null) {

                final LatLng latLng = position.target;
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        GeocodeSearch geocodeSearch = new GeocodeSearch(ShopAddressActivity.this);
                        LatLonPoint point = new LatLonPoint(latLng.latitude, latLng.longitude);
                        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, 1000, GeocodeSearch.AMAP);
                        RegeocodeAddress address = null;
                        try {
                            address = geocodeSearch.getFromLocation(regeocodeQuery);
                        } catch (AMapException e) {
                            e.printStackTrace();
                        }
                        if (null == address) {
                            return;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        String area = address.getProvince();//省或直辖市
                        String loc = address.getCity();//地级市或直辖市
                        String subLoc = address.getDistrict();//区或县或县级市
                        String ts = address.getTownship();//乡镇
                        String thf = null;//道路
                        List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
                        if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                            RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                            if (regeocodeRoad != null) {
                                thf = regeocodeRoad.getName();
                            }
                        }
                        String subthf = null;//门牌号
                        StreetNumber streetNumber = address.getStreetNumber();
                        if (streetNumber != null) {
                            subthf = streetNumber.getNumber();
                        }
                        String fn = address.getBuilding();//标志性建筑,当道路为null时显示
                        if (area != null)
                            stringBuffer.append(area);
                        if (loc != null && !area.equals(loc))
                            stringBuffer.append(loc);
                        if (subLoc != null)
                            stringBuffer.append(subLoc);
                        if (ts != null)
                            stringBuffer.append(ts);
                        if (thf != null)
                            stringBuffer.append(thf);
                        if (subthf != null)
                            stringBuffer.append(subthf);
                        if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                            stringBuffer.append(fn + "附近");

                        lat = latLng.latitude;
                        lng = latLng.longitude;
                        Location_position = stringBuffer.toString();

                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                setPosition();

                            }
                        });

                    }
                }).start();
            }
        }

        @Override
        public void onCameraChange(CameraPosition position) {
            if (locationMarker != null) {
                LatLng latLng = position.target;
                locationMarker.setPosition(latLng);
            }
        }
    };


    private void initLoc() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
            return;
        }
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听

        if (address.equals("")) {
            mLocationClient.setLocationListener(this);
        } else {
            //添加图钉
            locationLatLng = new LatLng(lat, lng);
            addMarker();
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    locationLatLng, 15));
            currentPosition = (EditText) findViewById(R.id.current_position);
            currentPosition.setText(address);
            my_4s_address_detail = (TextView) findViewById(R.id.my_4s_address_detail);
            my_4s_address_detail.setText(address);
        }
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
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker, final LatLng latLng) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(latLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * latLng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * latLng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    private MarkerOptions getMarkerOptions(String str) {
        //   AttentionModel attentionModel
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        //  options.icon(options.getIcon());
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.shop_manage_shop_address));
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
                        currentPosition = (EditText) findViewById(R.id.current_position);
                        currentPosition.setText(address);
                        my_4s_address_detail = (TextView) findViewById(R.id.my_4s_address_detail);
                        my_4s_address_detail.setText(address);
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
        if (null != mLocationClient) {
            mLocationClient.stopLocation();

        }
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
                    if (locationMarker != null) {
                        locationMarker.remove();
                    }

                    addMarker();
/*
                    locationMarker = aMap.addMarker(getMarkerOptions(address));
*/
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();
                    }
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
                    currentPosition = (EditText) findViewById(R.id.current_position);
                    currentPosition.setText(address);
                    my_4s_address_detail = (TextView) findViewById(R.id.my_4s_address_detail);
                    my_4s_address_detail.setText(address);

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
        progDialog.setMessage("正在搜索：...");
        progDialog.show();
    }

    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
}
