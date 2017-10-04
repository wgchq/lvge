package lvge.com.myapp.modules.commodity_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.modules.message_management.SystemMessageFragment;

//import android.support.v4.content.ContextCompatApi23;

public class CommodityManageHomepage extends AppCompatActivity {

    private android.app.FragmentManager fragmentManager = getFragmentManager();
    private CommodityHomepageOnsale commodityHomepageOnsale = null;
    private CommodityHomepagemSoldout commodityHomepagemSoldout = null;
    private CommodityHomepageShelffailure commodityHomepageShelffailure = null;

    private TabLayout tabLayout;
    private ImageView imageView_newgoods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_manage_homepage);

        imageView_newgoods = (ImageView)findViewById(R.id.commodity_list_newgoods);
        imageView_newgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommodityManageHomepage.this,CommodityNewgoods.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_management);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                // startActivity(intent);
                finish();
            }
        });

        DefaultFragment();

        tabLayout = (TabLayout)findViewById(R.id.commodity_manage_homepage_tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (tab.getPosition()){
                    case 0:
                        if (null == commodityHomepageOnsale) {
                            commodityHomepageOnsale = new CommodityHomepageOnsale();
                        }
                        transaction.replace(R.id.commmodity_manager_fragment,commodityHomepageOnsale);
                        transaction.show(commodityHomepageOnsale);
                        break;
                    case 1:
                        if(null == commodityHomepagemSoldout){
                            commodityHomepagemSoldout = new CommodityHomepagemSoldout();
                        }
                        transaction.replace(R.id.commmodity_manager_fragment,commodityHomepagemSoldout);
                        transaction.show(commodityHomepagemSoldout);
                        break;
                    case 2:
                        if(null == commodityHomepageShelffailure){
                            commodityHomepageShelffailure = new CommodityHomepageShelffailure();
                        }
                        transaction.replace(R.id.commmodity_manager_fragment,commodityHomepageShelffailure);
                        transaction.show(commodityHomepageShelffailure);
                        break;
                    default:
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void DefaultFragment() {
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (null == commodityHomepageOnsale) {
            commodityHomepageOnsale = new CommodityHomepageOnsale();
        }
        transaction.add(R.id.commmodity_manager_fragment,commodityHomepageOnsale);
        transaction.commit();
    }

}
