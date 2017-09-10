package lvge.com.myapp.modules.shop_management;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;

import java.io.File;

import lvge.com.myapp.R;

/**
 * Created by cnhao on 2017/8/27.
 */

public class ShopTakePhoto implements View.OnClickListener{
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;


    private Uri fileUri = null;
    private CropOptions cropOptions;
    Context ctx;

    public ShopTakePhoto(Context ctx){
        this.ctx = ctx;

        dialog = new Dialog(ctx, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(ctx).inflate(R.layout.layout_menu_shop_manage_img_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.from_phone_photo);
        takePhoto = (TextView) inflate.findViewById(R.id.take_photo);
        cancelPhoto = (TextView) inflate.findViewById(R.id.cancel);

        choosePhoto.setOnClickListener((View.OnClickListener) ctx);
        takePhoto.setOnClickListener((View.OnClickListener) ctx);
        cancelPhoto.setOnClickListener((View.OnClickListener) ctx);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_phone_photo:
//                fileUri = getImageCropUri();
//                getTakePhoto().onPickFromGalleryWithCrop(fileUri,cropOptions);
                Toast.makeText(ctx, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                break;
            case R.id.take_photo:
                //fileUri = getImageCropUri();
                //getTakePhoto().onPickFromCaptureWithCrop(fileUri,cropOptions);
                Toast.makeText(ctx, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            //finish();
        }
        dialog.dismiss();
    }

    private Uri getImageCropUri(){
        File file = new File(Environment.getExternalStorageDirectory(),"/temp/" + System.currentTimeMillis() + ".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();

        return Uri.fromFile(file);
    }
}
