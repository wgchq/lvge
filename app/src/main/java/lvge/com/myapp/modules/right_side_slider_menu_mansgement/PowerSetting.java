package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.ProgressDialog.PickerScrollView;
import lvge.com.myapp.ProgressDialog.Pickers;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoadRightSideMode;

public class PowerSetting extends AppCompatActivity {

    private SwitchButton employees_manager_switchbutton;
    private SwitchButton shop_manage_switchbutton;
    private SwitchButton merchandise_manager_switchbutton;
    private SwitchButton order_manager_switchbutton;
    private SwitchButton appraise_manager_switchbutton;
    private SwitchButton commission_manager_switchbutton;
    private SwitchButton finance_manager_switchbutton;
    private SwitchButton performance_manager_switchbutton;
    private TextView sale_consultant_Preservation;
    private RelativeLayout power_setting_choosename_Relayout;
    private TextView persion_profile_iamgeview;

    private SharedPreferences preferences;
    private final static String FILE_NAME = "login_file";

    private PickerScrollView pickerscrlllview;// 滚动选择器 
    private RelativeLayout picker_rel;
    private TextView picker_ok;
    private TextView picker_off;

    private List<Pickers> list;// 滚动选择器数据  
    private String[] id;
    private String[] name;

    private String pick_id = "";
    private String pick_name = "";
    private String power = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.power_setting_toobar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                // startActivity(intent);
                finish();
            }
        });

        persion_profile_iamgeview = (TextView)findViewById(R.id.persion_profile_iamgeview);
        picker_rel = (RelativeLayout)findViewById(R.id.picker_rel);
        pickerscrlllview=(PickerScrollView)findViewById(R.id.pickerscrlllview);
        picker_ok = (TextView)findViewById(R.id.picker_ok);
        picker_off = (TextView)findViewById(R.id.picker_off);

        pickerscrlllview.setOnSelectListener(new PickerScrollView.onSelectListener() {
            @Override
            public void onSelect(Pickers pickers) {
                pick_id = pickers.getShowId();
                pick_name = pickers.getShowConetnt();
            }
        });

        picker_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_rel.setVisibility(View.GONE);
            }
        });

        picker_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_rel.setVisibility(View.GONE);
                persion_profile_iamgeview.setText(pick_name);
            }
        });
        power_setting_choosename_Relayout = (RelativeLayout)findViewById(R.id.power_setting_choosename_Relayout);
        power_setting_choosename_Relayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_rel.setVisibility(View.VISIBLE);
            }
        });

        list = new ArrayList<Pickers>();
        id=new String[] { "1", "2", "3", "4", "5", "6"};
        name = new String[] { "张三","李四","王五","车前","收到","阿斯顿"};
        for(int i=0;i<name.length;i++){
            list.add(new Pickers(name[i],id[i]));
        }
        // 设置数据，默认选择第一条  
        pickerscrlllview.setData(list);
        pickerscrlllview.setSelected(0);

        sale_consultant_Preservation = (TextView)findViewById(R.id.sale_consultant_Preservation);
        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //boolean a =    employees_manager_switchbutton.isChecked();  获取状态
            }
        });

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

        shop_manage_switchbutton = (SwitchButton)findViewById(R.id.shop_manage_switchbutton);
        shop_manage_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });

        merchandise_manager_switchbutton = (SwitchButton)findViewById(R.id.merchandise_manager_switchbutton);

        merchandise_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });

        order_manager_switchbutton = (SwitchButton)findViewById(R.id.order_manager_switchbutton);

        order_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });

        appraise_manager_switchbutton = (SwitchButton)findViewById(R.id.appraise_manager_switchbutton);
        appraise_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        commission_manager_switchbutton = (SwitchButton)findViewById(R.id.commission_manager_switchbutton);
        commission_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        finance_manager_switchbutton = (SwitchButton)findViewById(R.id.finance_manager_switchbutton);
        finance_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
        performance_manager_switchbutton = (SwitchButton)findViewById(R.id.performance_manager_switchbutton);
        performance_manager_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String string = preferences.getString("right_data","");
        LoadRightSideMode result = new Gson().fromJson(string, LoadRightSideMode.class);

        power = result.getMarketEntity().getAppRights();
        if(power.equals("")){
            employees_manager_switchbutton.setChecked(true);
            employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            shop_manage_switchbutton.setChecked(true);
            shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            merchandise_manager_switchbutton.setChecked(true);
            merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            order_manager_switchbutton.setChecked(true);
            order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            appraise_manager_switchbutton.setChecked(true);
            appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            commission_manager_switchbutton.setChecked(true);
            commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            finance_manager_switchbutton.setChecked(true);
            finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));

            performance_manager_switchbutton.setChecked(true);
            performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
        }else {
            String[] str = power.split("-");
            try{
                if(str.length >= 1 && str[0].equals("0")){
                    employees_manager_switchbutton.setChecked(false);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    employees_manager_switchbutton.setChecked(true);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 2 && str[1].equals("0")){
                    shop_manage_switchbutton.setChecked(false);
                    shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    shop_manage_switchbutton.setChecked(true);
                    shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 3 && str[2].equals("0")){
                    merchandise_manager_switchbutton.setChecked(false);
                    merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    merchandise_manager_switchbutton.setChecked(true);
                    merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 4 && str[3].equals("0")){
                    order_manager_switchbutton.setChecked(false);
                    order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    order_manager_switchbutton.setChecked(true);
                    order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 5 && str[4].equals("0")){
                    appraise_manager_switchbutton.setChecked(false);
                    appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    appraise_manager_switchbutton.setChecked(true);
                    appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 6 && str[5].equals("0")){
                    commission_manager_switchbutton.setChecked(false);
                    commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    commission_manager_switchbutton.setChecked(true);
                    commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 7 && str[6].equals("0")){
                    finance_manager_switchbutton.setChecked(false);
                    finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    finance_manager_switchbutton.setChecked(true);
                    finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if(str.length >= 8 && str[7].equals("0")){
                    performance_manager_switchbutton.setChecked(false);
                    performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }else {
                    performance_manager_switchbutton.setChecked(true);
                    performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
       // performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
    }

    private void updata(){
        if(employees_manager_switchbutton.isChecked()){

        }
    }
}
