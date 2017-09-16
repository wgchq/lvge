package lvge.com.myapp.modules.validationtypescanqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;

public class ValidationTypeScanQRFailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_type_scan_qrfail);

        TextView re_validation = (TextView) findViewById(R.id.validation_type_scan_QR_re_validation);
        re_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidationTypeScanQRFailActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
