package lvge.com.myapp.modules.my_4s_management;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.model.SaleConsultantTwoMode;
import okhttp3.Call;
import okhttp3.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class SaleConsultantTwo extends AppCompatActivity implements View.OnClickListener {

    public static final int CUT_PICTURE = 1;
    public static final int SHOW_PICTURE = 2;
    public ImageView sale_consultant_two_iamgeview=null;
    private Uri imageUri = null;
    private File file;
    private String id;
    private Bitmap bitmap;
    private ProgressDialog progDialog = null;

    private static SaleConsultantTwo parent;

    private static final String LOG_TAG = "HelloCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri = null;

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_consultant_two);

        final RelativeLayout sale_consultant_two_iamge = (RelativeLayout) findViewById(R.id.sale_consultant_two_iamge);
        final EditText et_rname = (EditText) findViewById(R.id.sale_consultant_inputname);
        final EditText et_phone = (EditText) findViewById(R.id.sale_consultant_inputphone);
        final EditText et_memo = (EditText) findViewById(R.id.sale_consultant_inputmemo);
        sale_consultant_two_iamgeview = (ImageView) findViewById(R.id.sale_consultant_two_iamgeview);
        TextView sale_consultant_Preservation = (TextView) findViewById(R.id.sale_consultant_Preservation);
/**
        ImageView sale_consultant_back = (ImageView) findViewById(R.id.sale_consultant_back);
        sale_consultant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleConsultantTwo.this, SalesConsultant.class);
                startActivity(intent);
            }
        });
 **/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_4s_sales_consultant_two);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(SaleConsultantTwo.this, SalesConsultant.class);
               // startActivity(intent);
                finish();
            }
        });

        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //String strBitmap = convertIconToString(sale_consultant_two_iamgeview.getDrawingCache());
                    //   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    //  bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                    //  byte[] data = bos.toByteArray();
                    //  String strBitmap = new String(data);
                   // file = new File(saveBitmap("1"));
                    // FileOutputStream fo = new FileOutputStream(file);
                    // Map<String,File> fileMap = new HashMap<String, File>();
                    //fileMap.put("headImg",file);
                  //  if (!file.exists()) {
                   //     Toast.makeText(SaleConsultantTwo.this, "图片错误！", Toast.LENGTH_SHORT).show();
                  //      return;
                 //   }
                    if(et_memo.getText().toString().equals("") || et_memo.getText().toString() == null) {
                        Toast.makeText(SaleConsultantTwo.this, "请填入个性签名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(et_phone.getText().toString().equals("") || et_phone.getText().toString() == null){
                        Toast.makeText(SaleConsultantTwo.this, "请填入电话！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(et_rname.getText().toString().equals("") || et_rname.getText().toString() == null){
                        Toast.makeText(SaleConsultantTwo.this, "请填入姓名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sale_consultant_two_iamgeview.setDrawingCacheEnabled(true);
                    bitmap = sale_consultant_two_iamgeview.getDrawingCache();

                    showProgressDialog();

                     new Thread() {
                        public void run() {
                            try {
                               // showProgressDialog();
                                post_str(et_rname.getText().toString(), et_phone.getText().toString(), et_memo.getText().toString());
                                } catch ( Exception e) {
                                e.printStackTrace();
                                dissmissProgressDialog();
                            }
                        }
                    }.start();


/**
 PostFormBuilder post = OkHttpUtils.post();
 Map<String,String> params = new HashMap<String, String>();
 params.put("name",et_rname.getText().toString());
 params.put("phone",et_phone.getText().toString());
 params.put("memo",et_memo.getText().toString());
 post.url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save"); //地址
 post.params(params);
 post.addFile("headImg",et_rname.getText().toString()+".png",file);
 RequestCall build = post.build();
 build.execute(new StringCallback() {
@Override public void onError(Call call, Exception e, int i) {

}

@Override public void onResponse(String s, int i) {
LoginResultModel result = new Gson().fromJson(s, LoginResultModel.class);
}
});


 OkHttpUtils.post()//get 方法
 .url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save") //地址
 .addParams("name", et_rname.getText().toString()) //需要传递的参数
 .addParams("phone", et_phone.getText().toString())
 .addParams("memo",et_memo.getText().toString())
 .addFile("headImg", "111.PNG",file)
 .build()
 .execute(new Callback() {//通用的callBack

 //从后台获取成功后，对相应进行类型转化
 @Override public Object parseNetworkResponse(Response response, int i) throws Exception {

 String string = response.body().string();//获取相应中的内容Json格式
 //把json转化成对应对象
 //LoginResultModel是和后台返回值类型结构一样的对象
 LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
 return result;
 }

 @Override public void onError(okhttp3.Call call, Exception e, int i) {

 }

 @Override public void onResponse(Object object, int i) {

 //object 是 parseNetworkResponse的返回值
 if (null != object) {
 LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
 if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
 Toast.makeText(SaleConsultantTwo.this, "上传成功！", Toast.LENGTH_SHORT).show();
 } else {
 Toast.makeText(SaleConsultantTwo.this, "保存失败！", Toast.LENGTH_SHORT).show();
 }
 } else {//当没有返回对象时，表示网络没有联通
 Toast.makeText(SaleConsultantTwo.this, "网络异常！", Toast.LENGTH_SHORT).show();
 }
 }
 });

 ***/
                } catch (Exception e) {
                    Toast.makeText(SaleConsultantTwo.this, "网络异常！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /***
        sale_consultant_two_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
         ***/

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        if(id != null ){
            try {
                OkHttpUtils.get()//get 方法
                        .url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/detail") //地址
                        .addParams("id", id) //需要传递的参数
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response, int i) throws Exception {
                                String str = response.body().string();
                                SaleConsultantTwoMode result = new Gson().fromJson(str,SaleConsultantTwoMode.class);
                                return result;
                            }

                            @Override
                            public void onError(Call call, Exception e, int i) {

                            }

                            @Override
                            public void onResponse(Object o, int i) {
                                if(o !=null){
                                    SaleConsultantTwoMode result = (SaleConsultantTwoMode)o;
                                    if(result.getOperationResult().getResultCode() == 0){
                                        et_rname.setText(result.getMarketEntity().getName());
                                        et_phone.setText(result.getMarketEntity().getPhone());
                                        et_memo.setText(result.getMarketEntity().getMemo());

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
                                                            sale_consultant_two_iamgeview.setImageBitmap(bitm);
                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        });
            }catch (Exception e){

            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /***
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null)
        {
                imageUri = data.getData();

        }
        if (imageUri != null) {
            try {
                String[] prjo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(imageUri, prjo, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    // file = new File(path);
                   Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                    if (bitmap != null) {
                        sale_consultant_two_iamgeview.setImageBitmap(bitmap);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

     **/


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

    private void post_str(String name, String phone, String memo) {

        try {
            /*
            HttpURLConnection connection = null;
            URL url = new URL("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("name", name);
            connection.setRequestProperty("phone",phone);
            connection.setRequestProperty("memo", memo);
            FileInputStream fileInputStream = new FileInputStream(strpath);

            OutputStream os = connection.getOutputStream();
            int count = 0;
            while ((count = fileInputStream.read()) != -1) {
                os.write(count);
            }
            os.flush();
            os.close();

            int resultcode = connection.getResponseCode();
            if (resultcode!= HttpURLConnection.HTTP_OK) {
                Toast.makeText(SaleConsultantTwo.this, "上传失败！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SaleConsultantTwo.this, "上传成功！", Toast.LENGTH_SHORT).show();
            }
            */

            String path;
            if(id == null){
                 path = "http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save";
            }else {
                 path = "http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/update";
            }


            List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap());

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            map.put("phone", phone);
            map.put("memo", memo);

            if(id != null){
                map.put("id",id);
            }

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


    private void showProgressDialog(){
        if(progDialog == null){
            progDialog = new ProgressDialog(this);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在上传 ...");
        progDialog.show();
    }

    private void dissmissProgressDialog(){
        if(progDialog != null){
            progDialog.dismiss();
        }
    }

    Handler mHander = new Handler() {
        public void handleMessage(Message msg){
            dissmissProgressDialog();
            sale_consultant_two_iamgeview.setDrawingCacheEnabled(false);
            switch (msg.what){
                case 0:
                    Toast.makeText(SaleConsultantTwo.this,"上传成功！",Toast.LENGTH_LONG).show();
                   // Intent intent = new Intent(SaleConsultantTwo.this, SalesConsultant.class);
                   // startActivity(intent);
                    finish();
                    break;
                case 1:
                    Toast.makeText(SaleConsultantTwo.this,"上传失败！",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };

    public void my4s_show(View view) {
        //id_iamge = view.getId();
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.from_phone_photo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

               // intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.take_photo:
                Intent take_photo_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // create a file to save the image
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
                // set the image file name
                take_photo_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                try {
                    startActivityForResult(take_photo_intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                } catch (Exception e) {
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
        dialog.dismiss();
        if (requestCode == 1 && data != null)
        {
            fileUri = data.getData();
            if (fileUri != null) {
                try {
                    String[] prjo = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(fileUri, prjo, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        // file = new File(path);
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));

                        if (bitmap != null) {
                            // sale_consultant_two_iamgeview.setImageBitmap(bitmap);
                            crop(fileUri);
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


       else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && fileUri != null)
        {
            // imageUri = data.getData();
            try{
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));

                if(bitmap != null){
                    sale_consultant_two_iamgeview.setImageBitmap(bitmap);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(requestCode == 2){
            Bitmap bitmap = data.getParcelableExtra("data");
            if(bitmap != null){
                sale_consultant_two_iamgeview.setImageBitmap(bitmap);
            }
        }

    }

    private void crop(Uri uri){

        Intent intent = new Intent("com.android.camera.action.CROP");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try{
                String path = getPath(this,uri);
                intent.setDataAndType(Uri.fromFile(new File(path)),"image/*");
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

    public static String getPath(final Context context,final Uri uri){
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if(!DocumentsContract.isDocumentUri(context,uri)){
            if(uri != null){
                String uriStr = uri.toString();
                String path = uriStr.substring(10,uriStr.length());
                if(path.startsWith("com.sec.android.gallery3d")){
                    return null;
                }
            }
            String[] filePaehColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri,filePaehColumn,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePaehColumn[0]);
            String picturePath = context.getString(columnIndex);
            cursor.close();

            return picturePath;
        }
        if(isKitKat && DocumentsContract.isDocumentUri(context, uri)){
            if(isExternalStorgeDocument(uri)){
                final String docld = DocumentsContract.getDocumentId(uri);
                final String[] split = docld.split(":");
                final String type = split[0];

                if("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }else if(isDownloadsDocument(uri)){
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                return getDataColumn(context,contentUri,null,null);
            }else if(isMediaDocument(uri)){
                final String docld = DocumentsContract.getDocumentId(uri);
                final String[] split = docld.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if("image".equals(type)){
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }else if("video".equals(type)){
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if("audio".equals(type)){
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context,contentUri,selection,selectionArgs);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            if(isGooglePhotosUri(uri)){
                return uri.getLastPathSegment();
            }

            return getDataColumn(context,uri,null,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }
        return  null;
    }

    public static boolean isExternalStorgeDocument(Uri uri){
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static  boolean isDownloadsDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context,Uri uri,String selection,String[] seletionArgs){
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection={
                column
        };

        try{
            cursor = context.getContentResolver().query(uri,projection,selection,seletionArgs,null);
            if(cursor != null && cursor.moveToFirst()){
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri){
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
