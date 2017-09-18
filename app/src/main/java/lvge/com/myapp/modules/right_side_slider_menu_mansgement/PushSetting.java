package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import lvge.com.myapp.R;
import lvge.com.myapp.model.LoadRightSideMode;

public class PushSetting extends AppCompatActivity {

    private SwitchButton employees_manager_switchbutton;
    private SwitchButton push_setting_switchbutton_fangge;
    private SwitchButton push_setting_switchbutton_sound;
    private SwitchButton push_setting_switchbutton_jarrying;

    private SharedPreferences preferences;
    private final static String FILE_NAME = "login_file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);

        employees_manager_switchbutton = (SwitchButton)findViewById(R.id.employees_manager_switchbutton);
        employees_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        push_setting_switchbutton_fangge = (SwitchButton)findViewById(R.id.push_setting_switchbutton_fangge);
        push_setting_switchbutton_fangge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        push_setting_switchbutton_sound = (SwitchButton)findViewById(R.id.push_setting_switchbutton_sound);
        push_setting_switchbutton_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        push_setting_switchbutton_jarrying = (SwitchButton)findViewById(R.id.push_setting_switchbutton_jarrying);
        push_setting_switchbutton_jarrying.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    push_setting_switchbutton_jarrying.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    push_setting_switchbutton_jarrying.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String string = preferences.getString("right_data","");
        LoadRightSideMode result = new Gson().fromJson(string, LoadRightSideMode.class);

        String power = result.getMarketEntity().getConfigSwitch();
        if(power.equals("")){
            employees_manager_switchbutton.setChecked(true);
            employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            push_setting_switchbutton_fangge.setChecked(true);
            push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.RED));

            push_setting_switchbutton_sound.setChecked(true);
            push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.RED));

            push_setting_switchbutton_jarrying.setChecked(true);
            push_setting_switchbutton_jarrying.setBackColor(ColorStateList.valueOf(Color.RED));

        }else {
            String[] str = power.split("-");
            try {
                if (str.length >= 1 && str[0].equals("0")) {
                    employees_manager_switchbutton.setChecked(false);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    employees_manager_switchbutton.setChecked(true);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 2 && str[1].equals("0")) {
                    push_setting_switchbutton_fangge.setChecked(false);
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    push_setting_switchbutton_fangge.setChecked(true);
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 3 && str[2].equals("0")) {
                    push_setting_switchbutton_sound.setChecked(false);
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    push_setting_switchbutton_sound.setChecked(true);
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 4 && str[3].equals("0")) {
                    push_setting_switchbutton_jarrying.setChecked(false);
                    push_setting_switchbutton_jarrying.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    push_setting_switchbutton_jarrying.setChecked(true);
                    push_setting_switchbutton_jarrying.setBackColor(ColorStateList.valueOf(Color.RED));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
}
