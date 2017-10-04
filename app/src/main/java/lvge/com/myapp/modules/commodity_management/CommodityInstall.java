package lvge.com.myapp.modules.commodity_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lvge.com.myapp.R;

public class CommodityInstall extends AppCompatActivity {

    private RelativeLayout commodity_install_on;
    private RelativeLayout commodity_install_off;
    private ImageView commodity_install_imagebutton;
    private ImageView commodity_install_imagebutton2;

    private String str = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_install);

        commodity_install_on = (RelativeLayout)findViewById(R.id.commodity_install_on);
        commodity_install_off = (RelativeLayout)findViewById(R.id.commodity_install_off);
        commodity_install_imagebutton = (ImageView)findViewById(R.id.commodity_install_imagebutton);
        commodity_install_imagebutton2 = (ImageView)findViewById(R.id.commodity_install_imagebutton2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_newgoods_installl);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView commodity_newgoods_Commoditytype_finish = (TextView)findViewById(R.id.commodity_newgoods_install_finish);
        commodity_newgoods_Commoditytype_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inputinstallchoose",str);
                setResult(12,intent);
                finish();
            }
        });

        commodity_install_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commodity_install_imagebutton.setImageResource(R.mipmap.checkbox_checked);
                commodity_install_imagebutton2.setImageResource(R.color.mainBackgroundColor);
                str = "不支持门店安装";
            }
        });

        commodity_install_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commodity_install_imagebutton2.setImageResource(R.mipmap.checkbox_checked);
                commodity_install_imagebutton.setImageResource(R.color.mainBackgroundColor);
                str = "支持门店安装";
            }
        });

    }
}
