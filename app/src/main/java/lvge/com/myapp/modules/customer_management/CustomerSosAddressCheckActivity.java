package lvge.com.myapp.modules.customer_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import lvge.com.myapp.R;

public class CustomerSosAddressCheckActivity extends AppCompatActivity {

    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sos_address_check);
       //显示地图
        mapView = (MapView) findViewById(R.id.map);
        //必须要写
        mapView.onCreate(savedInstanceState);
        //获取地图对象
        aMap = mapView.getMap();

    }
}
