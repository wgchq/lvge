package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.LoadRightSideMode;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Call;
import okhttp3.Response;

public class PushSetting extends AppCompatActivity implements View.OnClickListener {

    private SwitchButton employees_manager_switchbutton;
    private SwitchButton push_setting_switchbutton_fangge;
    private SwitchButton push_setting_switchbutton_sound;
    private SwitchButton push_setting_switchbutton_jarrying;
    private TextView push_setting_order_onoff;
    private TextView sale_consultant_Preservation;

    private SharedPreferences preferences;
    private final static String FILE_NAME = "login_file";

    private Dialog dialog;
    private View inflate;
    private TextView sex_women;
    private TextView sex_man;
    private TextView sex_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.push_setting_toobar);
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

        sale_consultant_Preservation = (TextView)findViewById(R.id.sale_consultant_Preservation);
        sale_consultant_Preservation.setOnClickListener(this);
        push_setting_order_onoff = (TextView)findViewById(R.id.push_setting_order_onoff);

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
        if (result.getMarketEntity() == null)
            return;
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
                if(str[0].equals("0")){
                    push_setting_order_onoff.setText("未开启");
                }else {
                    push_setting_order_onoff.setText("已开启");
                }
                if (str.length >= 2 && str[1].equals("0")) {
                    employees_manager_switchbutton.setChecked(false);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    employees_manager_switchbutton.setChecked(true);
                    employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 3 && str[2].equals("0")) {
                    push_setting_switchbutton_fangge.setChecked(false);
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    push_setting_switchbutton_fangge.setChecked(true);
                    push_setting_switchbutton_fangge.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 4 && str[3].equals("0")) {
                    push_setting_switchbutton_sound.setChecked(false);
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                } else {
                    push_setting_switchbutton_sound.setChecked(true);
                    push_setting_switchbutton_sound.setBackColor(ColorStateList.valueOf(Color.RED));
                }
                if (str.length >= 5 && str[4].equals("0")) {
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

    public void employee_add_sexchange(View view){
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_menu_sexchange, null);
        //初始化控件
        sex_women = (TextView) inflate.findViewById(R.id.sex_women);
        sex_women.setText("未开启");
        sex_man = (TextView) inflate.findViewById(R.id.sex_man);
        sex_man.setText("已开启");
        sex_cancel = (TextView) inflate.findViewById(R.id.sex_cancel);
        sex_women.setOnClickListener(this);
        sex_man.setOnClickListener(this);
        sex_cancel.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_women:
                dialog.dismiss();
                push_setting_order_onoff.setText("未开启");
                break;
            case R.id.sex_man:
                dialog.dismiss();
                push_setting_order_onoff.setText("已开启");
                break;
            case R.id.sex_cancel:
                dialog.dismiss();
                break;
            case R.id.sale_consultant_Preservation:
                String str = "";
                if(push_setting_order_onoff.getText().equals("未开启")){
                    str += "0";
                }else {
                    str += "1";
                }
                if(employees_manager_switchbutton.isChecked()){
                    str += "-1";
                }else {
                    str += "-0";
                }
                if(push_setting_switchbutton_fangge.isChecked()){
                    str += "-1";
                }else {
                    str += "-0";
                }
                if(push_setting_switchbutton_sound.isChecked()){
                    str += "-1";
                }else {
                    str += "-0";
                }
                if(push_setting_switchbutton_jarrying.isChecked()){
                    str += "-1";
                }else {
                    str += "-0";
                }

                try{
                    OkHttpUtils.get()
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/userAppSwitchUpdate.do")
                            .addParams("switchConfig",str)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response, int i) throws Exception {
                                    String string = response.body().string();//获取相应中的内容Json格式
                                    //把json转化成对应对象
                                    //LoginResultModel是和后台返回值类型结构一样的对象
                                    LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                    return result;
                                }

                                @Override
                                public void onError(Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(Object o, int i) {
                                    LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                    if (result.getOperationResult().getResultCode() == 0) {//当返回值为0
                                        Toast.makeText(PushSetting.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(PushSetting.this, "更新失败！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(PushSetting.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
}
