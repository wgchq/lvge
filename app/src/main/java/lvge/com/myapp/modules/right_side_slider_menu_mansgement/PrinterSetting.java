package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

public class PrinterSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pirinter_setting_toobar);
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
    }
}
