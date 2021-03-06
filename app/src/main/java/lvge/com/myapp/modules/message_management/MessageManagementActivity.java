package lvge.com.myapp.modules.message_management;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import lvge.com.myapp.R;
import lvge.com.myapp.mainFragement.HomeFragment;

public class MessageManagementActivity extends AppCompatActivity {

    private SystemMessageFragment systemMessageFragment = null;
    private OrderMessageFragment orderMessageFragment = null;
    private android.app.FragmentManager fragmentManager = getFragmentManager();

    private TextView system_message_textview;
    private TextView order_message_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_management);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_validation_history);
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

        system_message_textview = (TextView)findViewById(R.id.system_message_textview);
        system_message_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                order_message_textview.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.order_message_no_select),null,null);
                system_message_textview.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.system_message_selected),null,null);
                if (systemMessageFragment == null) {
                    systemMessageFragment = new SystemMessageFragment();
                }
                transaction.replace(R.id.message_management_fragment, systemMessageFragment);
                transaction.commit();
            }
        });

        order_message_textview = (TextView)findViewById(R.id.order_message_textview);
        order_message_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                system_message_textview.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.system_message_no_select),null,null);
                order_message_textview.setCompoundDrawablesWithIntrinsicBounds(null,getResources().getDrawable(R.mipmap.order_message_selected),null,null);
                if (orderMessageFragment == null) {
                    orderMessageFragment = new OrderMessageFragment();
                }
                transaction.replace(R.id.message_management_fragment, orderMessageFragment);
                transaction.commit();
            }
        });

    }

    private void DefaultFragment() {
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (null == systemMessageFragment) {
            systemMessageFragment = new SystemMessageFragment();
        }

        transaction.add(R.id.message_management_fragment, systemMessageFragment);
        transaction.commit();
    }
}
