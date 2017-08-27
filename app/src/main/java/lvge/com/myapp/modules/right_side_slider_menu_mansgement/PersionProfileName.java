package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class PersionProfileName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_profile_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_persion_profile_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String inputname = intent.getStringExtra("inputname");
        EditText phone_number = (EditText) findViewById(R.id.persion_profile_name_input);
        phone_number.setText(inputname);

        TextView complete = (TextView) findViewById(R.id.persion_profile_name_compltant);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputname = (EditText) findViewById(R.id.persion_profile_name_input);
                String str_name = inputname.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("inputname",str_name);
                setResult(10,intent);
                finish();
            }
        });
    }
}
