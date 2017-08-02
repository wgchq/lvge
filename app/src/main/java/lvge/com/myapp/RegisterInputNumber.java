package lvge.com.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class RegisterInputNumber extends AppCompatActivity {
    private EditText register_input_number;
    private Button register_input_number_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_input_number);

        register_input_number = (EditText)findViewById(R.id.register_input_number);
        register_input_number.setInputType(InputType.TYPE_CLASS_PHONE);
        register_input_number_button = (Button)findViewById(R.id.register_input_number_button);

        register_input_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null || s.length() == 0)
                    return;
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<s.length();i++){
                    if(i!=3 && i!=8 && s.charAt(i) == '-'){
                        continue;
                    }else {
                        sb.append(s.charAt(i));
                        if((sb.length() == 4 || sb.length()==9) && sb.charAt(sb.length()-1) != '-'){
                            sb.insert(sb.length()-1,'-');
                        }
                    }
                }
                if(!sb.toString().equals(s.toString())){
                    int index = start + 1;
                    if(sb.charAt(start) == '-'){
                        if(before == 0){
                            index++;
                        }else {
                            index--;
                        }
                    }else {
                        if(before == 1){
                            index--;
                        }
                    }
                    register_input_number.setText(sb.toString());
                    register_input_number.setSelection(index);
                }
                register_input_number_button.setBackgroundResource(R.drawable.lg_main_button_background_input);
                register_input_number_button.setTextColor(getResources().getColor(R.color.buttonTextColor));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(register_input_number_button.getText().toString().equals("")){
                    register_input_number_button.setBackgroundResource(R.drawable.lg_main_button_background);
                    register_input_number_button.setTextColor(getResources().getColor(R.color.lg_button_text_color));
                }
            }
        });


    }
}
