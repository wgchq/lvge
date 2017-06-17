package lvge.com.myapp.moduels.commodity_management;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
//import android.support.v4.content.ContextCompatApi23;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;

public class CommodityManageHomepage extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    //顶部的LinearLayout

    private LinearLayout mTabOnSale;    //出售中
    private LinearLayout mTabSoldout;   //已售完
    private LinearLayout mTabShelffailure;   //未上架
    private LinearLayout mTabApplication;    //申请中

    //顶部的TextView
    private TextView mOnSale;
    private TextView mSoldout;
    private TextView mShelffailure;
    private TextView mApplication;

    //tab的引导线
    private ImageView mTabLine;

    //ViewPager的当前选项
    private int currentIndex;

    //屏幕宽度
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_manage_homepage);

        mViewPager = (ViewPager)findViewById(R.id.id_commodity_viewpager);

        initView();

       initTabLine();


        //初始化Adapter
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        //设置监听

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(currentIndex == 0 && position == 0)  //0->1
                {
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)mTabLine.getLayoutParams();
                    lp.leftMargin = (int)(positionOffset*(screenWidth*1.0/4) + currentIndex * (screenWidth/4));
                    mTabLine.setLayoutParams(lp);
                }else if(currentIndex == 1 && position == 0) //1->0
                {
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)mTabLine.getLayoutParams();
                    lp.leftMargin = (int)(-(1 - positionOffset)*(screenWidth*1.0/4) + currentIndex * (screenWidth/4));
                    mTabLine.setLayoutParams(lp);
                }else if (currentIndex == 1 && position == 1) // 1->2
                {
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
                            .getLayoutParams();
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                    mTabLine.setLayoutParams(lp);
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
                            .getLayoutParams();
                    lp.leftMargin = (int) (-(1-positionOffset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                    mTabLine.setLayoutParams(lp);

                }

            }

            @Override
            public void onPageSelected(int position) {

                //重置所有TextView的字体颜色
                resetTextView();
                switch (position){
                    case 0:
                    mOnSale.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 1:
                        mSoldout.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        mShelffailure.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 3:
                        mApplication.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(1);
    }

    //初始化控件

    private void initView(){
        mTabOnSale = (LinearLayout)findViewById(R.id.id_tab_onsale_ly_);
        mTabSoldout = (LinearLayout)findViewById(R.id.id_tab_Soldout_ly);
        mTabShelffailure = (LinearLayout)findViewById((R.id.id_tab_Shelffailure_ly));
        mTabApplication = (LinearLayout)findViewById(R.id.id_tab_application_ly);

        mOnSale = (TextView)findViewById(R.id.id_onsale);
        mApplication = (TextView)findViewById(R.id.id_application);
        mShelffailure = (TextView)findViewById(R.id.id_shelffailure);
        mSoldout = (TextView)findViewById(R.id.id_soldout);

        CommodityHomepageOnsale chOnsale = new CommodityHomepageOnsale();
        CommodityHomepagemSoldout chSoldout = new CommodityHomepagemSoldout();
        CommodityHomepageShelffailure chShelffailure = new CommodityHomepageShelffailure();
        CommodityHomepageApplication chApplication = new CommodityHomepageApplication();

        mFragments.add(chOnsale);
        mFragments.add(chSoldout);
        mFragments.add(chShelffailure);
        mFragments.add(chApplication);


    }

    //根据屏幕宽度，初始化引导线的宽度
    private  void initTabLine(){
        mTabLine = (ImageView)findViewById(R.id.id_tab_line);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)mTabLine.getLayoutParams();

        lp.width = screenWidth /4;
        mTabLine.setLayoutParams(lp);
    }

    //重置颜色

    protected void resetTextView(){
        mOnSale.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        mSoldout.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        mShelffailure.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        mApplication.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }


}
