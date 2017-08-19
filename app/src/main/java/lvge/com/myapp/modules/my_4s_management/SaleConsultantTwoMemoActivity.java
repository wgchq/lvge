package lvge.com.myapp.modules.my_4s_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class SaleConsultantTwoMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_consultant_two_memo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sonsultant_two_memo);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String inputmemo = intent.getStringExtra("inputmemo");
        EditText phone_number = (EditText) findViewById(R.id.sales_consultant_two_memo_input);
        phone_number.setText(inputmemo);

        TextView complete = (TextView) findViewById(R.id.consultant_two_memo_compltant);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputmemo = (EditText) findViewById(R.id.sales_consultant_two_memo_input);
                String str_memo = inputmemo.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("inputmemo",str_memo);
                setResult(12,intent);
                finish();
            }
        });
    }
}
