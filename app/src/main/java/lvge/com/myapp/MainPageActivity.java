package lvge.com.myapp;


import android.app.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lvge.com.myapp.ui.MenuAdapter;
import lvge.com.myapp.ui.SlideMenu;
import lvge.com.myapp.util.BottomNavigationViewHelper;


public class MainPageActivity extends Activity {

    private SlideMenu mMenu;

    private int[] menus_logo = {
            R.mipmap.menu_personal,
            R.mipmap.menu_staff_info,
            R.mipmap.menu_authority,
            R.mipmap.menu_push_notice,
            R.mipmap.menu_print_setting,
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

        listView = (ListView) findViewById(R.id.lv_menus);
        menuAdapter = new MenuAdapter(this, getMenuListItems());
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


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
    }

    private List<Map<String, Object>> getMenuListItems() {

        String[] menu_text = {
                this.getString(R.string.menu_personal),
                this.getString(R.string.menu_staff_info),
                this.getString(R.string.menu_authority),
                this.getString(R.string.menu_push_notice),
                this.getString(R.string.menu_print_setting),
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
