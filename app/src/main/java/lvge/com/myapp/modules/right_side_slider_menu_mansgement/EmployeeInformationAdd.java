package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.my_4s_management.BaseTest;
import lvge.com.myapp.modules.my_4s_management.SaleConsultantTwo;
import okhttp3.Call;
import okhttp3.Response;

public class EmployeeInformationAdd extends AppCompatActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener{

    private Dialog dialog;
    private View inflate;
    private TextView sex_women;
    private TextView sex_man;
    private TextView sex_cancel;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Button choose_dialog_abandon;
    private Button choose_dialog_continue;

    private Uri fileUri = null;
    private TakePhoto takePhoto_ta;
    private InvokeParam invokeParam;
    private CropOptions cropOptions;

    private TextView employee_information_add_inputsex;
    private TextView employee_information_add_name;
    private ImageView employee_information_add_iamgeview;
    private TextView employee_information_add_inputemploy;
    private TextView employee_information_add_complete;
    private EditText employee_information_add_addname;
    private EditText employee_information_add_password;
    private EditText employee_information_add_addpasswordagain;

    private Bitmap bitmap;

    private CustomProgressDialog progressDialog = null;
    private boolean isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_information_add);

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        employee_information_add_inputsex = (TextView)findViewById(R.id.employee_information_add_inputsex);
        employee_information_add_name = (TextView)findViewById(R.id.employee_information_add_name);
        employee_information_add_iamgeview = (ImageView)findViewById(R.id.employee_information_add_iamgeview);
        employee_information_add_inputemploy = (TextView)findViewById(R.id.employee_information_add_inputemploy);
        employee_information_add_complete = (TextView)findViewById(R.id.employee_information_add_complete);
        employee_information_add_addname = (EditText)findViewById(R.id.employee_information_add_addname);
        employee_information_add_password = (EditText)findViewById(R.id.employee_information_add_password);
        employee_information_add_addpasswordagain = (EditText)findViewById(R.id.employee_information_add_addpasswordagain);

        employee_information_add_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee_information_add_iamgeview.setDrawingCacheEnabled(true);
                bitmap = employee_information_add_iamgeview.getDrawingCache();


                new Thread() {
                    public void run() {
                        try {
                            startProgerssDialog();
                            post_str(employee_information_add_addname.getText().toString(), employee_information_add_password.getText().toString(), employee_information_add_addpasswordagain.getText().toString(),employee_information_add_name.getText().toString(),employee_information_add_inputsex.getText().toString(),employee_information_add_inputemploy.getText().toString());
                        } catch ( Exception e) {
                            e.printStackTrace();
                            stopProgressDialog();
                        }
                    }
                }.start();

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_employee_information_add);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                if(isSave){
                    finish();
                }else {
                    choose_dialog();
                }
            }
        });

    }

    public void employee_add_post(View view){  //岗位
        try{
            Intent intent = new Intent(EmployeeInformationAdd.this, EmployeeInformationAddPost.class);
            startActivityForResult(intent,11);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void employee_add_name(View view){
        Intent intent = new Intent(EmployeeInformationAdd.this, EmployeeInformationAddName.class);
        startActivityForResult(intent, 10);
    }
    public void choose_dialog(){

        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.choose_dialog, null);
        choose_dialog_abandon = (Button)inflate.findViewById(R.id.choose_dialog_abadan);
        choose_dialog_abandon.setOnClickListener(this);
        choose_dialog_continue = (Button)inflate.findViewById(R.id.choose_dialog_continue);
        choose_dialog_continue.setOnClickListener(this);
        //初始化控件
     /*   sex_women = (TextView) inflate.findViewById(R.id.sex_women);
        sex_man = (TextView) inflate.findViewById(R.id.sex_man);
        sex_cancel = (TextView) inflate.findViewById(R.id.sex_cancel);
        sex_women.setOnClickListener(this);
        sex_man.setOnClickListener(this);
        sex_cancel.setOnClickListener(this);*/
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
       dialogWindow.setGravity(Gravity.CENTER);
/*
        dialogWindow.setGravity(Gravity.BOTTOM);
*/
        //获得窗体的属性
       /* WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);*/
        dialog.show();//显示对话框


/*

        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(this).inflate(R.layout.choose_dialog,null);
        choose_dialog_abandon = (Button) inflate.findViewById(R.id.choose_dialog_abandon);
        choose_dialog_continue =  (Button) inflate.findViewById(R.id.choose_dialog_continue);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
       WindowManager.LayoutParams lp = dialogWindow.getAttributes();
       lp.x = 10;
        dialogWindow.setAttributes(lp);
        dialog.show();*/
    }
    public void employee_add_sexchange(View view){
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_menu_sexchange, null);
        //初始化控件
        sex_women = (TextView) inflate.findViewById(R.id.sex_women);
        sex_man = (TextView) inflate.findViewById(R.id.sex_man);
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

    public void employee_add_Imageview(View view){
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_menu_shop_manage_img_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.from_phone_photo);
        takePhoto = (TextView) inflate.findViewById(R.id.take_photo);
        cancelPhoto = (TextView) inflate.findViewById(R.id.cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancelPhoto.setOnClickListener(this);
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
                employee_information_add_inputsex.setText("女");
                break;
            case R.id.sex_man:
                dialog.dismiss();
                employee_information_add_inputsex.setText("男");
                break;
            case R.id.sex_cancel:
                dialog.dismiss();
                break;
            case R.id.from_phone_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromGalleryWithCrop(fileUri,cropOptions);
                break;
            case R.id.take_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromCaptureWithCrop(fileUri,cropOptions);
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
           case R.id.choose_dialog_abadan:
                dialog.dismiss();
                finish();
                break;
            case R.id.choose_dialog_continue:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    private Uri getImageCropUri(){
        File file = new File(Environment.getExternalStorageDirectory(),"/temp/" + System.currentTimeMillis() + ".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();

        return Uri.fromFile(file);
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto_ta == null){
            takePhoto_ta = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto_ta;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (10 == requestCode && data != null) {
            String inputname = data.getStringExtra("inputname");
            employee_information_add_name.setText(inputname);
        }else if(11 == requestCode && data != null){
            String inputpost = data.getStringExtra("inputpost");
            employee_information_add_inputemploy.setText(inputpost);
        }
        if (null == dialog) {
        } else {
            dialog.dismiss();
        }

        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,requestCode,data);
    }

    @Override
    public void takeSuccess(TResult result) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
                employee_information_add_iamgeview.setImageBitmap(bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,int[] grantResultsnts){
        super.onRequestPermissionsResult(requestCode,permissions,grantResultsnts);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResultsnts);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    protected void onSaveInstanceState(Bundle outState){
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void startProgerssDialog(){
        if(progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            // progressDialog.setMessage("正在加载中。。");
        }
        progressDialog.show();
    }

    private void stopProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private String saveBitmap() throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();

        // Bitmap bitm = convertViewToBitMap(sale_consultant_two_iamgeview);
        String strPath = Environment.getExternalStorageDirectory().toString() + "/save";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);

        if(baos.toByteArray().length/1024 >500 ){
            int option = 90;
            while (baos.toByteArray().length/1024 >500){
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.PNG,option,baos);
                option -= 10;
            }
            ByteArrayInputStream isbm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isbm,null,null);

            isbm.close();
        }
        baos.close();

        try {
            File desDir = new File(strPath);
            if (!desDir.exists()) {
                desDir.mkdir();
            }

            File imageFile = new File(strPath + "/1.PNG");
            if(imageFile.exists()){
                imageFile.delete();
            }
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strPath + "/1.PNG";
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //System.out.println("按下了back键 onKeyDown()");
            if(isSave){
                finish();
            }else {
                choose_dialog();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void post_str(String username, String password, String confirmPassword,String name,String sex,String job) {

        try {
            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/user/saveStaff.do";
            String str_sex = "";
            List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap());

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", username);
            map.put("password", password);
            map.put("confirmPassword", confirmPassword);
            map.put("name", name);
            if(sex.equals("男")){
                str_sex = "1";
            }else {
                str_sex = "0";
            }
            map.put("sex", str_sex);
            map.put("job", job);

            BaseTest bs = new BaseTest();
            String str = bs.imgPut(path, filePaths, map);
            returnMessage(str);

        } catch (Exception e) {
            e.printStackTrace();
            returnMessage("error");
        }
    }

    private void returnMessage(String string) {
        Message msg = new Message();
        if(string.equals("error")){
            msg.what = 1;
            mHander.sendMessage(msg);
            return;
        }

        LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);

        if(result.getOperationResult().getResultCode() == 0){

            msg.what = 0;
            mHander.sendMessage(msg);
        }else {
            msg.what = 1;
            mHander.sendMessage(msg);
        }
        // dissmissProgressDialog();
    }

    Handler mHander = new Handler() {
        public void handleMessage(Message msg){
            stopProgressDialog();
            employee_information_add_iamgeview.setDrawingCacheEnabled(false);
            switch (msg.what){
                case 0:
                    Toast.makeText(EmployeeInformationAdd.this,"上传成功！",Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(SaleConsultantTwo.this, SalesConsultant.class);
                    // startActivity(intent);
                    finish();
                    break;
                case 1:
                    Toast.makeText(EmployeeInformationAdd.this,"上传失败！",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };
}
