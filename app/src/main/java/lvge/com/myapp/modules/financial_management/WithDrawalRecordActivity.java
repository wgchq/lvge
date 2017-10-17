package lvge.com.myapp.modules.financial_management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import lvge.com.myapp.R;

public class WithDrawalRecordActivity extends AppCompatActivity {

   private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_drawal_record);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.financial_management_with_drawal_record);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView with_drawal_record_calendar = (ImageView)findViewById(R.id.with_drawal_record_calendar);
        with_drawal_record_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,WithDrawalRecordCalendarActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
