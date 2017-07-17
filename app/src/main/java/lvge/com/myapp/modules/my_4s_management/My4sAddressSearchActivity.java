package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.LatLng;

import java.util.ArrayList;

import lvge.com.myapp.R;
import lvge.com.myapp.model.AddressModel;

public class My4sAddressSearchActivity extends AppCompatActivity {
    private Double lat;
    private Double lng;
    private String address;
    private ListView search_list_view;
    private My4sAddressSearchAdapter adapter;
    private ArrayList<AddressModel> data;
    private String city = "";
    private EditText current_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_address_search);



        current_position = (EditText)findViewById(R.id.current_position);

        ImageView my4s_address_search_ImageView = (ImageView) findViewById(R.id.my4s_address_search_ImageView);
        my4s_address_search_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearchQuery(current_position.getText().toString().trim());
            }
        });
    }

    private void doSearchQuery(String str) {

        try {
            search_list_view = (ListView) findViewById(R.id.search_list_view);
            adapter = new My4sAddressSearchAdapter(this);
            data = new ArrayList<AddressModel>();
            adapter.setData(data);
            search_list_view.setAdapter(adapter);
            InputTask inputTask = new InputTask(this, adapter);
            inputTask.onSearch(str, city);
            search_list_view.setAdapter(adapter);

            search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EditText current_position = (EditText) findViewById(R.id.current_position);
                    lat = adapter.getData().get(position).getLatitude();
                    lng = adapter.getData().get(position).getLongitude();
                    address = adapter.getData().get(position).getText();
                    Intent intent = new Intent(My4sAddressSearchActivity.this, My4sAddressActivity.class);
                   startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
