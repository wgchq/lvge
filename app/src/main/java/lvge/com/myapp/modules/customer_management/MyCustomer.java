package lvge.com.myapp.modules.customer_management;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;

public class MyCustomer extends AppCompatActivity {

    private ViewPager mVpage;
    private MyCustomerAllFragement allFragement = new MyCustomerAllFragement();
    private List<Fragment> fragmentslist = new ArrayList<Fragment>();
    private FragmentAdapter  mFragementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customer);

        mVpage = (ViewPager)findViewById(R.id.order_management_viewpager);
        fragmentslist.add(allFragement);

        mFragementAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentslist);
        mVpage.setOffscreenPageLimit(1);   //ViewPager的缓存为4帧
        mVpage.setCurrentItem(0);    //初始设置选中第一针

        mVpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class FragmentAdapter extends FragmentPagerAdapter{

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
