package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Call;
import okhttp3.Response;

public class PersionProfileChangePassword extends AppCompatActivity {

    private TextView persion_profile_name_compltant;
    private EditText persion_profile_inputnewpassword;
    private EditText persion_profile_inputnewpassword_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_profile_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_persion_profile_changepassword);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        persion_profile_name_compltant = (TextView)findViewById(R.id.persion_profile_name_compltant);
        persion_profile_inputnewpassword = (EditText)findViewById(R.id.persion_profile_inputnewpassword);
        persion_profile_inputnewpassword_again = (EditText)findViewById(R.id.persion_profile_inputnewpassword_again);

        persion_profile_name_compltant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = persion_profile_inputnewpassword.getText().toString();
                String confirmPassword = persion_profile_inputnewpassword_again.getText().toString();
                if(password.equals("") || confirmPassword.equals("")){
                    Toast.makeText(PersionProfileChangePassword.this, "新密码或确认新密码为空，请重新填入！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(confirmPassword)){
                    Intent intent = new Intent();
                    intent.putExtra("password",password);
                    intent.putExtra("confirmPassword",confirmPassword);
                    setResult(10,intent);
                    finish();
                }else {
                    Toast.makeText(PersionProfileChangePassword.this, "新密码或确认新密码不一致，请重新填入！", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}
