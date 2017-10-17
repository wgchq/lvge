package lvge.com.myapp.modules.financial_management;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

public class WithDrawRecordDetailActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_record_detail);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.financial_management_with_drawal_record_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
