package lvge.com.myapp;


import android.app.Activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lvge.com.myapp.mainFragement.ClientFragment;
import lvge.com.myapp.mainFragement.HomeFragment;
import lvge.com.myapp.mainFragement.MyFragment;
import lvge.com.myapp.mainFragement.OrderFragment;
import lvge.com.myapp.ui.MenuAdapter;
import lvge.com.myapp.ui.SlideMenu;
import lvge.com.myapp.util.BottomNavigationViewHelper;


public class MainPageActivity extends Activity {

    private HomeFragment homeFragment = null;
    private ClientFragment clientFragment = null;
    private OrderFragment orderFragment = null;
    private MyFragment myFragment = null;
    private FragmentManager fragmentManager = getFragmentManager();


    private SlideMenu mMenu;

    private int[] menus_logo = {
       /*     R.mipmap.menu_personal,
            R.mipmap.menu_staff_info,
            R.mipmap.menu_authority,
            R.mipmap.menu_push_notice,
            R.mipmap.menu_print_setting,*/
            R.mipmap.menu_exit
    };

    private ListView listView;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*// 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_main_page);

        mMenu = (SlideMenu) findViewById(R.id.id_menu);

        TextView nav_header_main_shop_name = (TextView)findViewById(R.id.nav_header_main_shop_name);
        final Bundle bundle = getIntent().getExtras();
        nav_header_main_shop_name.setText(bundle.getString("name"));

        listView = (ListView) findViewById(R.id.lv_menus);
        menuAdapter = new MenuAdapter(this, getMenuListItems());
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    AlertDialog.Builder  builder = new AlertDialog.Builder(MainPageActivity.this);
                    builder.setIcon(R.mipmap.warming);
                    builder.setTitle("注销");
                    builder.setMessage("是否确定注销？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
            }

            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        DefaultFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                if (homeFragment == null) {
                                    homeFragment = new HomeFragment();
                                }
                                transaction.replace(R.id.fragment_content, homeFragment);

                                break;
                            case R.id.navigation_client:
                                if (clientFragment == null) {
                                    clientFragment = new ClientFragment();
                                }
                                transaction.replace(R.id.fragment_content, clientFragment);

                                transaction.show(clientFragment);
                                break;
                            case R.id.navigation_order:
                                if (orderFragment == null) {
                                    orderFragment = new OrderFragment();
                                }
                                transaction.replace(R.id.fragment_content, orderFragment);

                                transaction.show(orderFragment);
                                break;

                            case R.id.navigation_my:
                                if (myFragment == null) {
                                    myFragment = new MyFragment();
                                }
                                transaction.replace(R.id.fragment_content, myFragment);

                                transaction.show(myFragment);
                                break;
                        }
                        transaction.commit();
                        return true;
                    }
                });
    }


    private void DefaultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homeFragment == null) {

            homeFragment = new HomeFragment();
        }
        Bundle bundle = getIntent().getExtras();

        Bundle bud = new Bundle();
        bud.putString("name",bundle.getString("name"));
        homeFragment.setArguments(bud);


        transaction.add(R.id.fragment_content, homeFragment);
        transaction.commit();
    }

    private List<Map<String, Object>> getMenuListItems() {

        String[] menu_text = {
              /*  this.getString(R.string.menu_personal),
                this.getString(R.string.menu_staff_info),
                this.getString(R.string.menu_authority),
                this.getString(R.string.menu_push_notice),
                this.getString(R.string.menu_print_setting),*/
                this.getString(R.string.menu_exit)
        };


        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < menus_logo.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menuLogo", menus_logo[i]);
            map.put("title", menu_text[i]);
            listItems.add(map);
        }
        return listItems;
    }

    public void toggleMenu(View view) {
        mMenu.toggle();
    }


}
