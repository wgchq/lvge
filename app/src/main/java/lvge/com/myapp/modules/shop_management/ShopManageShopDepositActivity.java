package lvge.com.myapp.modules.shop_management;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import lvge.com.myapp.R;
        import lvge.com.myapp.modules.wechat.PayMainActivity;

public class ShopManageShopDepositActivity extends AppCompatActivity {

    private TextView shop_deposit_argue;
    private Button btn_shop_deposit_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manage_shop_deposit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_deposit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shop_deposit_argue = (TextView)findViewById(R.id.shop_deposit_argue);
        shop_deposit_argue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManageShopDepositActivity.this,ShopManagementDepositArgueActivity.class);
                startActivity(intent);
            }
        });

        btn_shop_deposit_get = (Button)findViewById(R.id.btn_shop_deposit_get);
        //暂时屏蔽支付功能
        btn_shop_deposit_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManageShopDepositActivity.this, PayMainActivity.class);
                startActivity(intent);
            }
        });

    }
}
