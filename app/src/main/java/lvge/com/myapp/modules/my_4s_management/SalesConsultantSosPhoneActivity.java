package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class SalesConsultantSosPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant_sos_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_consultant_sos_phone);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("edit","f");
                setResult(5,intent);
                finish();
            }
        });

        Intent intent = getIntent();
        String sosphonenumber = intent.getStringExtra("sosnumber");
        EditText sosphone = (EditText) findViewById(R.id.sos_phone_number);
        sosphone.setText(sosphonenumber);

        TextView complete = (TextView) findViewById(R.id.sos_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sosphone = (EditText) findViewById(R.id.sos_phone_number);
                String str_sosphone = sosphone.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("sosphone",str_sosphone);
                intent.putExtra("edit","t");
                setResult(5,intent);
                finish();
            }
        });
    }
}
