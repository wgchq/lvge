package lvge.com.myapp.modules.shop_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

/**
 * Created by cnhao on 2017/9/7.
 */

public class ShopManagementPhonenumber extends AppCompatActivity {

    private TextView shop_finish_phonenumber;
    private EditText shop_phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management_phonenumber);

        Intent intent = getIntent();
        String phonenumber = intent.getStringExtra("phonenumber");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_phonenumber);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shop_finish_phonenumber = (TextView)findViewById(R.id.shop_finish_phonenumber);

        shop_phonenumber = (EditText)findViewById(R.id.shop_phonenumber);
        shop_phonenumber.setText(phonenumber);

        shop_finish_phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber;
                if(null == shop_phonenumber.getText())
                    phonenumber = "";
                else
                    phonenumber = shop_phonenumber.getText().toString();

                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("PhonenumberResult", phonenumber);
                intent.putExtras(bundle);
                setResult(1001,intent);
                finish();
            }
        });
    }
}
