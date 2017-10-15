package lvge.com.myapp.modules.commodity_management;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;

import org.json.JSONObject;

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
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;
import lvge.com.myapp.http.NetworkConfig;
import lvge.com.myapp.model.CommodityNewgoodsGiftAddMode;
import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.my_4s_management.BaseTest;
import lvge.com.myapp.modules.my_4s_management.ListUtil;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationAdd;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationAddPost;
import lvge.com.myapp.modules.right_side_slider_menu_mansgement.EmployeeInformationPostAdd;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.Response;

public class CommodityNewgoodsGiftAdd extends AppCompatActivity implements View.OnClickListener, TakePhoto.TakeResultListener,InvokeListener {

    private EditText commodity_newgoods_addgift;
    private EditText commodity_newgoods_description;
    private TextView commodity_newgoods_gift_add;
    private ImageView commodity_newgoods_giftFigure;

    private Bitmap bitmap;
    private CustomProgressDialog progressDialog = null;

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
        setContentView(R.layout.activity_commodity_newgoods_gift_add);

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        commodity_newgoods_addgift = (EditText)findViewById(R.id.commodity_newgoods_addgift);
        commodity_newgoods_description = (EditText)findViewById(R.id.commodity_newgoods_description);
        commodity_newgoods_giftFigure = (ImageView)findViewById(R.id.commodity_newgoods_giftFigure);
        commodity_newgoods_gift_add = (TextView)findViewById(R.id.commodity_newgoods_gift_add);
        commodity_newgoods_gift_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commodity_newgoods_giftFigure.setDrawingCacheEnabled(true);
                bitmap = commodity_newgoods_giftFigure.getDrawingCache();

                final String name = commodity_newgoods_addgift.getText().toString();
                final String descript = commodity_newgoods_description.getText().toString();

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            startProgerssDialog();
                            post_str(name,descript);
                        } catch ( Exception e) {
                            e.printStackTrace();
                            stopProgressDialog();
                        }
                    }
                };

                new Thread() {
                    public void run() {
                        Looper.prepare();
                        new Handler().post(runnable);
                        Looper.loop();
                    }
                }.start();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                getTakePhoto().onPickFromGalleryWithCrop(fileUri, cropOptions);
                break;
            case R.id.take_photo:
                fileUri = getImageCropUri();
                getTakePhoto().onPickFromCaptureWithCrop(fileUri, cropOptions);
                break;
            case R.id.cancel:
                dialog.dismiss();
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
                commodity_newgoods_giftFigure.setImageBitmap(bitmap);
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

    private void post_str(String username, String scription) {

        try {
            String path = NetworkConfig.BASE_URL+"sellerapp/goods/giftAddOrUpdate";
            List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap());

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", username);
            map.put("giftDesc", scription);

            String str = imgPut(path, filePaths, map);
            returnMessage(str);

        } catch (Exception e) {
            e.printStackTrace();
            returnMessage("error");
        }
    }

    private String saveBitmap() throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();

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


    private void returnMessage(String string) {
        Message msg = new Message();
        if(string.equals("error")){
            msg.what = 1;
            mHander.sendMessage(msg);
            return;
        }

        CommodityNewgoodsGiftAddMode result = new Gson().fromJson(string, CommodityNewgoodsGiftAddMode.class);

        if(result.getOperationResult().getResultCode() == 0){

            msg.what = 0;
            mHander.sendMessage(msg);
        }else {
            msg.what = 1;
            mHander.sendMessage(msg);
        }
    }

    Handler mHander = new Handler() {
        public void handleMessage(Message msg){
            stopProgressDialog();
            commodity_newgoods_giftFigure.setDrawingCacheEnabled(false);
            switch (msg.what){
                case 0:
                    Toast.makeText(CommodityNewgoodsGiftAdd.this,"上传成功！",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 1:
                    Toast.makeText(CommodityNewgoodsGiftAdd.this,"上传失败！",Toast.LENGTH_LONG).show();
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

    public String imgPut(String path, List<String> filePaths, Map<String, Object> map) throws Exception {
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符

        // 发送POST请求
        URL url = new URL(path);
        //String sa = String.valueOf(new CookieJarImpl(new MemoryCookieStore()));
        //  CookieStore store = OkHttpUtils.getInstance().getOkHttpClient().
        ///  List<Cookie> URIS = cookieJar.;
        CookieJarImpl cookieJarImpl = (CookieJarImpl)OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        CookieStore store =cookieJarImpl.getCookieStore();        // 得到所有的 URI
        List<Cookie> uris = store.getCookies();
        String responsea = uris.toString();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cookie", responsea);
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        OutputStream out = new DataOutputStream(conn.getOutputStream());

        // *****************************其他参数的设置*********************************
        if (map != null) {
            Iterator iter = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String inputName = (String) entry.getKey();
                String inputValue = (String) entry.getValue();
                if (inputValue == null) {
                    continue;
                }
                sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                sb.append(inputValue);
            }

            out.write(sb.toString().getBytes());
        }
        // *****************************其他参数的设置
        // end*********************************

        // ************************文件和图片的设置*****************************
        if (ListUtil.hasValue(filePaths)) {
            for (String filePath : filePaths) {
                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                StringBuffer strBuf = new StringBuffer();
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                // strBuf.append("Content-Disposition: form-data; name='file" +
                // index + "'; filename=" + filename + "\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"p1"  + "\"; filename=\"" + filename + "\"\r\n");
                strBuf.append("Content-Type:multipart/form-data" + "\r\n\r\n");

                out.write(strBuf.toString().getBytes());

                InputStream is = new FileInputStream(filePath);
                DataInputStream i = new DataInputStream(is);

                int bytes = 0;
                byte[] bufferOut = new byte[1024 * 500 * 3];
                while ((bytes = i.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                is.close();
            }
        }
        // ************************文件和图片的设置 end*****************************

        byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
        out.write(endData);

        out.flush();
        out.close();

        InputStream ins = null;
        try {
            ins = conn.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int in;
            while ((in = ins.read()) != -1) {
                baos.write(in);
            }
            String str = baos.toString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
        return null;
    }

    public static HttpCookie getcookies(){

        HttpCookie res = null;
        // 使用 Cookie 的时候：
        // 取出 CookieStore
        CookieJarImpl cookieJarImpl = (CookieJarImpl)OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        CookieStore store =cookieJarImpl.getCookieStore();        // 得到所有的 URI
        List<Cookie> uris = store.getCookies();
        /**
         for (URI ur : uris) {
         // 筛选需要的 URI
         // 得到属于这个 URI 的所有 Cookie
         List<HttpCookie> cookies = store.get(ur);
         for (HttpCookie coo : cookies) {
         res = coo;
         }
         }
         **/
        return res;
    }
}
