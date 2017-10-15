package lvge.com.myapp.modules.commodity_management;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import lvge.com.myapp.R;

public class CommodityNewgoodsGraphicDescription extends AppCompatActivity implements View.OnClickListener, TakePhoto.TakeResultListener,InvokeListener {

    private TextView commodity_newgoods_Graphic_description_finish;
    private ImageView commodity_newgoods_graphic_decription_imageview;

    private Dialog dialog;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Uri fileUri = null;
    private CropOptions cropOptions;
    private TakePhoto takePhoto_ta;
    private InvokeParam invokeParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_newgoods_graphic_description);

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        commodity_newgoods_graphic_decription_imageview = (ImageView)findViewById(R.id.commodity_newgoods_graphic_decription_imageview);
        commodity_newgoods_Graphic_description_finish = (TextView)findViewById(R.id.commodity_newgoods_Graphic_description_finish);
        commodity_newgoods_Graphic_description_finish.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_newgoods_Graphic_description);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.commodity_newgoods_Graphic_description_finish:
                Intent intent = new Intent();
                intent.putExtra("graphicDescription",fileUri.toString());
                setResult(16,intent);
                finish();
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,requestCode,data);
    }

    public void show(View view) {
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

    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto_ta == null){
            takePhoto_ta = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto_ta;
    }

    protected void onSaveInstanceState(Bundle outState){
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,int[] grantResultsnts){
        super.onRequestPermissionsResult(requestCode,permissions,grantResultsnts);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResultsnts);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    private Uri getImageCropUri(){
        File file = new File(Environment.getExternalStorageDirectory(),"/temp/" + System.currentTimeMillis() + ".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();

        return Uri.fromFile(file);
    }

    @Override
    public void takeSuccess(TResult result) {
        try {
           /* String strPath = result.getImage().getCompressPath();*/
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if (bitmap != null) {
                commodity_newgoods_graphic_decription_imageview.setImageBitmap(bitmap);
            }
            dialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }
}
