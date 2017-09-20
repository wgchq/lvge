package lvge.com.myapp.modules.message_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lvge.com.myapp.R;

public class MessageDetail extends AppCompatActivity {

    private TextView message_detail_title;
    private TextView message_detail_time;
    private TextView message_detail_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        message_detail_title = (TextView)findViewById(R.id.message_detail_title);
        message_detail_time = (TextView)findViewById(R.id.message_detail_time);
        message_detail_message = (TextView)findViewById(R.id.message_detail_message);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        message_detail_title.setText(title);
        String time = intent.getStringExtra("time");
        message_detail_time.setText(time);
        String message = intent.getStringExtra("message");
        message_detail_message.setText(message);
    }
}
