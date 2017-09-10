package lvge.com.myapp.modules.shop_management;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.TextView;


import org.w3c.dom.Text;

import lvge.com.myapp.R;

public class ShopManageAuthenticationActivity extends AppCompatActivity {

    public static Fragment[] fragments;
    private NotAuthenticationFragment notAuthenticationFragment;
    private HasCommitAuthenticationFragment hasCommitAuthenticationFragment;
    private NotPassAuthenticationFragment notPassAuthenticationFragment;
    private CommittingAuthenticFragment committingAuthenticFragment;
    private HasPassAuthenticationFragment hasPassAuthenticationFragment;


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

        Intent intent = getIntent();
        String authentic = intent.getStringExtra("authentic");

        TextView shop_authentication_help = (TextView)findViewById(R.id.shop_authentication_help);


        notAuthenticationFragment = new NotAuthenticationFragment(handler);

        hasCommitAuthenticationFragment = new HasCommitAuthenticationFragment();

        notPassAuthenticationFragment = new NotPassAuthenticationFragment(handler);

        committingAuthenticFragment = new CommittingAuthenticFragment();

        hasPassAuthenticationFragment = new HasPassAuthenticationFragment();

        shop_authentication_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManageAuthenticationActivity.this,ShopManageAuthenticationHelpActivity.class);
                startActivity(intent);
            }
        });

        if (findViewById(R.id.fragment_container_authentication) != null) {

            if (savedInstanceState != null) {
                return;
            }
            //authentic = ShopManagementParameter.AUTHENTIC_NOPASS;
            switch (authentic) {
                case ShopManagementParameter.AUTHENTIC_NOAPPLY:     //没申请
                    handler.sendEmptyMessage(ShopManagementParameter.AUTHENTIC_NOTAUTHENTIC_PAGE);
                    break;
                case ShopManagementParameter.AUTHENTIC_APPLYING:    //申请中
                    handler.sendEmptyMessage(ShopManagementParameter.AUTHENTIC_COMMITTING_PAGE);
                    break;
                case ShopManagementParameter.AUTHENTIC_NOPASS:      //没通过
                    handler.sendEmptyMessage(ShopManagementParameter.AUTHENTIC_NOTPASS_PAGE);
                    break;
                case ShopManagementParameter.AUTHENTIC_PASSING:     //已通过
                    handler.sendEmptyMessage(ShopManagementParameter.AUTHENTIC_HASPASS_PAGE);
                    break;
            }


        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case ShopManagementParameter.AUTHENTIC_NOTAUTHENTIC_PAGE:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, notAuthenticationFragment)
                            .commit();
                    break;
                case ShopManagementParameter.AUTHENTIC_HASCOMMIT_PAGE:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, hasCommitAuthenticationFragment)
                            .commit();
                    break;
                case ShopManagementParameter.AUTHENTIC_NOTPASS_PAGE:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, notPassAuthenticationFragment)
                            .commit();
                    break;
                case ShopManagementParameter.AUTHENTIC_COMMITTING_PAGE:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, committingAuthenticFragment)
                            .commit();
                    break;
                case ShopManagementParameter.AUTHENTIC_HASPASS_PAGE:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_authentication, hasPassAuthenticationFragment)
                            .commit();
                    break;
            }
        }
    };
}
