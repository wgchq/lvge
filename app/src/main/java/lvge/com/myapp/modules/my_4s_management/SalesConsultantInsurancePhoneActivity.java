package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class SalesConsultantInsurancePhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant_insurance_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_consultant_insurance_phone);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("edit","f");
                setResult(55,intent);
                finish();
            }
        });


        Intent intent = getIntent();
        String inputInsurancenumber = intent.getStringExtra("inputInsurancenumber");
        EditText phone_number = (EditText) findViewById(R.id.insurance_phone_number);
        phone_number.setText(inputInsurancenumber);

        TextView complete = (TextView) findViewById(R.id.insurance_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText insurance_phone_number = (EditText) findViewById(R.id.insurance_phone_number);
                String str_insurance_phone_number = insurance_phone_number.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("insurance_phone_number",str_insurance_phone_number);
                intent.putExtra("edit","t");
                setResult(55,intent);
                finish();
            }
        });

    }
}
