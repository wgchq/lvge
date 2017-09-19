package lvge.com.myapp.modules.shop_management;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
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
import java.util.Timer;
import java.util.TimerTask;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.model.CustomerDetail;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.My4sUpdataImageViewModel;
import lvge.com.myapp.modules.my_4s_management.BaseTest;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivity;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivityGson;
import lvge.com.myapp.util.L;
import okhttp3.Call;
import okhttp3.Response;

import lvge.com.myapp.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class ShopManagementActivity extends TakePhotoActivity implements View.OnClickListener{
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;
    private Dialog consume_back_dialog;
    private Uri fileUri = null;
    private CropOptions cropOptions;

    private int id_iamge;

    private ImageView shop_image_main_page;
    private ImageView shop_image_env_page;
    private ImageView shop_image_env_page_1;
    private ImageView shop_image_env_page_2;
    private ImageView shop_image_other_page;

    private Bitmap shop_image_main_page_bitmap;
    private Bitmap shop_image_env_page_bitmap;
    private Bitmap shop_image_env_page_1_bitmap;
    private Bitmap shop_image_env_page_2_bitmap;
    private Bitmap shop_image_other_page_bitmap;

    private boolean shop_image_main_page_bool = false;
    private boolean shop_image_env_page_bool = false;
    private boolean shop_image_env_page_1_bool = false;
    private boolean shop_image_env_page_2_bool = false;
    private boolean shop_image_other_page_bool = false;

    private String shop_image_main_page_id;
    private String shop_image_env_page_id;
    private String shop_image_env_page_1_id;
    private String shop_image_env_page_2_id;
    private String shop_image_other_page_id;

    private CustomProgressDialog progressDialog = null;
    private int ImageNum = 0;

    private TextView shop_finish;
    private ShopManagementActivityGson result;

    //shop_profile
    private String areaID;
    private String sellerID;
    private String authentic;
    private String address;
    private String name;
    private String openService;
    private String lng;
    private String telephone;
    private String lat;
    private String mobile;
    private String noExpense;

    private String imgPath;
    private String type;
    private String entityType;
    private String picId;

    private TextView shop_name;
    private TextView tv_shop_authentication_text_content;
    private TextView tv_shop_deposit_text_content;
    private TextView tv_shop_address_content;

    private int Image_num = 0;

    private TextView consume_back_support;
    private TextView consume_back_no_support;
    private TextView consume_back_cancel;

    private TextView tv_shop_consume_back_text_content;
    private TextView tv_shop_contact_text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management);

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_management);
        toolbar.setTitle("");
       //d setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_shop_address_content = (TextView) findViewById(R.id.tv_shop_address_content);
        shop_image_main_page = (ImageView)findViewById(R.id.shop_image_main_page);
        shop_image_env_page = (ImageView)findViewById(R.id.shop_image_env_page);
        shop_image_env_page_1 = (ImageView)findViewById(R.id.shop_image_env_page_1);
        shop_image_env_page_2 = (ImageView)findViewById(R.id.shop_image_env_page_2);
        shop_image_other_page = (ImageView)findViewById(R.id.shop_image_other_page);

        shop_finish = (TextView)findViewById(R.id.shop_finish_1);
        shop_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startProgerssDialog();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
                            L.d("areaID:" + areaID + "\nsellerID:" + sellerID + "\nauthentic:" + authentic
                                    + "\naddress:" + address + "\nname:" + name + "\nopenService:" + openService
                                    + "\nlng:" + lng + "\ntelephone:" + telephone + "\nlat:" + lat + "\nmobile:" + mobile);
                            OkHttpUtils.post()//get 方法
                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/seller/saveOrUpdate") //地址
                                    .addParams("name", shop_name.getText().toString())
                                    .addParams("sellerID",sellerID)
                                    .addParams("address", address)
                                    .addParams("areaID", areaID)
                                    .addParams("lng", lng)
                                    .addParams("lat", lat)
                                    .addParams("mobile", mobile)
                                    .addParams("telephone", telephone)
                                    .addParams("noExpense",noExpense)
                                    //.addParams("type",type)
                                    .build()
                                    .execute(new Callback() {
                                        @Override
                                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                                            String string = response.body().string();//获取相应中的内容Json格式
                                            //把json转化成对应对象
                                            //LoginResultModel是和后台返回值类型结构一样的对象
                                            CustomerDetail result = new Gson().fromJson(string, CustomerDetail.class);
                                            return result;
                                        }

                                        @Override
                                        public void onError(Call call, Exception e, int i) {
                                            stopProgressDialog();
                                        }

                                        @Override
                                        public void onResponse(Object o, int i) {
                                            if (null != o) {
                                                CustomerDetail result = (CustomerDetail) o;//把通用的Object转化成指定的对象
                                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录
                                                    Toast.makeText(ShopManagementActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(ShopManagementActivity.this, result.getOperationResult().getResultMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {//当没有返回对象时，表示网络没有联通
                                                Toast.makeText(ShopManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                            }
                                            stopProgressDialog();
                                        }
                                    });
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule(task, 4000);//3秒后执行



                } catch (Exception e) {
                    stopProgressDialog();
                    Toast.makeText(ShopManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }

                try {
                    //if (shop_image_main_page_bool)// || shop_image_env_page_bool || shop_image_env_page_1_bool)
                        startProgerssDialog();
                    if (shop_image_main_page_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        shop_image_main_page.setDrawingCacheEnabled(true);
                        shop_image_main_page_bitmap = shop_image_main_page.getDrawingCache();
                        filePaths.add(saveBitmap("1", shop_image_main_page_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(shop_image_main_page.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_MAINPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,"");
                                    else
                                    post_str(filePaths,ShopManagementParameter.SHOPIMG_MAINPAGE
                                            ,ShopManagementParameter.SHOPIMG_PAGE,shop_image_main_page.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        //Thread.sleep(1000);
                        ImageNum++;
                        shop_image_main_page_bool = false;
                    }
                    if (shop_image_env_page_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        shop_image_env_page.setDrawingCacheEnabled(true);
                        shop_image_env_page_bitmap = shop_image_env_page.getDrawingCache();
                        filePaths.add(saveBitmap("2", shop_image_env_page_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(shop_image_env_page.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                            ,ShopManagementParameter.SHOPIMG_PAGE,shop_image_env_page.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        //Thread.sleep(1000);
                        shop_image_env_page_bool = false;
                        ImageNum++;
                    }
                    if (shop_image_env_page_1_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        shop_image_env_page_1.setDrawingCacheEnabled(true);
                        shop_image_env_page_1_bitmap = shop_image_env_page_1.getDrawingCache();
                        filePaths.add(saveBitmap("3", shop_image_env_page_1_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(null == shop_image_env_page_1.getTag())
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,"");
                                    else
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,shop_image_env_page_1.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        //Thread.sleep(1000);
                        shop_image_env_page_1_bool = false;
                        ImageNum++;
                    }
                    if (shop_image_env_page_2_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        shop_image_env_page_2.setDrawingCacheEnabled(true);
                        shop_image_env_page_2_bitmap = shop_image_env_page_2.getDrawingCache();
                        filePaths.add(saveBitmap("4", shop_image_env_page_2_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(shop_image_env_page_2.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_ENVPAGE
                                            ,ShopManagementParameter.SHOPIMG_PAGE,shop_image_env_page_2.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        //Thread.sleep(1000);
                        shop_image_env_page_2_bool = false;
                        ImageNum++;
                    }
                    if (shop_image_other_page_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        shop_image_other_page.setDrawingCacheEnabled(true);
                        shop_image_other_page_bitmap = shop_image_other_page.getDrawingCache();
                        filePaths.add(saveBitmap("5", shop_image_other_page_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(shop_image_other_page.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_OTHERPAGE
                                                ,ShopManagementParameter.SHOPIMG_PAGE,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_OTHERPAGE
                                            ,ShopManagementParameter.SHOPIMG_PAGE,shop_image_other_page.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        //Thread.sleep(1000);
                        shop_image_other_page_bool = false;
                        ImageNum++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        RelativeLayout lyy_shop_img = (RelativeLayout) findViewById(R.id.lly_shop_img_add);
//        lyy_shop_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShopManagementActivity.this, ShopManageShopImgActivity.class);
//                startActivity(intent);
//            }
//        });
        //configTakePhotoOption(getTakePhoto());

        RelativeLayout lly_shop_authentication = (RelativeLayout) findViewById(R.id.lly_shop_authentication);
        lly_shop_authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this, ShopManageAuthenticationActivity.class);
                intent.putExtra("authentic", authentic);
                startActivity(intent);
            }
        });


        RelativeLayout lly_shop_deposit = (RelativeLayout) findViewById(R.id.lly_shop_deposit);
        lly_shop_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManagementActivity.this, ShopManageShopDepositActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout lly_shop_consume_back = (RelativeLayout)findViewById(R.id.lly_shop_consume_back);
        lly_shop_consume_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consume_back_is_support();
            }
        });

        RelativeLayout lly_shop_contact = (RelativeLayout) findViewById(R.id.lly_shop_contact);

        lly_shop_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShopManagementActivity.this,ShopManagementPhonenumber.class);
                intent.putExtra("phonenumber",mobile);
                startActivityForResult(intent,0);
//                EditText phonenumberEdit = new EditText(ShopManagementActivity.this);
//                phonenumberEdit.setText(telephone);
//                new AlertDialog.Builder(ShopManagementActivity.this).setTitle("联系电话")
//                        .setIcon(android.R.drawable.ic_menu_call)
//                        .setView(phonenumberEdit)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
            }
        });

        RelativeLayout lly_shop_return_goods = (RelativeLayout)findViewById(R.id.lly_shop_return_goods) ;
        lly_shop_return_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManagementActivity.this,ShopManagementBackAddress.class);
                startActivity(intent);
            }
        });


        RelativeLayout lly_shop_address =(RelativeLayout)findViewById(R.id.lly_shop_address);
        lly_shop_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ShopManagementActivity.this,ShopAddressActivity.class);

                Bundle bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("id", sellerID);
                bundle.putString("lng", lng);
                bundle.putString("lat", lat);
                bundle.putString("name",shop_name.getText().toString());
                bundle.putString("address", address);
                bundle.putString("areaID",areaID);
                bundle.putString("mobile",mobile);
                bundle.putString("telephone",telephone);
                bundle.putString("noExpense",noExpense);
                intent.putExtras(bundle);



                startActivity(intent);
            }
        });

        tv_shop_consume_back_text_content = (TextView)findViewById(R.id.tv_shop_consume_back_text_content);
        tv_shop_contact_text_content = (TextView)findViewById(R.id.tv_shop_contact_text_content);
        resource_init();
        network_init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:


                break;
            case 1001:
                Bundle b = data.getExtras();
                mobile = b.getString("PhonenumberResult");
                L.d(mobile);
                break;
            default:
                break;
        }
    }

    //    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        switch (resultCode){
//            case RESULT_OK:
//                Bundle b = data.getExtras();
//                mobile = b.getString("PhonenumberResult");
//
//
//                break;
//            default:
//                break;
//        }
//    }

    private void startProgerssDialog(){
        if(progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(this);
            // progressDialog.setMessage("正在加载中。。");
        }
        progressDialog.show();
    }

    private void resource_init(){
        shop_name = (TextView)findViewById(R.id.shop_name);
        tv_shop_authentication_text_content = (TextView)findViewById(R.id.tv_shop_authentication_text_content);
        tv_shop_deposit_text_content = (TextView)findViewById(R.id.tv_shop_deposit_text_content);
    }

    private void network_init(){
        try {

            OkHttpUtils.get()
                    //get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/seller/detail") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            L.d(string);
                            result = new Gson().fromJson(string, ShopManagementActivityGson.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                result = (ShopManagementActivityGson) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    areaID = String.valueOf(result.getMarketEntity().getAreaID());
                                    sellerID = String.valueOf(result.getMarketEntity().getSellerID());
                                    authentic = String.valueOf(result.getMarketEntity().getAuthentic());
                                    address = String.valueOf(result.getMarketEntity().getAddress());
                                    name = String.valueOf(result.getMarketEntity().getName());
                                    openService = String.valueOf(result.getMarketEntity().getOpenService());
                                    lng = String.valueOf(result.getMarketEntity().getLng());
                                    telephone = result.getMarketEntity().getTelephone();
                                    lat = result.getMarketEntity().getLat();
                                    mobile = result.getMarketEntity().getMobile();
                                    noExpense = result.getMarketEntity().getNoExpense();
                                    L.d("areaID:" + areaID + "\nsellerID:" + sellerID + "\nauthentic:" + authentic
                                            + "\naddress:" + address + "\nname:" + name + "\nopenService:" + openService
                                            + "\nlng:" + lng + "\ntelephone:" + telephone + "\nlat:" + lat + "\nmobile" + mobile
                                            + "\nnoExpense:"+noExpense);
                                    switch (authentic) {
                                        case ShopManagementParameter.AUTHENTIC_NOAPPLY:
                                            tv_shop_authentication_text_content.setHint("未申请企业认证");
                                            break;
                                        case ShopManagementParameter.AUTHENTIC_APPLYING:
                                            tv_shop_authentication_text_content.setHint("企业认证审核中");
                                            break;
                                        case ShopManagementParameter.AUTHENTIC_PASSING:
                                            tv_shop_authentication_text_content.setHint("已通过企业认证");
                                            break;
                                        case ShopManagementParameter.AUTHENTIC_NOPASS:
                                            tv_shop_authentication_text_content.setHint("未通过企业认证");
                                            break;
                                        default:
                                            break;
                                    }
                                    if(openService!=null) {
                                        switch (openService) {
                                            case ShopManagementParameter.OPENSERVICE_NOAPPLY:
                                                tv_shop_deposit_text_content.setHint("未开通");
                                                break;
                                            case ShopManagementParameter.OPENSERVICE_APPLYING:
                                                tv_shop_deposit_text_content.setHint("开通审核中");
                                                break;
                                            case ShopManagementParameter.OPENSERVICE_PASSING:
                                                tv_shop_deposit_text_content.setHint("审核通过");
                                        }
                                    }

                                    if(noExpense != null){
                                        switch (noExpense) {
                                            case ShopManagementParameter.NOEXPENSE_NOSUPPORT:
                                                tv_shop_consume_back_text_content.setHint("不支持");
                                                break;
                                            case ShopManagementParameter.NOEXPENSE_SUPPORT:
                                                tv_shop_consume_back_text_content.setHint("支持");
                                                break;
                                        }
                                    }

                                    if(address!=null)
                                    {
                                        tv_shop_address_content.setText(address);
                                    }

                                    tv_shop_contact_text_content.setHint(mobile);

                                    shop_name.setText(name);

                                    if (result.getMarketEntity().getSellerImgs() != null) {
                                        int size = result.getMarketEntity().getSellerImgs().size();
                                        for (int index = 0; index < size; index++) {
                                            imgPath = result.getMarketEntity().getSellerImgs().get(index).getImgPath();
                                            type = String.valueOf(result.getMarketEntity().getSellerImgs().get(index).getType());
                                            entityType = result.getMarketEntity().getSellerImgs().get(index).getEntityType();
                                            picId = result.getMarketEntity().getSellerImgs().get(index).getPictureId();
                                            L.d("imgPath:" + imgPath + "\ntype:" + type + "\nentityType:" + entityType+"\npicId:"+picId);
                                            if (imgPath == null || imgPath == "")
                                                continue;

                                            switch (type) {
                                                case ShopManagementParameter.SHOPIMG_MAINPAGE:
                                                    shop_image_main_page.setTag(picId);
                                                    OkHttpUtils.get()
                                                            .url(imgPath)
                                                            .build()
                                                            .execute(new BitmapCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int i) {

                                                                }

                                                                @Override
                                                                public void onResponse(Bitmap bitmap, int i) {
                                                                    shop_image_main_page.setImageBitmap(bitmap);
                                                                }
                                                            });
                                                    break;
                                                case ShopManagementParameter.SHOPIMG_ENVPAGE:
                                                    if(Image_num == 0){
                                                        shop_image_env_page.setTag(picId);
                                                        OkHttpUtils.get()
                                                                .url(imgPath)
                                                                .build()
                                                                .execute(new BitmapCallback() {
                                                                    @Override
                                                                    public void onError(Call call, Exception e, int i) {

                                                                    }

                                                                    @Override
                                                                    public void onResponse(Bitmap bitmap, int i) {
                                                                        shop_image_env_page.setImageBitmap(bitmap);
                                                                    }
                                                                });
                                                    }else if(Image_num == 1){
                                                        shop_image_env_page_1.setTag(picId);
                                                        OkHttpUtils.get()
                                                                .url(imgPath)
                                                                .build()
                                                                .execute(new BitmapCallback() {
                                                                    @Override
                                                                    public void onError(Call call, Exception e, int i) {

                                                                    }

                                                                    @Override
                                                                    public void onResponse(Bitmap bitmap, int i) {
                                                                        shop_image_env_page_1.setImageBitmap(bitmap);
                                                                    }
                                                                });
                                                    }else{
                                                        shop_image_env_page_2.setTag(picId);
                                                        OkHttpUtils.get()
                                                                .url(imgPath)
                                                                .build()
                                                                .execute(new BitmapCallback() {
                                                                    @Override
                                                                    public void onError(Call call, Exception e, int i) {

                                                                    }

                                                                    @Override
                                                                    public void onResponse(Bitmap bitmap, int i) {
                                                                        shop_image_env_page_2.setImageBitmap(bitmap);
                                                                    }
                                                                });
                                                    }

                                                    Image_num++;

                                                    break;
                                                case ShopManagementParameter.SHOPIMG_OTHERPAGE:
                                                    shop_image_other_page.setTag(picId);
                                                    OkHttpUtils.get()
                                                            .url(imgPath)
                                                            .build()
                                                            .execute(new BitmapCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int i) {

                                                                }

                                                                @Override
                                                                public void onResponse(Bitmap bitmap, int i) {
                                                                    shop_image_other_page.setImageBitmap(bitmap);
                                                                }
                                                            });
                                                    break;
                                                default:
                                                    break;
                                            }



                                        }
                                        Image_num = 0;
                                    } else {
                                        // Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                                        // startActivity(intent);
                                    }
                                } else {//当没有返回对象时，表示网络没有联通
                                    // Toast.makeText(MainActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
           // currentIndex = 0;
        } catch (Exception e) {
            Toast.makeText(ShopManagementActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    public void consume_back_is_support(){
        consume_back_dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_menu_shop_manage_consume_back_dialog, null);
        //初始化控件
        consume_back_support = (TextView) inflate.findViewById(R.id.consume_back_support);
        consume_back_no_support = (TextView) inflate.findViewById(R.id.consume_back_no_support);
        consume_back_cancel = (TextView) inflate.findViewById(R.id.consume_back_cancel);

        consume_back_support.setOnClickListener(this);
        consume_back_no_support.setOnClickListener(this);
        consume_back_cancel.setOnClickListener(this);

        //将布局设置给Dialog
        consume_back_dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = consume_back_dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        consume_back_dialog.show();//显示对话框
    }
//    public void finish1(View view){
//        //Toast.makeText(ShopManagementActivity.this,"ok",Toast.LENGTH_SHORT);
//        L.d("xxx");
//    }

    public void show(View view) {
        id_iamge = view.getId();

//        switch(id_iamge){
//            case R.id.shop_image_main_page:
//                if(shop_image_main_page_bool){
//                    Toast.makeText(this,"请先长按删除照片",Toast.LENGTH_SHORT);
//                    return;
//                }
//                break;
//            case R.id.shop_image_env_page:
//                if(shop_image_env_page_bool){
//                    Toast.makeText(this,"请先长按删除照片",Toast.LENGTH_SHORT);
//                    return;
//                }
//                break;
//            case R.id.shop_image_env_page_1:
//                if(shop_image_env_page_1_bool){
//                    Toast.makeText(this,"请先长按删除照片",Toast.LENGTH_SHORT);
//                    return;
//                }
//                break;
//            case R.id.shop_image_env_page_2:
//                if(shop_image_env_page_2_bool){
//                    Toast.makeText(this,"请先长按删除照片",Toast.LENGTH_SHORT);
//                    return;
//                }
//                break;
//            case R.id.shop_image_other_page:
//                if(shop_image_other_page_bool){
//                    Toast.makeText(this,"请先长按删除照片",Toast.LENGTH_SHORT);
//                    return;
//                }
//                break;
//            default:break;
//        }

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

    public void shop_upload_info(){
        Toast.makeText(this,"ok",Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_phone_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromGalleryWithCrop(fileUri,cropOptions);
                //Toast.makeText(this, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.take_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromCaptureWithCrop(fileUri,cropOptions);
                //Toast.makeText(this, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;

            case R.id.consume_back_support:
                tv_shop_consume_back_text_content.setHint("支持");
                noExpense = ShopManagementParameter.NOEXPENSE_SUPPORT;
                consume_back_dialog.dismiss();
                break;
            case R.id.consume_back_no_support:
                tv_shop_consume_back_text_content.setHint("不支持");
                noExpense = ShopManagementParameter.NOEXPENSE_NOSUPPORT;
                consume_back_dialog.dismiss();
                break;
            case R.id.consume_back_cancel:
                consume_back_dialog.dismiss();
                break;
                //finish();
        }
        //dialog.dismiss();
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto_ta == null){
            takePhoto_ta = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto_ta;
    }

    private Uri getImageCropUri(){
        File file = new File(Environment.getExternalStorageDirectory(),"/temp/" + System.currentTimeMillis() + ".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdir();

        return Uri.fromFile(file);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        try{
            String strPath = result.getImage().getCompressPath();
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
               switch (id_iamge) {
                    case R.id.shop_image_main_page:
                        // Glide.with(this).load(strPath).into(my_4s_shop_pic_1);
                        shop_image_main_page.setImageBitmap(bitmap);
                        shop_image_main_page_bool = true;
                        break;
                    case R.id.shop_image_env_page:
                        shop_image_env_page.setImageBitmap(bitmap);
                        shop_image_env_page_bool = true;
                        break;
                    case R.id.shop_image_env_page_1:
                        shop_image_env_page_1.setImageBitmap(bitmap);
                        shop_image_env_page_1_bool = true;
                        break;
                   case R.id.shop_image_env_page_2:
                       shop_image_env_page_2.setImageBitmap(bitmap);
                       shop_image_env_page_2_bool = true;
                       break;
                   case R.id.shop_image_other_page:
                       shop_image_other_page.setImageBitmap(bitmap);
                       shop_image_other_page_bool = true;
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
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Toast.makeText(this,"选择已取消",Toast.LENGTH_SHORT);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    private void post_str(List<String> filePaths, String filetype, String type,String picId) {

        try {

            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/seller/sellerImgSave";

            PicUpload bs = new PicUpload();
            String str = bs.imgPut(path, filePaths, filetype, type,picId);

            returnMessage(str);

        } catch (Exception e) {
            e.printStackTrace();
            returnMessage("error");
        }
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
        //        private ImageView shop_image_main_page;
//        private ImageView shop_image_env_page;
//        private ImageView shop_image_env_page_1;
//        private ImageView shop_image_env_page_2;
//        private ImageView shop_image_other_page;
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ImageNum--;
                    if (ImageNum == 0) {
                        stopProgressDialog();
                        shop_image_main_page.setDrawingCacheEnabled(false);
                        shop_image_env_page.setDrawingCacheEnabled(false);
                        shop_image_env_page_1.setDrawingCacheEnabled(false);
                        shop_image_env_page_2.setDrawingCacheEnabled(false);
                        shop_image_other_page.setDrawingCacheEnabled(false);
                        Toast.makeText(ShopManagementActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 1:
                    stopProgressDialog();
                    Toast.makeText(ShopManagementActivity.this, "上传失败！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

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

    private void stopProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
