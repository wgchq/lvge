package lvge.com.myapp.modules.shop_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

/**
 * Created by cnhao on 2017/8/28.
 */

public class ShopManageAuthenticationHelpActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage_submit_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_management_help);
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
