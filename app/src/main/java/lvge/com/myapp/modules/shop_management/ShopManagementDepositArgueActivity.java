package lvge.com.myapp.modules.shop_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class ShopManagementDepositArgueActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage_shop_deposit_argue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_deposit_argue);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
