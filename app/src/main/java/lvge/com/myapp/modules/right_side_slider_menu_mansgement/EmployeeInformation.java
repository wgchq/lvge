package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import lvge.com.myapp.R;
import lvge.com.myapp.model.SalesConsutantListViewData;

public class EmployeeInformation extends AppCompatActivity {

    private TextView employee_information_toadd;
    private SwipeMenuListView employee_information_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_emplyee_information_list);
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

        employee_information_toadd = (TextView)findViewById(R.id.employee_information_toadd);
        employee_information_listview = (SwipeMenuListView)findViewById(R.id.employee_information_listview);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("删除");
                swipeMenu.addMenuItem(deleteItem);
            }
        };

        employee_information_toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeInformation.this,EmployeeInformationAdd.class);
                startActivity(intent);
            }
        });

        employee_information_listview.setMenuCreator(creator);

        //添加删除事件
        employee_information_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
               // SalesConsutantListViewData item = contentList.get(i);
                switch (i1) {
                    case 0:
                       // removeConsultant(item.getId());
                        // getListItem();
                }
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
