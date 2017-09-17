package lvge.com.myapp.modules.validationtypescanqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

public class ValidationTypeScanQRSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_type_scan_qr_success);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_validation_type_scan_QR_success);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
