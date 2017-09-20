package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.ProgressDialog.PickerScrollView;
import lvge.com.myapp.ProgressDialog.Pickers;
import lvge.com.myapp.R;
import lvge.com.myapp.model.EmployeeInformationMode;
import lvge.com.myapp.model.LoadRightSideMode;
import okhttp3.Call;
import okhttp3.Response;

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
    private String[] appRight;

    private String[] powerManal = new String[12]; //获取员工权限列表
    private String UserID = "";
    private String  appRigh = "";

    private int selectID;

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
                for(int i = 0;i<name.length;i++){
                    if(pick_name.equals(name[i])){
                        selectID = i;
                        break;
                    }
                }
                UserID = id[selectID];
                initswitch(appRight[selectID]);
            }
        });
        power_setting_choosename_Relayout = (RelativeLayout)findViewById(R.id.power_setting_choosename_Relayout);
        power_setting_choosename_Relayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker_rel.setVisibility(View.VISIBLE);
            }
        });
        initEmployeeInformation();

        list = new ArrayList<Pickers>();

        sale_consultant_Preservation = (TextView)findViewById(R.id.sale_consultant_Preservation);
        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean a =    employees_manager_switchbutton.isChecked();  获取状态
                updata();
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

        employees_manager_switchbutton.setChecked(false);
        employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        shop_manage_switchbutton.setChecked(false);
        shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        merchandise_manager_switchbutton.setChecked(false);
        merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        order_manager_switchbutton.setChecked(false);
        order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        appraise_manager_switchbutton.setChecked(false);
        appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        commission_manager_switchbutton.setChecked(false);
        commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        finance_manager_switchbutton.setChecked(false);
        finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        performance_manager_switchbutton.setChecked(false);
        performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));


        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String string = preferences.getString("right_data","");
        LoadRightSideMode result = new Gson().fromJson(string, LoadRightSideMode.class);

        power = result.getMarketEntity().getAppRights();
        initswitch(power);
        // performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
    }

    private void updata(){
        if(UserID.equals("")){
            return;
        }
        if(employees_manager_switchbutton.isChecked()){
            powerManal[0] = "1";
        }else {
            powerManal[0] = "0";
        }
        if(order_manager_switchbutton.isChecked()){
            powerManal[1] = "2";
        }else {
            powerManal[1] = "0";
        }
        if(shop_manage_switchbutton.isChecked()){
            powerManal[2] = "3";
        }else {
            powerManal[2] = "0";
        }
        if(merchandise_manager_switchbutton.isChecked()){
            powerManal[3] = "4";
        }else {
            powerManal[3] = "0";
        }
        if(finance_manager_switchbutton.isChecked()){
            powerManal[4] = "5";
        }else {
            powerManal[4] = "0";
        }
        if(commission_manager_switchbutton.isChecked()){
            powerManal[6] = "7";
        }else {
            powerManal[6] = "0";
        }
        if(appraise_manager_switchbutton.isChecked()){
            powerManal[7] = "8";
        }else {
            powerManal[7] = "0";
        }
        if(performance_manager_switchbutton.isChecked()){
            powerManal[10] = "11";
        }else {
            powerManal[10] = "0";
        }


        for(int i=0;i<powerManal.length;i++){
            if(powerManal[i] != "0" && powerManal[i] != null){
                appRigh = appRigh + "-" + powerManal[i];
            }
        }

        if(appRigh != null){
            if(appRigh.substring(0,1).equals("-")){
                appRigh = appRigh.substring(1,appRigh.length());
            }
        }

        try{
            OkHttpUtils.get()
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/userAppRightsUpdate.do")
                    .addParams("appRights",appRigh)
                    .addParams("userId",UserID)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            //stopProgressDialog();
                            EmployeeInformationMode result = new Gson().fromJson(string, EmployeeInformationMode.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                EmployeeInformationMode result = (EmployeeInformationMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                    Toast.makeText(PowerSetting.this,"更新成功！",Toast.LENGTH_LONG).show();
                                    for(int j = 0;j<id.length;j++){
                                        if(UserID.equals(id[j])){
                                            appRight[j] = appRigh;
                                            break;
                                        }
                                    }
                                }else {
                                    Toast.makeText(PowerSetting.this,"更新失败！",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });

        }catch (Exception e){
            Toast.makeText(PowerSetting.this,"网络异常！",Toast.LENGTH_LONG).show();
        }
    }

    private void initswitch(String appRight){

        employees_manager_switchbutton.setChecked(false);
        employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        shop_manage_switchbutton.setChecked(false);
        shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        merchandise_manager_switchbutton.setChecked(false);
        merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        order_manager_switchbutton.setChecked(false);
        order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        appraise_manager_switchbutton.setChecked(false);
        appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        commission_manager_switchbutton.setChecked(false);
        commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        finance_manager_switchbutton.setChecked(false);
        finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        performance_manager_switchbutton.setChecked(false);
        performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
        if(appRight.equals("")){
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
            String[] str = appRight.split("-");

            for(int i=0;i<str.length;i++){
                if(Integer.valueOf(str[i]) <= 12  && 1 <= Integer.valueOf(str[i])){
                    powerManal[Integer.valueOf(str[i]) - 1] = str[i];
                }
                switch (str[i]){
                    case "1":
                        employees_manager_switchbutton.setChecked(true);
                        employees_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "2":
                        order_manager_switchbutton.setChecked(true);
                        order_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "3":
                        shop_manage_switchbutton.setChecked(true);
                        shop_manage_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "4":
                        merchandise_manager_switchbutton.setChecked(true);
                        merchandise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "5":
                        finance_manager_switchbutton.setChecked(true);
                        finance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "7":
                        commission_manager_switchbutton.setChecked(true);
                        commission_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "8":
                        appraise_manager_switchbutton.setChecked(true);
                        appraise_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    case "11":
                        performance_manager_switchbutton.setChecked(true);
                        performance_manager_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                        break;
                    default:
                        break;
                }
            }

        }
    }

    private void initEmployeeInformation() {
        try {
            OkHttpUtils.get()
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/queryStaff.do")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            //stopProgressDialog();
                            EmployeeInformationMode result = new Gson().fromJson(string, EmployeeInformationMode.class);
                            return result;
                        }

                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object o, int i) {
                            if (null != o) {
                                EmployeeInformationMode result = (EmployeeInformationMode) o;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                    int length = result.getMarketEntity().size();
                                    id=new String[length];
                                    name = new String[length];
                                    appRight = new String[length];

                                    for(int j=0;j<length;j++){
                                        id[j] = result.getMarketEntity().get(j).getUSER_ID();
                                        name[j] = result.getMarketEntity().get(j).getNAME();
                                        appRight[j] = result.getMarketEntity().get(j).getAppRights();
                                        list.add(new Pickers(name[j],id[j]));
                                    }
                                    // 设置数据，默认选择第一条  
                                    pickerscrlllview.setData(list);
                                    pickerscrlllview.setSelected(0);
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            // stopProgressDialog();
            Toast.makeText(PowerSetting.this,"获取失败！",Toast.LENGTH_LONG).show();
        }
    }
}
