package lvge.com.myapp.modules.shop_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;


import lvge.com.myapp.R;

public class ShopManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_management);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RelativeLayout lyy_shop_img = (RelativeLayout)findViewById(R.id.lly_shop_img_add);
        lyy_shop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this,ShopManageShopImgActivity.class);
                startActivity(intent);
            }
        });

    }
}
