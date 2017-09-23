package lvge.com.myapp.modules.shop_management;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.PermissionManager;
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
import lvge.com.myapp.R;
import lvge.com.myapp.model.CustomerDetail;
import lvge.com.myapp.model.My4sUpdataImageViewModel;
import lvge.com.myapp.util.L;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint({"NewApi", "ValidFragment"})
public class NotAuthenticationFragment extends TakePhotoFragment implements View.OnClickListener {

    private View view;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;

    private CustomProgressDialog progressDialog = null;

    private Uri fileUri = null;
    private CropOptions cropOptions;

    Context ctx;

    private int id_iamge;

    private ImageView img_business_licence;
    private ImageView img_identity_card_positive;
    private ImageView img_identity_card_negative;

    private Boolean img_business_licence_bool=false;
    private Boolean img_identity_card_positive_bool=false;
    private Boolean img_identity_card_negative_bool=false;

    private Bitmap img_business_licence_bitmap;
    private Bitmap img_identity_card_positive_bitmap;
    private Bitmap img_identity_card_negative_bitmap;

    private NotAuthenticationGson result;

    private CustomerDetail result1;
    private Handler mHandler;
    private String companyName;
    private String checkStatus;

    private EditText shop_authentication_company_name;

    private String imgPath;
    private String type;
    private String entityType;
    private String picId;

    public NotAuthenticationFragment(){

    }

    public NotAuthenticationFragment(Handler mHandler) {
        // Required empty public constructor
        this.mHandler = mHandler;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        view = inflater.inflate(R.layout.fragment_shop_manage_not_authentication, container, false);
        ctx = getActivity();
        img_business_licence = (ImageView) view.findViewById(R.id.img_business_licence);
        img_business_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_iamge = R.id.img_business_licence;
                show(v);
            }
        });

        img_identity_card_positive = (ImageView) view.findViewById(R.id.img_identity_card_positive);
        img_identity_card_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_iamge = R.id.img_identity_card_positive;
                show(v);
            }
        });

        img_identity_card_negative = (ImageView) view.findViewById(R.id.img_identity_card_negative);
        img_identity_card_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_iamge = R.id.img_identity_card_negative;
                show(v);
            }
        });

        shop_authentication_company_name = (EditText)view.findViewById(R.id.shop_authentication_company_name);


        TextView shop_authentication_confirm_and_submit = (TextView)view.findViewById(R.id.shop_authentication_confirm_and_submit);
        shop_authentication_confirm_and_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                HasCommitAuthenticationFragment HasCommit = new HasCommitAuthenticationFragment();
//                FragmentTransaction transaction =getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container_authentication,HasCommit);
//                transaction.commit();

                L.d("shop_authentication_confirm_and_submit is clicked");
                if (shop_authentication_company_name.getText().toString().isEmpty()) {
                    L.d("company_name is null");
                    Toast.makeText(ctx, "请填写公司名称", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (img_business_licence_bool || img_identity_card_positive_bool || img_identity_card_negative_bool)
//                    startProgerssDialog();
//                else {
//                    Toast.makeText(ctx, "请先上传照片", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                try {
                    if (img_business_licence_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        img_business_licence.setDrawingCacheEnabled(true);
                        img_business_licence_bitmap = img_business_licence.getDrawingCache();
                        filePaths.add(saveBitmap("1", img_business_licence_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(img_business_licence.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_BUSINESS_LICENSE
                                                ,ShopManagementParameter.SHOPIMG_IDENTITY,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_BUSINESS_LICENSE
                                            ,ShopManagementParameter.SHOPIMG_IDENTITY,img_business_licence.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        img_business_licence_bool = false;
                    }
                    if (img_identity_card_positive_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        img_identity_card_positive.setDrawingCacheEnabled(true);
                        img_identity_card_positive_bitmap = img_identity_card_positive.getDrawingCache();
                        filePaths.add(saveBitmap("2", img_identity_card_positive_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(img_identity_card_positive.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_IDENTITY_CARD_POSITIVE
                                                ,ShopManagementParameter.SHOPIMG_IDENTITY,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_IDENTITY_CARD_POSITIVE
                                            ,ShopManagementParameter.SHOPIMG_IDENTITY,img_identity_card_positive.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        img_identity_card_positive_bool = false;
                    }
                    if (img_identity_card_negative_bool) {
                        final List<String> filePaths = new ArrayList<>();
                        final Map<String, Object> map = new HashMap<String, Object>();
                        img_identity_card_negative.setDrawingCacheEnabled(true);
                        img_identity_card_negative_bitmap = img_identity_card_negative.getDrawingCache();
                        filePaths.add(saveBitmap("3", img_identity_card_negative_bitmap));

                        new Thread() {
                            public void run() {
                                try {
                                    // showProgressDialog();
                                    if(img_identity_card_negative.getTag() == null)
                                        post_str(filePaths, ShopManagementParameter.SHOPIMG_IDENTITY_CARD_NEGATIVE
                                                ,ShopManagementParameter.SHOPIMG_IDENTITY,"");
                                    else
                                    post_str(filePaths, ShopManagementParameter.SHOPIMG_IDENTITY_CARD_NEGATIVE
                                            ,ShopManagementParameter.SHOPIMG_IDENTITY,img_identity_card_negative.getTag().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        Thread.sleep(1000);
                        img_identity_card_negative_bool = false;
                    }
                } catch (Exception e) {
                    stopProgressDialog();
                    Toast.makeText(ctx, "网络异常！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                try {
                    startProgerssDialog();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            OkHttpUtils.post()
                                    //get 方法
                                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/seller/sellerIdentifycationApply") //地址
                                    .addParams("companyName", shop_authentication_company_name.getText().toString())
                                    .build()
                                    .execute(new Callback() {

                                        @Override
                                        public Object parseNetworkResponse(Response response, int i) throws Exception {
                                            String string = response.body().string();//获取相应中的内容Json格式
                                            //把json转化成对应对象
                                            //LoginResultModel是和后台返回值类型结构一样的对象
                                            L.d(string);
                                            result1 = new Gson().fromJson(string, CustomerDetail.class);

                                            return result1;
                                        }

                                        @Override
                                        public void onError(Call call, Exception e, int i) {
                                            stopProgressDialog();
                                        }

                                        @Override
                                        public void onResponse(Object o, int i) {
                                            if (null != o) {
                                                result1 = (CustomerDetail) o;
                                                L.d(String.valueOf(result1.getOperationResult().getResultCode()));
                                                if (result1.getOperationResult().getResultCode() == 0) {
                                                    Toast.makeText(ctx, "已经提交店铺认证", Toast.LENGTH_SHORT).show();
                                                    mHandler.sendEmptyMessage(1002);
                                                }else{
                                                    Toast.makeText(ctx,result1.getOperationResult().getResultMsg(),Toast.LENGTH_SHORT).show();
                                                }
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
                    Toast.makeText(ctx, "网络异常！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        network_init(view);
        return view;
    }

    private void network_init(View view){
        try {

            OkHttpUtils.get()
                    //get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/seller/detailIdentifycation") //地址
                    .build()
                    .execute(new Callback() {//通用的callBack

                        //从后台获取成功后，对相应进行类型转化
                        @Override
                        public Object parseNetworkResponse(Response response, int i) throws Exception {

                            String string = response.body().string();//获取相应中的内容Json格式
                            //把json转化成对应对象
                            //LoginResultModel是和后台返回值类型结构一样的对象
                            result = new Gson().fromJson(string, NotAuthenticationGson.class);
                            return result;
                        }

                        @Override
                        public void onError(okhttp3.Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(Object object, int i) {

                            //object 是 parseNetworkResponse的返回值
                            if (null != object) {
                                result = (NotAuthenticationGson) object;//把通用的Object转化成指定的对象
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                    companyName = String.valueOf(result.getMarketEntity().getCompanyName());
//                                    sellerID = String.valueOf(result.getMarketEntity().getSellerID());
                                    checkStatus = String.valueOf(result.getMarketEntity().getCheckStatus());
                                    shop_authentication_company_name.setText(companyName);
                                    L.d("companyName:"+companyName+"  checkStatus:"+checkStatus);
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
                                                case ShopManagementParameter.SHOPIMG_BUSINESS_LICENSE:
                                                    img_business_licence.setTag(picId);
                                                    OkHttpUtils.get()
                                                            .url(imgPath)
                                                            .build()
                                                            .execute(new BitmapCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int i) {

                                                                }

                                                                @Override
                                                                public void onResponse(Bitmap bitmap, int i) {
                                                                    img_business_licence.setImageBitmap(bitmap);
                                                                }
                                                            });
                                                    break;
                                                case ShopManagementParameter.SHOPIMG_IDENTITY_CARD_POSITIVE:
                                                    img_identity_card_positive.setTag(picId);
                                                    OkHttpUtils.get()
                                                            .url(imgPath)
                                                            .build()
                                                            .execute(new BitmapCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int i) {

                                                                }

                                                                @Override
                                                                public void onResponse(Bitmap bitmap, int i) {
                                                                    img_identity_card_positive.setImageBitmap(bitmap);
                                                                }
                                                            });
                                                    break;
                                                case ShopManagementParameter.SHOPIMG_IDENTITY_CARD_NEGATIVE:
                                                    img_identity_card_negative.setTag(picId);
                                                    OkHttpUtils.get()
                                                            .url(imgPath)
                                                            .build()
                                                            .execute(new BitmapCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int i) {

                                                                }

                                                                @Override
                                                                public void onResponse(Bitmap bitmap, int i) {
                                                                    img_identity_card_negative.setImageBitmap(bitmap);
                                                                }
                                                            });
                                                    break;
                                                default:
                                                    break;
                                            }



                                        }

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
            Toast.makeText(view.getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    public void show(View view) {
//        ShopTakePhoto shopTakePhoto = new ShopTakePhoto(ctx);
        dialog = new Dialog(ctx, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(ctx).inflate(R.layout.layout_menu_shop_manage_img_dialog, null);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_phone_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromGalleryWithCrop(fileUri,cropOptions);
                Toast.makeText(ctx, "点击了从相册选择", Toast.LENGTH_SHORT).show();
                break;
            case R.id.take_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromCaptureWithCrop(fileUri,cropOptions);
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

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        try{
            String strPath = result.getImage().getCompressPath();
            Bitmap bitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
                switch (id_iamge) {
                    case R.id.img_business_licence:
                        // Glide.with(this).load(strPath).into(my_4s_shop_pic_1);
                        img_business_licence.setImageBitmap(bitmap);
                        img_business_licence_bool = true;
                        break;
                    case R.id.img_identity_card_positive:
                        img_identity_card_positive.setImageBitmap(bitmap);
                        img_identity_card_positive_bool = true;
                        break;
                    case R.id.img_identity_card_negative:
                        img_identity_card_negative.setImageBitmap(bitmap);
                        img_identity_card_negative_bool = true;
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

        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Toast.makeText(ctx,"选择已取消",Toast.LENGTH_SHORT);
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
        PermissionManager.handlePermissionsResult((Activity) ctx,type,invokeParam,this);
    }

    private void post_str(List<String> filePaths, String filetype,String type,String picId) {

        try {

            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/seller/sellerImgSave";

            PicUpload bs = new PicUpload();
            String str = bs.imgPut(path, filePaths, filetype,type,picId);

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
//                    ImageNum--;
//                    if (ImageNum == 0) {
//                        stopProgressDialog();
//                        shop_image_main_page.setDrawingCacheEnabled(false);
//                        shop_image_env_page.setDrawingCacheEnabled(false);
//                        shop_image_env_page_1.setDrawingCacheEnabled(false);
//                        shop_image_env_page_2.setDrawingCacheEnabled(false);
//                        shop_image_other_page.setDrawingCacheEnabled(false);
//                        Toast.makeText(ShopManagementActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
//                    }
                    break;
                case 1:
                    stopProgressDialog();
//                    Toast.makeText(ShopManagementActivity.this, "上传失败！", Toast.LENGTH_LONG).show();
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

    private void startProgerssDialog(){
        if(progressDialog == null){
            progressDialog = CustomProgressDialog.createDialog(ctx);
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
