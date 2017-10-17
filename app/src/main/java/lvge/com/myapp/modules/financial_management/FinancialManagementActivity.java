package lvge.com.myapp.modules.financial_management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import lvge.com.myapp.R;

public class FinancialManagementActivity extends AppCompatActivity {


    private TextView fi_chat_detail;
    private TextView with_drawal_questions;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_management);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.financial_management_callback);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fi_chat_detail = (TextView) findViewById(R.id.fi_chat_detail);
        fi_chat_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WithDrawalRecordActivity.class);
                startActivity(intent);
            }
        });

        with_drawal_questions = (TextView) findViewById(R.id.with_drawal_questions);
        with_drawal_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WithDrawRecordDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
