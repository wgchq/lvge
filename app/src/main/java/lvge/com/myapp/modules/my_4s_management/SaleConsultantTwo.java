package lvge.com.myapp.modules.my_4s_management;

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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lvge.com.myapp.MainActivity;
import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.model.LoginResultModel;
import okhttp3.Response;

public class SaleConsultantTwo extends AppCompatActivity {

    public static final int CUT_PICTURE = 1;
    public static final int SHOW_PICTURE =2;
    public ImageView sale_consultant_two_iamgeview;
    private Uri imageUri = null;
    private String path = null;
    private  File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_consultant_two);

        RelativeLayout sale_consultant_two_iamge = (RelativeLayout)findViewById(R.id.sale_consultant_two_iamge);
        sale_consultant_two_iamgeview = (ImageView)findViewById(R.id.sale_consultant_two_iamgeview);
        TextView sale_consultant_Preservation = (TextView)findViewById(R.id.sale_consultant_Preservation);

        sale_consultant_Preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText et_rname = (EditText) findViewById(R.id.sale_consultant_inputname);
                    EditText et_phone = (EditText) findViewById(R.id.sale_consultant_inputphone);
                    EditText et_memo  = (EditText)findViewById(R.id.sale_consultant_inputmemo);
                    //file = new File(sale_consultant_two_iamgeview.getDrawable().toString());

                    OkHttpUtils.post()//get 方法
                            .url("http://www.lvgew.com/obdcarmarket/sellerapp/salesConsultant/save") //地址
                            .addParams("name", et_rname.getText().toString()) //需要传递的参数
                            .addParams("phone", et_phone.getText().toString())
                            .addParams("memo",et_memo.getText().toString())
                            .addParams("headImg",sale_consultant_two_iamgeview.getDrawable().toString())
                            .build()
                            .execute(new Callback() {//通用的callBack

                                //从后台获取成功后，对相应进行类型转化
                                @Override
                                public Object parseNetworkResponse(Response response, int i) throws Exception {

                                    String string = response.body().string();//获取相应中的内容Json格式
                                    //把json转化成对应对象
                                    //LoginResultModel是和后台返回值类型结构一样的对象
                                    LoginResultModel result = new Gson().fromJson(string, LoginResultModel.class);
                                    return result;
                                }

                                @Override
                                public void onError(okhttp3.Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(Object object, int i) {

                                    //object 是 parseNetworkResponse的返回值
                                    if (null != object) {
                                        LoginResultModel result = (LoginResultModel) object;//把通用的Object转化成指定的对象
                                        if (result.getOperationResult().getResultCode() == 0) {//当返回值为2时不可登录
                                            sale_consultant_two_iamgeview.setDrawingCacheEnabled(false);
                                            Toast.makeText(SaleConsultantTwo.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                        } else {

                                        }
                                    } else {//当没有返回对象时，表示网络没有联通
                                        Toast.makeText(SaleConsultantTwo.this, "网络异常！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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
                startActivityForResult(intent,1);
            }
        });
    }
    public String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
       if(requestCode == 1)
           imageUri = data.getData();
        if(imageUri != null){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                sale_consultant_two_iamgeview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos =new ByteArrayOutputStream();// outputstream  
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组  
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }


}
