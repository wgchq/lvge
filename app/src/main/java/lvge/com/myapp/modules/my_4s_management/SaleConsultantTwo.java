package lvge.com.myapp.modules.my_4s_management;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.util.Base64;
import android.view.View;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Call;
import okhttp3.Response;

public class SaleConsultantTwo extends AppCompatActivity {

    public static final int CUT_PICTURE = 1;
    public static final int SHOW_PICTURE = 2;
    public ImageView sale_consultant_two_iamgeview=null;
    private Uri imageUri = null;
    private String path = null;
    private File file;
    private Bitmap bitmap;

    private ProgressDialog progDialog = null;

    private static SaleConsultantTwo parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_consultant_two);

        final RelativeLayout sale_consultant_two_iamge = (RelativeLayout) findViewById(R.id.sale_consultant_two_iamge);
        sale_consultant_two_iamgeview = (ImageView) findViewById(R.id.sale_consultant_two_iamgeview);
        TextView sale_consultant_Preservation = (TextView) findViewById(R.id.sale_consultant_Preservation);

        ImageView sale_consultant_back = (ImageView) findViewById(R.id.sale_consultant_back);
        sale_consultant_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final EditText et_rname = (EditText) findViewById(R.id.sale_consultant_inputname);
                    final EditText et_phone = (EditText) findViewById(R.id.sale_consultant_inputphone);
                    final EditText et_memo = (EditText) findViewById(R.id.sale_consultant_inputmemo);

                    //String strBitmap = convertIconToString(sale_consultant_two_iamgeview.getDrawingCache());
                    //   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    //  bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                    //  byte[] data = bos.toByteArray();
                    //  String strBitmap = new String(data);
                    file = new File(saveBitmap(et_rname.getText().toString()));
                    // FileOutputStream fo = new FileOutputStream(file);
                    // Map<String,File> fileMap = new HashMap<String, File>();
                    //fileMap.put("headImg",file);
                    if (!file.exists()) {
                        Toast.makeText(SaleConsultantTwo.this, "图片错误！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showProgressDialog();

                     new Thread() {
                        public void run() {
                            try {
                               // showProgressDialog();
                                post_str(et_rname.getText().toString(), et_phone.getText().toString(), et_memo.getText().toString(), saveBitmap(et_rname.getText().toString()));
                            } catch (IOException e) {
                                e.printStackTrace();
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


        sale_consultant_two_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

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
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                    if (bitmap != null) {
                        sale_consultant_two_iamgeview.setImageBitmap(bitmap);

                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private String saveBitmap(String strfilename) throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();

        // Bitmap bitm = convertViewToBitMap(sale_consultant_two_iamgeview);
        String strPath = Environment.getExternalStorageDirectory().toString() + "/save";

        try {
            File desDir = new File(strPath);
            if (!desDir.exists()) {
                desDir.mkdir();
            }

            File imageFile = new File(strPath + "/" + strfilename + ".PNG");
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strPath + "/" + strfilename + ".PNG";
    }

    private void post_str(String name, String phone, String memo, String strpath) {

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

            String path = "http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save";

            List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap(name));

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            map.put("phone", phone);
            map.put("memo", memo);

            BaseTest bs = new BaseTest();
            String str = bs.imgPut(path, filePaths, map);
            returnMessage(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnMessage(String string) {

        LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
        Message msg = new Message();
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
        progDialog.setMessage("正在上传：。。。");
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
            switch (msg.what){
                case 0:
                    Toast.makeText(SaleConsultantTwo.this,"上传成功！",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(SaleConsultantTwo.this,"上传失败！",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };

}
