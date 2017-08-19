package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class SaleConsultantTwoPhonoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_consultant_two_phono);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sonsultant_two_phone);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String inputphone = intent.getStringExtra("inputphone");
        EditText phone_number = (EditText) findViewById(R.id.sales_consultant_two_phone_input);
        phone_number.setText(inputphone);

        TextView complete = (TextView) findViewById(R.id.consultant_two_phone_compltant);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputphone = (EditText) findViewById(R.id.sales_consultant_two_phone_input);
                String str_inputphone = inputphone.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("inputphone",str_inputphone);
                setResult(11,intent);
                finish();
            }
        });
    }
}
