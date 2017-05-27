package lvge.com.myapp;


import android.app.Activity;

import android.os.Bundle;

import android.view.View;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lvge.com.myapp.ui.MenuAdapter;
import lvge.com.myapp.ui.SlideMenu;


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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);
        String menu_personal = this.getString(R.string.menu_personal);
        String menu_staff_info = this.getString(R.string.menu_staff_info);
        String menu_authority = this.getString(R.string.menu_authority);
        String menu_push_notice = this.getString(R.string.menu_push_notice);
        String menu_print_setting = this.getString(R.string.menu_print_setting);
        String menu_exit = this.getString(R.string.menu_exit);

        mMenu = (SlideMenu) findViewById(R.id.id_menu);

        listView = (ListView) findViewById(R.id.lv_menus);
        menuAdapter = new MenuAdapter(this, getMenuListItems());
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
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
