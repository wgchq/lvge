package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import lvge.com.myapp.R;
import lvge.com.myapp.modules.shop_management.ShopManageShopImgActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;

public class My4sManagementActivity extends AppCompatActivity {

    private File cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_management);






        ImageView my4s_manage_back = (ImageView)findViewById(R.id.my4s_management_back);   //返回图片
        ImageView my4s_manage_sales_consultant = (ImageView)findViewById(R.id.commodity_my4s_sales_consultant);   //销售顾问
        ImageView my4s_manage_address = (ImageView)findViewById(R.id.commodity_my4s_address);    //地址
        TextView my4s_manage_finish = (TextView)findViewById(R.id.my4s_finish);

        my4s_manage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        my4s_manage_sales_consultant.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, SalesConsultant.class);
                startActivity(intent);
            }
        }));

        my4s_manage_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, My4sAddressActivity.class);
                startActivity(intent);
            }
        });

        my4s_manage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

}
