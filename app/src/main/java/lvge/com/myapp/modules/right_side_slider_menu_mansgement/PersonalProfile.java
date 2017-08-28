package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.FileNotFoundException;

import lvge.com.myapp.R;

public class PersonalProfile extends AppCompatActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener{

    private Dialog dialog;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;

    private TextView sex_women;
    private TextView sex_man;
    private TextView sex_cancel;

    private Uri fileUri = null;
    private TakePhoto takePhoto_ta;
    private InvokeParam invokeParam;
    private CropOptions cropOptions;

    private ImageView persion_profile_iamgeview;
    private TextView persion_profile_inputname;
    private RelativeLayout persion_profile_Relayout_name;
    private RelativeLayout persion_profile_Relayout_changePassword;
    private TextView persion_profile_inputsex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        persion_profile_iamgeview = (ImageView)findViewById(R.id.persion_profile_iamgeview);
        persion_profile_inputname = (TextView)findViewById(R.id.persion_profile_inputname);
        persion_profile_inputsex = (TextView)findViewById(R.id.persion_profile_inputsex);
        persion_profile_Relayout_name = (RelativeLayout)findViewById(R.id.persion_profile_Relayout_name);
        persion_profile_Relayout_changePassword = (RelativeLayout)findViewById(R.id.persion_profile_Relayout_changePassword);

        persion_profile_Relayout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalProfile.this, PersionProfileName.class);
                String inputname = persion_profile_inputname.getText().toString();
                intent.putExtra("inputname", inputname);
                startActivityForResult(intent, 10);
            }
        });

        persion_profile_Relayout_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalProfile.this,PersionProfileChangePassword.class);
                startActivity(intent);
            }
        });
    }

    public void profile_sexchange(View view){
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

    public void profile_Imageview(View view){
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
                persion_profile_inputsex.setText("女");
                break;
            case R.id.sex_man:
                dialog.dismiss();
                persion_profile_inputsex.setText("男");
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
            case R.id.cancel:
                dialog.dismiss();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (10 == requestCode && data != null) {
            String inputname = data.getStringExtra("inputname");
            persion_profile_inputname.setText(inputname);
        }
        if (null == dialog) {
        } else {
            dialog.dismiss();
        }

        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,requestCode,data);
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

    @Override
    public void takeSuccess(TResult result) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
                persion_profile_iamgeview.setImageBitmap(bitmap);
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
}