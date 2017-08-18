package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class SalesConsultantPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant_phone);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sonsultant_phone);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("edit","f");
                setResult(6,intent);
                finish();
            }
        });


        Intent intent = getIntent();
        String inputnumber = intent.getStringExtra("inputnumber");
        EditText phone_number = (EditText) findViewById(R.id.phone_number);
        phone_number.setText(inputnumber);

        TextView complete = (TextView) findViewById(R.id.consultant_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText phone_number = (EditText) findViewById(R.id.phone_number);
                String str_phone_number = phone_number.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("phone_number",str_phone_number);
                intent.putExtra("edit","t");
                setResult(6,intent);
                finish();
            }
        });

    }
}
