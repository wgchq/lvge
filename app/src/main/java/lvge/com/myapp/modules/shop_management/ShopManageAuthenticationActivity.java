package lvge.com.myapp.modules.shop_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;


import lvge.com.myapp.R;

public class ShopManageAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage_shop_authentication);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_authentication);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (findViewById(R.id.fragment_container_authentication) != null) {

            if (savedInstanceState != null) {
                return;
            }
            int a = 3;

            switch (a) {
                case 1:
                    NotAuthenticationFragment notAuthenticationFragment = new NotAuthenticationFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, notAuthenticationFragment)
                            .commit();
                    break;
                case 2:

                    HasCommitAuthenticationFragment hasCommitAuthenticationFragment = new HasCommitAuthenticationFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, hasCommitAuthenticationFragment)
                            .commit();
                    break;
                case 3:

                    NotPassAuthenticationFragment notPassAuthenticationFragment = new NotPassAuthenticationFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, notPassAuthenticationFragment)
                            .commit();
                    break;
            }


        }
    }

}
