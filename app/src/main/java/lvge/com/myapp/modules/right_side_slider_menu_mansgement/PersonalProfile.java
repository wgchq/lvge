package lvge.com.myapp.modules.right_side_slider_menu_mansgement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.zhy.http.okhttp.callback.BitmapCallback;
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
import java.util.concurrent.ExecutionException;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.ProgressDialog.RoundImageView;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoadRightSideMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.my_4s_management.BaseTest;
import lvge.com.myapp.modules.my_4s_management.SaleConsultantTwo;
import okhttp3.Call;
import okhttp3.Response;

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
    private TextView sale_consultant_Preservation;

    private SharedPreferences preferences;
    private final static String FILE_NAME = "login_file";

    private String password = "";
    private String confirmPassword = "";
    private Bitmap bitmap;

    private CustomProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.persion_profile_toobar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String string = preferences.getString("right_data","");
        LoadRightSideMode result = new Gson().fromJson(string, LoadRightSideMode.class);

        persion_profile_iamgeview = (ImageView)findViewById(R.id.persion_profile_iamgeview);
        persion_profile_inputname = (TextView)findViewById(R.id.persion_profile_inputname);
        if(!result.getMarketEntity().getSeller().getName().equals("")){
            persion_profile_inputname.setText(result.getMarketEntity().getSeller().getName());
        }
        persion_profile_inputsex = (TextView)findViewById(R.id.persion_profile_inputsex);

        if(result.getMarketEntity().getHeadImg() != null && result.getMarketEntity().getHeadImg() != ""){
            OkHttpUtils.get()
                    .url(result.getMarketEntity().getHeadImg())
                    .build()
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Bitmap bitm, int i) {
                            persion_profile_iamgeview.setImageBitmap(bitm);
                        }
                    });
        }

        if(result.getMarketEntity().getSex() == 0){
            persion_profile_inputsex.setText("男");
        }else {
            persion_profile_inputsex.setText("女");
        }

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
                startActivityForResult(intent, 11);   //修改密码
            }
        });

        sale_consultant_Preservation = (TextView)findViewById(R.id.sale_consultant_Preservation);
        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persion_profile_iamgeview.setDrawingCacheEnabled(true);
                bitmap = persion_profile_iamgeview.getDrawingCache();
                startProgerssDialog();
                String sex = "";
                if(persion_profile_inputsex.getText().toString().equals("男")){
                    sex = "1";
                }else {
                    sex = "0";
                }

                try{
                    OkHttpUtils.get()
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/update.do")
                            .addParams("password",password)
                            .addParams("confirmPassword",confirmPassword)
                            .addParams("name",persion_profile_inputname.getText().toString())
                            .addParams("sex",sex)
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
                                    if (null != o) {
                                        LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                        if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                            try{
                                                //List<String> filePaths = new ArrayList<>();
                                                //filePaths.add(saveBitmap());
                                                OkHttpUtils.post()
                                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/user/updateImage")
                                                        .addFile("photo","1.png",new File(saveBitmap()))
                                                        .build()
                                                        .execute(new Callback() {
                                                            @Override
                                                            public Object parseNetworkResponse(Response response, int i) throws Exception {
                                                                String string = response.body().string();//获取相应中的内容Json格式
                                                                //把json转化成对应对象
                                                                //LoginResultModel是和后台返回值类型结构一样的对象
                                                                stopProgressDialog();
                                                                LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                                                return result;
                                                            }

                                                            @Override
                                                            public void onError(Call call, Exception e, int i) {

                                                            }

                                                            @Override
                                                            public void onResponse(Object o, int i) {
                                                                if (null != o) {
                                                                    LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                                                    if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                                                        Toast.makeText(PersonalProfile.this,"上传成功！",Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                            }
                                                        });
                                            }catch (Exception e){
                                                Toast.makeText(PersonalProfile.this,"上传失败！",Toast.LENGTH_LONG).show();
                                            }
                                        /**
                                            new Thread() {
                                                public void run() {
                                                    try {
                                                        // showProgressDialog();
                                                        post_str();
                                                    } catch ( Exception e) {
                                                        e.printStackTrace();
                                                        stopProgressDialog();
                                                    }
                                                }
                                            }.start();
                                         **/
                                        }
                                    }
                                }
                            });
                }catch (Exception e){
                    stopProgressDialog();
                    Toast.makeText(PersonalProfile.this,"上传失败！",Toast.LENGTH_LONG).show();
                }

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
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (10 == requestCode && data != null) {
            String inputname = data.getStringExtra("inputname");
            persion_profile_inputname.setText(inputname);
        }else if(11 == requestCode && data != null){
            password = data.getStringExtra("password");
            confirmPassword = data.getStringExtra("confirmPassword");
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
    private void post_str() {

        try {
            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/user/updateImage";

            List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap());

            Map<String, Object> map = new HashMap<String, Object>();


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
            persion_profile_iamgeview.setDrawingCacheEnabled(false);
            switch (msg.what){
                case 0:
                    Toast.makeText(PersonalProfile.this,"上传成功！",Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(SaleConsultantTwo.this, SalesConsultant.class);
                    // startActivity(intent);
                    finish();
                    break;
                case 1:
                    Toast.makeText(PersonalProfile.this,"上传失败！",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };

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
}
