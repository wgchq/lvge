package lvge.com.myapp.modules.customer_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lvge.com.myapp.R;

public class MessagePush extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_push);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_message_send);

        Intent intent = getIntent();
        String plate_no = intent.getStringExtra("plate_number");

        toolbar.setTitle(plate_no);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
