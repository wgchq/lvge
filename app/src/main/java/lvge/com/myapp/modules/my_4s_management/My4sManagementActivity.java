
package lvge.com.myapp.modules.my_4s_management;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.My4sUpdataImageViewModel;
import lvge.com.myapp.modules.shop_management.ShopManageShopImgActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;
import okhttp3.Call;
import okhttp3.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class My4sManagementActivity extends AppCompatActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener{
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    My4sManagementActivityGson result;
    private static final String LOG_TAG = "HelloCamera";
    private String id="";
    private String lat="0";
    private String lng="0";
    private String address="";

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;
    private String path;

    private int currentIndex = 0;

    // private Uri imageUri = null;
    private Uri fileUri = null;

    private ImageView my_4s_shop_pic_1;
    private ImageView my_4s_shop_pic_2;
    private ImageView my_4s_shop_pic_3;

    private int id_iamge;

    private ProgressDialog progDialog = null;
    private boolean my_4s_pic_1_bool = false;
    private boolean my_4s_pic_2_bool = false;
    private boolean my_4s_pic_3_bool = false;

    private int my_4s_pic_1_id = 0;
    private int my_4s_pic_2_id = 0;
    private int my_4s_pic_3_id = 0;

    private Bitmap my_4s_1_bitmap;
    private Bitmap my_4s_2_bitmap;
    private Bitmap my_4s_3_bitmap;

    private int ImageNum = 0;

    private CustomProgressDialog progressDialog = null;
    private CropOptions cropOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my4s_management);


        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
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
        final TextView commodity_my4s_setting_inputnumber = (TextView) findViewById(R.id.commodity_my4s_setting_inputnumber);
        final TextView commodity_my4s_setting_inputsosnumber = (TextView) findViewById(R.id.commodity_my4s_setting_inputsosnumber);
        final TextView commodity_my4s_setting_inputInsurancenumber = (TextView) findViewById(R.id.commodity_my4s_setting_inputInsurancenumber);

        commodity_my4s_setting_inputnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, SalesConsultantPhoneActivity.class);
                String inputnumber = commodity_my4s_setting_inputnumber.getText().toString();
                intent.putExtra("inputnumber", inputnumber);
                startActivityForResult(intent, 6);


            }
        });

        commodity_my4s_setting_inputsosnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, SalesConsultantSosPhoneActivity.class);
                String sosnumber = commodity_my4s_setting_inputsosnumber.getText().toString();
                intent.putExtra("sosnumber", sosnumber);
                startActivityForResult(intent, 5);
            }
        });

        commodity_my4s_setting_inputInsurancenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My4sManagementActivity.this, SalesConsultantInsurancePhoneActivity.class);
                String inputInsurancenumber = commodity_my4s_setting_inputInsurancenumber.getText().toString();
                intent.putExtra("inputInsurancenumber", inputInsurancenumber);
                startActivityForResult(intent, 55);
            }
        });

        my_4s_shop_pic_1 = (ImageView) findViewById(R.id.my_4s_shop_pic_1);
        my_4s_shop_pic_2 = (ImageView) findViewById(R.id.my_4s_shop_pic_2);
        my_4s_shop_pic_3 = (ImageView) findViewById(R.id.my_4s_shop_pic_3);

        my_4s_shop_pic_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                builder.setIcon(R.mipmap.warming);
                builder.setTitle("删除");
                builder.setMessage("是否确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (my_4s_pic_1_id == 0) {
                            my_4s_shop_pic_1.setImageResource(R.mipmap.my_4s_shop_no_pic);
                        } else {
                            try {
                                OkHttpUtils.post()//get 方法
                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/sellerImgDelete") //地址
                                        .addParams("imgID", String.valueOf(my_4s_pic_1_id))
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
                                                        Toast.makeText(My4sManagementActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                                        my_4s_shop_pic_2.setDrawingCacheEnabled(true);
                                                        my_4s_shop_pic_1.setImageDrawable(my_4s_shop_pic_2.getDrawable());
                                                        my_4s_pic_1_id = my_4s_pic_2_id;
                                                        my_4s_shop_pic_3.setDrawingCacheEnabled(true);
                                                        my_4s_shop_pic_2.setImageDrawable(my_4s_shop_pic_3.getDrawable());
                                                        my_4s_pic_2_id = my_4s_pic_3_id;
                                                        my_4s_shop_pic_2.setDrawingCacheEnabled(false);
                                                        my_4s_shop_pic_3.setDrawingCacheEnabled(false);

                                                        my_4s_shop_pic_3.setImageResource(R.mipmap.my_4s_shop_no_pic);
                                                        my_4s_pic_3_id = 0;
                                                    } else {
                                                       // Toast.makeText(My4sManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                                        builder.setMessage("网络异常，请重新登陆");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
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

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

        my_4s_shop_pic_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                builder.setIcon(R.mipmap.warming);
                builder.setTitle("删除");
                builder.setMessage("是否确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (my_4s_pic_2_id == 0) {
                            my_4s_shop_pic_2.setImageResource(R.mipmap.my_4s_shop_no_pic);
                        } else {
                            try {
                                OkHttpUtils.post()//get 方法
                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/sellerImgDelete") //地址
                                        .addParams("imgID", String.valueOf(my_4s_pic_2_id))
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
                                                        Toast.makeText(My4sManagementActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                                        my_4s_shop_pic_3.setDrawingCacheEnabled(true);
                                                        my_4s_shop_pic_2.setImageDrawable(my_4s_shop_pic_3.getDrawable());
                                                        my_4s_pic_2_id = my_4s_pic_3_id;
                                                        my_4s_shop_pic_3.setDrawingCacheEnabled(false);

                                                        my_4s_shop_pic_3.setImageResource(R.mipmap.my_4s_shop_no_pic);
                                                        my_4s_pic_3_id = 0;
                                                    } else {
                                                        //Toast.makeText(My4sManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                                        builder.setMessage("网络异常，请重新登陆");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
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

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

        my_4s_shop_pic_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                builder.setIcon(R.mipmap.warming);
                builder.setTitle("删除");
                builder.setMessage("是否确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (my_4s_pic_3_id == 0) {
                            my_4s_shop_pic_3.setImageResource(R.mipmap.my_4s_shop_no_pic);
                        } else {
                            try {
                                OkHttpUtils.post()//get 方法
                                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/sellerImgDelete") //地址
                                        .addParams("imgID", String.valueOf(my_4s_pic_3_id))
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
                                                        Toast.makeText(My4sManagementActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                                        my_4s_shop_pic_3.setImageResource(R.mipmap.my_4s_shop_no_pic);
                                                        my_4s_pic_3_id = 0;
                                                    } else {
                                                       // Toast.makeText(My4sManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                                        builder.setMessage("网络异常，请重新登陆");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
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
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

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

                            e.printStackTrace();

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

                                    if (result.getMarketEntity().getImgVOs() != null) {
                                        int size = result.getMarketEntity().getImgVOs().size();
                                        for (int index = 0; index < size; index++) {
                                            path = result.getMarketEntity().getImgVOs().get(index).getPath();
                                            if (path == null || path == "")
                                                continue;
                                            if (my_4s_pic_1_id == 0) {
                                                my_4s_pic_1_id = result.getMarketEntity().getImgVOs().get(index).getId();
                                                OkHttpUtils.get()
                                                        .url(path)
                                                        .build()
                                                        .execute(new BitmapCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e, int i) {

                                                            }

                                                            @Override
                                                            public void onResponse(Bitmap bitmap, int i) {
                                                                    my_4s_shop_pic_1.setImageBitmap(bitmap);
                                                            }
                                                        });
                                            } else if (my_4s_pic_2_id == 0) {
                                                my_4s_pic_2_id = result.getMarketEntity().getImgVOs().get(index).getId();
                                                OkHttpUtils.get()
                                                        .url(path)
                                                        .build()
                                                        .execute(new BitmapCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e, int i) {

                                                            }

                                                            @Override
                                                            public void onResponse(Bitmap bitmap, int i) {
                                                                my_4s_shop_pic_2.setImageBitmap(bitmap);
                                                            }
                                                        });
                                            } else if (my_4s_pic_3_id == 0) {
                                                my_4s_pic_3_id = result.getMarketEntity().getImgVOs().get(index).getId();
                                                OkHttpUtils.get()
                                                        .url(path)
                                                        .build()
                                                        .execute(new BitmapCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e, int i) {

                                                            }

                                                            @Override
                                                            public void onResponse(Bitmap bitmap, int i) {
                                                                my_4s_shop_pic_3.setImageBitmap(bitmap);
                                                            }
                                                        });
                                            }
                                        }
                                    }

                                } else {
                                    // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                    // startActivity(intent);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                    builder.setMessage("网络异常，请重新登陆");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                builder.setMessage("网络异常，请重新登陆");
                                builder.setCancelable(false);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
            currentIndex = 0;
        } catch (Exception e) {
            Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
        my4s_manage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startProgerssDialog();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
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
                                            stopProgressDialog();
                                        }

                                        @Override
                                        public void onResponse(Object o, int i) {
                                            if (null != o) {
                                                LoginResultModel result = (LoginResultModel) o;//把通用的Object转化成指定的对象
                                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                                    Toast.makeText(My4sManagementActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                                } else {
                                                   // Toast.makeText(My4sManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(My4sManagementActivity.this);
                                                    builder.setMessage("网络异常，请重新登陆");
                                                    builder.setCancelable(false);
                                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(My4sManagementActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }
                                            } else {//当没有返回对象时，表示网络没有联通
                                                Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                            }
                                            stopProgressDialog();
                                        }
                                    });
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule(task, 2000);//3秒后执行



                } catch (Exception e) {
                    stopProgressDialog();
                    Toast.makeText(My4sManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }

                try {
                    if (my_4s_pic_1_bool || my_4s_pic_2_bool || my_4s_pic_3_bool)
                        startProgerssDialog();
                    if (my_4s_pic_1_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        my_4s_shop_pic_1.setDrawingCacheEnabled(true);
                        my_4s_1_bitmap = my_4s_shop_pic_1.getDrawingCache();
                        filePaths.add(saveBitmap("1", my_4s_1_bitmap));
                        if (my_4s_pic_1_id != 0) {
                            map.put("imgID", String.valueOf(my_4s_pic_1_id));
                        }
                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    post_str(filePaths, map);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        ImageNum++;
                        my_4s_pic_1_bool = false;
                    }
                    if (my_4s_pic_2_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        my_4s_shop_pic_2.setDrawingCacheEnabled(true);
                        my_4s_2_bitmap = my_4s_shop_pic_2.getDrawingCache();
                        filePaths.add(saveBitmap("2", my_4s_2_bitmap));
                        if (my_4s_pic_2_id != 0) {
                            map.put("imgID", String.valueOf(my_4s_pic_2_id));
                        }
                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    post_str(filePaths, map);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        my_4s_pic_2_bool = false;
                        ImageNum++;
                    }
                    if (my_4s_pic_3_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        my_4s_shop_pic_3.setDrawingCacheEnabled(true);
                        my_4s_3_bitmap = my_4s_shop_pic_3.getDrawingCache();
                        filePaths.add(saveBitmap("3", my_4s_3_bitmap));
                        if (my_4s_pic_3_id != 0) {
                            map.put("imgID", String.valueOf(my_4s_pic_3_id));
                        }
                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    post_str(filePaths, map);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        my_4s_pic_3_bool = false;
                        ImageNum++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
/*
                Intent intent = new Intent(My4sManagementActivity.this, My4sAddressActivity.class);
*/
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
        id_iamge = view.getId();
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
                /**
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                if(Build.VERSION.SDK_INT >= 24){
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                }else {
                    intent.setType("image/*");
                }
                startActivityForResult(intent, 1);
                break;
                 **/
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromGalleryWithCrop(fileUri,cropOptions);
                break;
            case R.id.take_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromCaptureWithCrop(fileUri,cropOptions);
            /**
                Intent take_photo_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // create a file to save the image
               // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                if(Build.VERSION.SDK_INT >= 24){
                    fileUri = FileProvider.getUriForFile(this,"lvge.com.myapp" + ".fileprovider",getOutputMediaFile(MEDIA_TYPE_IMAGE));
                }else {
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                }

                // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
                // set the image file name
                take_photo_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                try {
                    startActivityForResult(take_photo_intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                **/
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (5 == requestCode && data != null) {
            String edit = data.getStringExtra("edit").toString();
            if (edit.equals("t") ) {
                TextView sos_phone = (TextView) findViewById(R.id.commodity_my4s_setting_inputsosnumber);
                String sos_phone_number = data.getStringExtra("sosphone").toString();
                sos_phone.setText(sos_phone_number);
            }

        } else if (6 == requestCode && data != null) {
            String edit = data.getStringExtra("edit").toString();
            if (edit.equals("t"))
            {
                TextView phone_number = (TextView) findViewById(R.id.commodity_my4s_setting_inputnumber);
                String str_phone_number = data.getStringExtra("phone_number").toString();
                phone_number.setText(str_phone_number);
            }


        } else if (55 == requestCode && data != null) {

            String edit = data.getStringExtra("edit").toString();
            if (edit.equals("t"))
            {
                TextView insurance_phone_number = (TextView) findViewById(R.id.commodity_my4s_setting_inputInsurancenumber);
                String str_insurance_phone_number = data.getStringExtra("insurance_phone_number").toString();
                insurance_phone_number.setText(str_insurance_phone_number);
            }

        }

        if (null == dialog) {
        } else {
            dialog.dismiss();
        }

        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,requestCode,data);
        /**
        if (requestCode == 1 && data != null) {
            fileUri = data.getData();
            if(fileUri != null){
                crop(fileUri);
            }
        }

        /**
        if (fileUri != null) {
            try {
                String[] prjo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(fileUri, prjo, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    // file = new File(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));

                    if (bitmap != null) {
                        switch (id_iamge) {
                            case R.id.my_4s_shop_pic_1:
                                my_4s_shop_pic_1.setImageBitmap(bitmap);
                                my_4s_pic_1_bool = true;
                                break;
                            case R.id.my_4s_shop_pic_2:
                                my_4s_shop_pic_2.setImageBitmap(bitmap);
                                my_4s_pic_2_bool = true;
                                break;
                            case R.id.my_4s_shop_pic_3:
                                my_4s_shop_pic_3.setImageBitmap(bitmap);
                                my_4s_pic_3_bool = true;
                                break;
                            default:
                                break;
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

       else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && fileUri != null) {
            // imageUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));

                if (bitmap != null) {
                    crop(fileUri);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if(2 == requestCode) {
            Bitmap bitmap = data.getParcelableExtra("data");
            if (bitmap != null) {
                switch (id_iamge) {
                    case R.id.my_4s_shop_pic_1:
                        my_4s_shop_pic_1.setImageBitmap(bitmap);
                        my_4s_pic_1_bool = true;
                        break;
                    case R.id.my_4s_shop_pic_2:
                        my_4s_shop_pic_2.setImageBitmap(bitmap);
                        my_4s_pic_2_bool = true;
                        break;
                    case R.id.my_4s_shop_pic_3:
                        my_4s_shop_pic_3.setImageBitmap(bitmap);
                        my_4s_pic_3_bool = true;
                        break;
                    default:
                        break;
                }
            }
        }
         **/
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

    private void post_str(List<String> filePaths, Map<String, Object> map) {

        try {

            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/shop4S/sellerImgSave";

            BaseTest bs = new BaseTest();
            String str = bs.imgPut(path, filePaths, map);

            returnMessage(str);

        } catch (Exception e) {
            e.printStackTrace();
            returnMessage("error");
        }
    }


    private String saveBitmap(String name, Bitmap bitmap) throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();

        // Bitmap bitm = convertViewToBitMap(sale_consultant_two_iamgeview);
        String strPath = Environment.getExternalStorageDirectory().toString() + "/save";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        if (baos.toByteArray().length / 1024 > 500) {
            int option = 90;
            while (baos.toByteArray().length / 1024 > 500) {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.PNG, option, baos);
                option -= 10;
            }
            ByteArrayInputStream isbm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isbm, null, null);

            isbm.close();
        }
        baos.close();

        try {
            File desDir = new File(strPath);
            if (!desDir.exists()) {
                desDir.mkdir();
            }

            File imageFile = new File(strPath + "/" + name + ".PNG");
            if (imageFile.exists()) {
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
        return strPath + "/" + name + ".PNG";
    }

    private void returnMessage(String string) {
        Message msg = new Message();
        if (string.equals("error")) {
            msg.what = 1;
            mHander.sendMessage(msg);
            return;
        }

        My4sUpdataImageViewModel result = new Gson().fromJson(string, My4sUpdataImageViewModel.class);

        if (result.getOperationResult().getResultCode() == 0) {

            msg.what = 0;
            mHander.sendMessage(msg);
        } else {
            msg.what = 1;
            mHander.sendMessage(msg);
        }
        // dissmissProgressDialog();
    }

    Handler mHander = new Handler() {
        public void handleMessage(Message msg) {
            //dissmissProgressDialog();
            // my_4s_shop_pic_1.setDrawingCacheEnabled(false);
            // my_4s_shop_pic_2.setDrawingCacheEnabled(false);
            // my_4s_shop_pic_3.setDrawingCacheEnabled(false);
            switch (msg.what) {
                case 0:
                    ImageNum--;
                    if (ImageNum == 0) {
                        stopProgressDialog();
                        my_4s_shop_pic_1.setDrawingCacheEnabled(false);
                        my_4s_shop_pic_2.setDrawingCacheEnabled(false);
                        my_4s_shop_pic_3.setDrawingCacheEnabled(false);
                        Toast.makeText(My4sManagementActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 1:
                    stopProgressDialog();
                    Toast.makeText(My4sManagementActivity.this, "上传失败！", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };

    private void crop(Uri uri){

        Intent intent = new Intent("com.android.camera.action.CROP");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try{
                if(Build.VERSION.SDK_INT >= 24){
                    intent.setDataAndType(uri,"image/*");
                }else {
                    String path = getPath(this,uri);
                    intent.setDataAndType(Uri.fromFile(new File(path)),"image/*");
                }
            }catch (Exception e){
                e.toString();
            }

        }else {
            intent.setDataAndType(uri,"image/*");
        }

        intent.putExtra("crop","true");

        //裁剪比例1:1
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);

        //裁剪后输出图片尺寸大小
        intent.putExtra("outputX",250);
        intent.putExtra("outputY",250);

        intent.putExtra("outputFormat","JPEG");  //图片格式
        intent.putExtra("noFaceDetection",true);   //取消人脸识别
        intent.putExtra("return-data",true);

        startActivityForResult(intent,2);
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void takeSuccess(TResult result) {
        try{
            String strPath = result.getImage().getCompressPath();
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
                switch (id_iamge) {
                    case R.id.my_4s_shop_pic_1:
                       // Glide.with(this).load(strPath).into(my_4s_shop_pic_1);
                         my_4s_shop_pic_1.setImageBitmap(bitmap);
                        my_4s_pic_1_bool = true;
                        break;
                    case R.id.my_4s_shop_pic_2:
                        my_4s_shop_pic_2.setImageBitmap(bitmap);
                        my_4s_pic_2_bool = true;
                        break;
                    case R.id.my_4s_shop_pic_3:
                        my_4s_shop_pic_3.setImageBitmap(bitmap);
                        my_4s_pic_3_bool = true;
                        break;
                    default:
                        break;
                }
            }

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

    private TakePhoto takePhoto_ta;
    private InvokeParam invokeParam;
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto_ta == null){
            takePhoto_ta = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
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
}


