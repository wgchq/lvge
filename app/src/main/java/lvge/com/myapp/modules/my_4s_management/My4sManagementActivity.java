
package lvge.com.myapp.modules.my_4s_management;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.model.Text;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.shop_management.ShopManageShopImgActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;
import okhttp3.Call;
import okhttp3.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class My4sManagementActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    My4sManagementActivityGson result;
    private static final String LOG_TAG = "HelloCamera";
    private String id;
    private String lat;
    private String lng;
    private String address;

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_management);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_4s_management);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(My4sManagementActivity.this, MainPageActivity.class);
                // startActivity(intent);
                finish();
            }
        });

        // ImageView my4s_manage_back = (ImageView) findViewById(R.id.my4s_management_back);   //返回图片
        TextView my4s_manage_finish = (TextView) findViewById(R.id.my4s_finish);
        final TextView commodity_my4s_sales_consultant = (TextView) findViewById(R.id.commodity_my4s_sales_consultant);
        final TextView commodity_my4s_address = (TextView) findViewById(R.id.commodity_my4s_address);
        final EditText commodity_my4s_setting_inputnumber = (EditText) findViewById(R.id.commodity_my4s_setting_inputnumber);
        final EditText commodity_my4s_setting_inputsosnumber = (EditText) findViewById(R.id.commodity_my4s_setting_inputsosnumber);
        final EditText commodity_my4s_setting_inputInsurancenumber = (EditText) findViewById(R.id.commodity_my4s_setting_inputInsurancenumber);
        RelativeLayout my4s_management_to_salesconsultant = (RelativeLayout) findViewById(R.id.my4s_management_to_salesconsultant);
        LinearLayout my4s_management_to_address = (LinearLayout) findViewById(R.id.my4s_management_to_address);

        try {

            OkHttpUtils.get()
                    //get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/detail") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            result = new Gson().fromJson(string, My4sManagementActivityGson.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                result = (My4sManagementActivityGson) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    id = String.valueOf(result.getMarketEntity().getId());
                                    lat = String.valueOf(result.getMarketEntity().getLat());
                                    lng = String.valueOf(result.getMarketEntity().getLng());
                                    address = String.valueOf(result.getMarketEntity().getAddress());

                                    String salerNum = "共有" + result.getMarketEntity().getSalerNum() + "人";
                                    commodity_my4s_sales_consultant.setText(String.valueOf(salerNum));
                                    commodity_my4s_address.setText(String.valueOf(result.getMarketEntity().getAddress()));
                                    commodity_my4s_setting_inputnumber.setText(String.valueOf(result.getMarketEntity().getServerPhone()));
                                    commodity_my4s_setting_inputsosnumber.setText(String.valueOf(result.getMarketEntity().getAssistPhone()));
                                    commodity_my4s_setting_inputInsurancenumber.setText(String.valueOf(result.getMarketEntity().getNotifyDangerPhone()));
                                } else {
                                    // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    // startActivity(intent);
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }


        my4s_manage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OkHttpUtils.post()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/update") //地址
                            .addParams("id", id)
                            .addParams("lat", lat)
                            .addParams("lng", lng)
                            .addParams("address", address)
                            .addParams("serverPhone", commodity_my4s_setting_inputnumber.getText().toString())
                            .addParams("assistPhone", commodity_my4s_setting_inputsosnumber.getText().toString())
                            .addParams("notifyDangerPhone", commodity_my4s_setting_inputInsurancenumber.getText().toString())
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
                                            Toast.makeText(My4sManagementActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(My4sManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        my4s_management_to_salesconsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, SalesConsultant.class);
                startActivity(intent);
            }
        });

        my4s_management_to_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, My4sAddressActivity.class);

                Bundle bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("id", id);
                bundle.putString("lng", lng);
                bundle.putString("lat", lat);
                bundle.putString("address", address);
                bundle.putString("serverPhone", commodity_my4s_setting_inputnumber.getText().toString());
                bundle.putString("assistPhone", commodity_my4s_setting_inputsosnumber.getText().toString());
                bundle.putString("notifyDangerPhone", commodity_my4s_setting_inputInsurancenumber.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.from_phone_photo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.take_photo:
                Intent take_photo_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // create a file to save the image
                Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
                // set the image file name
                take_photo_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
             try
             {
                 startActivityForResult(take_photo_intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }

                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
        }
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");

            Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                Log.d(LOG_TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
