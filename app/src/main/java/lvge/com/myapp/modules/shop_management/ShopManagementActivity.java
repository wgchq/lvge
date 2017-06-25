package lvge.com.myapp.modules.shop_management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

        RelativeLayout lyy_shop_img = (RelativeLayout) findViewById(R.id.lly_shop_img_add);
        lyy_shop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this, ShopManageShopImgActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout lly_shop_authentication = (RelativeLayout) findViewById(R.id.lly_shop_authentication);
        lly_shop_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this, ShopManageAuthenticationActivity.class);
                startActivity(intent);
            }
        });


        RelativeLayout lly_shop_deposit = (RelativeLayout) findViewById(R.id.lly_shop_deposit);
        lly_shop_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this, ShopManageShopDepositActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout lly_shop_contact = (RelativeLayout) findViewById(R.id.lly_shop_contact);
        lly_shop_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ShopManagementActivity.this).setTitle("联系电话")
                        .setIcon(android.R.drawable.ic_menu_call)
                        .setView(new EditText(ShopManagementActivity.this))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


    }

}
