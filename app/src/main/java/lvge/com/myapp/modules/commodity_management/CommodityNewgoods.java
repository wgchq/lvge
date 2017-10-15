package lvge.com.myapp.modules.commodity_management;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.kyleduo.switchbutton.SwitchButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;

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
import lvge.com.myapp.modules.my_4s_management.ListUtil;
import okhttp3.Cookie;

public class CommodityNewgoods extends AppCompatActivity implements View.OnClickListener,TakePhoto.TakeResultListener,InvokeListener {

    private SwitchButton commodity_newgoods_switchbutton;
    private EditText commodity_newgoods_commodityname;   //商品名称
    private ImageView commodity_newgoods_mainFigure;   //主图
    private ImageView commodity_newgoods_Figure1;  //附图
    private ImageView commodity_newgoods_Figure2;
    private ImageView commodity_newgoods_Figure3;
    private RelativeLayout commodity_newgoods_relayout_type;  //商品类型
    private TextView commodity_newgoods_type;
    private RelativeLayout commodity_newgoods_Relayout_inventory;  //商品库存
    private RelativeLayout commodity_newgoods_Relayout_installation;  //门店安装
    private RelativeLayout commodity_newgoods_Relayout_royalty;  //安装提成
    private RelativeLayout commodity_newgoods_Relayout_cartype; //安装车型
    private RelativeLayout commodity_newgoods_Relayout_freight;  //运费
    private RelativeLayout commodity_newgoods_Relayout_typechoose; //品种
    private TextView commodity_newgoods_cultivartypechoose;
    private TextView commodity_newgoods_install;
    private TextView commodity_cultivar_type;
    private TextView commodity_newgoods_textviewInstall;
    private RelativeLayout commodity_nowgoods_Relayout_parameter;  //商品参数
    private RelativeLayout commodity_newgoods_Relayout_brand;
    private RelativeLayout commodity_newgoods_Relayout_specification;
    private RelativeLayout commodity_newgoods_Relayout_commodityAddress;
    private RelativeLayout commodity_newgoods_Realyout_question;  //常见问题
    private ImageView commodity_newgoods_CommodityParameter;
    private TextView commodity_newgoods_cartype_text;
    private RelativeLayout commodity_goodnews_Relayout_description;   //图文描述
    private TextView commodity_goodnews_description;
    private RelativeLayout commodity_newgoods_Relayout_gift;   //赠品
    private TextView commodity_newgoods_gift;
    private TextView commodity_newgoods_freight;
    private TextView commodity_newgoods_shangjia;
    private EditText commodity_newgoods_commodityOriginalprice;
    private EditText commodity_newgoods_commoditySellingprice;
    private EditText commodity_newgoods_commodityDiscount;
    private EditText commodity_newgoods_commodityStock;
    private EditText commodity_newgoods_commodityBrand;
    private EditText commodity_newgoods_commodity_specification;
    private EditText commodity_newgoods_commodityAddress;

    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;

    private CropOptions cropOptions;
    private TakePhoto takePhoto_ta;
    private InvokeParam invokeParam;
    private Uri fileUri = null;
    private Dialog dialog;
    private int id_iamge;
    private View inflate;
    private CustomProgressDialog progressDialog = null;

    private boolean isshowOarameter = true;

    private String name = "";  //商品名字
    private Bitmap graphicDescription = null;
    private String giftid = "";
    private ArrayList<String> question = new ArrayList<String>();
    private ArrayList<String> answer = new ArrayList<String>();
    private Bitmap bitmap;
    List<String> filePaths = new ArrayList<>();  //图片存储
    private String type = "";   //商品类型
    private String inputcultivarchoose="";  //品种类型
    private String originalPrice;   //商品原始价格
    private String discountPrice;    //折扣价
    private String discountInfo;   //折扣信息描述
    private String stock;   //商品库存
    private String properties;  //商品品牌
    private String brand_series; //品牌ID+车系ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_newgoods);

        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();

        commodity_newgoods_commodityname = (EditText)findViewById(R.id.commodity_newgoods_commodityname);
        commodity_newgoods_mainFigure = (ImageView)findViewById(R.id.commodity_newgoods_mainFigure);
        commodity_newgoods_Figure1 = (ImageView)findViewById(R.id.commodity_newgoods_Figure1);
        commodity_newgoods_Figure2 = (ImageView)findViewById(R.id.commodity_newgoods_Figure2);
        commodity_newgoods_Figure3 = (ImageView)findViewById(R.id.commodity_newgoods_Figure3);
        commodity_newgoods_relayout_type = (RelativeLayout) findViewById(R.id.commodity_newgoods_relayout_type);
        commodity_newgoods_type = (TextView)findViewById(R.id.commodity_newgoods_type);
        commodity_newgoods_Relayout_inventory = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_inventory);
        commodity_newgoods_Relayout_installation = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_installation);
        commodity_newgoods_Relayout_royalty = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_royalty);
        commodity_newgoods_Relayout_cartype = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_cartype);
        commodity_newgoods_Relayout_freight = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_freight);
        commodity_newgoods_Relayout_typechoose = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_typechoose);
        commodity_newgoods_cultivartypechoose = (TextView)findViewById(R.id.commodity_newgoods_cultivartypechoose);
        commodity_newgoods_install = (TextView)findViewById(R.id.commodity_newgoods_install);
        commodity_cultivar_type = (TextView)findViewById(R.id.commodity_cultivar_type);
        commodity_newgoods_textviewInstall = (TextView)findViewById(R.id.commodity_newgoods_textviewInstall);
        commodity_nowgoods_Relayout_parameter = (RelativeLayout)findViewById(R.id.commodity_nowgoods_Relayout_parameter);
        commodity_newgoods_Relayout_brand = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_brand);
        commodity_newgoods_Relayout_specification = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_specification);
        commodity_newgoods_Relayout_commodityAddress = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_commodityAddress);
        commodity_newgoods_CommodityParameter = (ImageView)findViewById(R.id.commodity_newgoods_CommodityParameter);
        commodity_newgoods_cartype_text = (TextView)findViewById(R.id.commodity_newgoods_cartype_text);
        commodity_newgoods_Realyout_question = (RelativeLayout)findViewById(R.id.commodity_newgoods_Realyout_question);
        commodity_goodnews_Relayout_description = (RelativeLayout)findViewById(R.id.commodity_goodnews_Relayout_description);
        commodity_newgoods_Relayout_gift = (RelativeLayout)findViewById(R.id.commodity_newgoods_Relayout_gift);
        commodity_goodnews_description = (TextView)findViewById(R.id.commodity_goodnews_description);
        commodity_newgoods_gift = (TextView)findViewById(R.id.commodity_newgoods_gift);
        commodity_newgoods_freight = (TextView)findViewById(R.id.commodity_newgoods_freight);
        commodity_newgoods_shangjia = (TextView)findViewById(R.id.commodity_newgoods_shangjia);
        commodity_newgoods_commodityOriginalprice = (EditText)findViewById(R.id.commodity_newgoods_commodityOriginalprice);
        commodity_newgoods_commoditySellingprice = (EditText)findViewById(R.id.commodity_newgoods_commoditySellingprice);
        commodity_newgoods_commodityDiscount = (EditText)findViewById(R.id.commodity_newgoods_commodityDiscount);
        commodity_newgoods_commodityStock = (EditText)findViewById(R.id.commodity_newgoods_commodityStock);
        commodity_newgoods_commodityBrand = (EditText)findViewById(R.id.commodity_newgoods_commodityBrand);
        commodity_newgoods_commodity_specification = (EditText)findViewById(R.id.commodity_newgoods_commodity_specification);
        commodity_newgoods_commodityAddress = (EditText)findViewById(R.id.commodity_newgoods_commodityAddress);

        commodity_newgoods_relayout_type.setOnClickListener(this);
        commodity_newgoods_Relayout_typechoose.setOnClickListener(this);
        commodity_newgoods_Relayout_installation.setOnClickListener(this);
        commodity_newgoods_Relayout_royalty.setOnClickListener(this);
        commodity_nowgoods_Relayout_parameter.setOnClickListener(this);
        commodity_newgoods_Relayout_cartype.setOnClickListener(this);
        commodity_newgoods_Realyout_question.setOnClickListener(this);
        commodity_goodnews_Relayout_description.setOnClickListener(this);
        commodity_newgoods_Relayout_gift.setOnClickListener(this);
        commodity_newgoods_Relayout_freight.setOnClickListener(this);
        commodity_newgoods_shangjia.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_management_list);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commodity_newgoods_switchbutton = (SwitchButton)findViewById(R.id.commodity_newgoods_switchbutton);
        commodity_newgoods_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    commodity_newgoods_switchbutton.setBackColor(ColorStateList.valueOf(Color.RED));
                }else {
                    commodity_newgoods_switchbutton.setBackColor(ColorStateList.valueOf(Color.LTGRAY));
                }
            }
        });
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
            case R.id.commodity_newgoods_relayout_type:
                Intent intent = new Intent(CommodityNewgoods.this,CommodityNewgoodsCommdotyType.class);
                startActivityForResult(intent,10);
                break;
            case R.id.commodity_newgoods_Relayout_typechoose:
                Intent intent1 = new Intent(CommodityNewgoods.this,CommodityCultivarTypeChoice.class);
                startActivityForResult(intent1,11);
                break;
            case R.id.commodity_newgoods_Relayout_installation:
                Intent intent2 = new Intent(CommodityNewgoods.this,CommodityInstall.class);
                startActivityForResult(intent2,12);
                break;
            case R.id.commodity_newgoods_Relayout_royalty:
                Intent intent3 = new Intent(CommodityNewgoods.this,CommodityCultivarType.class);
                startActivityForResult(intent3,13);
                break;
            case R.id.commodity_nowgoods_Relayout_parameter:
                if (isshowOarameter){
                    isshowOarameter = false;
                    commodity_newgoods_Relayout_brand.setVisibility(View.GONE);
                    commodity_newgoods_Relayout_specification.setVisibility(View.GONE);
                    commodity_newgoods_Relayout_commodityAddress.setVisibility(View.GONE);
                    commodity_newgoods_Relayout_cartype.setVisibility(View.GONE);
                    commodity_newgoods_CommodityParameter.setImageResource(R.mipmap.commodity_list_gre_xiala);
                }else {
                    isshowOarameter = true;
                    commodity_newgoods_Relayout_brand.setVisibility(View.VISIBLE);
                    commodity_newgoods_Relayout_specification.setVisibility(View.VISIBLE);
                    commodity_newgoods_Relayout_commodityAddress.setVisibility(View.VISIBLE);
                    commodity_newgoods_Relayout_cartype.setVisibility(View.VISIBLE);
                    commodity_newgoods_CommodityParameter.setImageResource(R.mipmap.menu_dropdown);
                }
                break;
            case R.id.commodity_newgoods_Relayout_cartype:
                Intent intent4 = new Intent(CommodityNewgoods.this,CommodityNewgoods_CarType_Choose.class);
                startActivityForResult(intent4,14);
                break;
            case R.id.commodity_newgoods_Realyout_question:
                Intent intent5 = new Intent(CommodityNewgoods.this,CommodityCommonProblem.class);
                startActivityForResult(intent5,15);
                break;
            case R.id.commodity_goodnews_Relayout_description:
                Intent intent6 = new Intent(CommodityNewgoods.this,CommodityNewgoodsGraphicDescription.class);
                startActivityForResult(intent6,16);
                break;
            case R.id.commodity_newgoods_Relayout_gift:
                Intent intent7 = new Intent(CommodityNewgoods.this,CommodityNewgoodsGift.class);
                startActivityForResult(intent7,17);
                break;
            case R.id.commodity_newgoods_Relayout_freight:
                Intent intent8 = new Intent(CommodityNewgoods.this,CommodityNewgoodsFreight.class);
                startActivityForResult(intent8,18);
                break;
            case R.id.commodity_newgoods_shangjia:
                try {
                    name = commodity_newgoods_commodityname.getText().toString();
                    commodity_newgoods_mainFigure.setDrawingCacheEnabled(true);
                    filePaths.add(saveBitmap("main"));
                    commodity_newgoods_mainFigure.setDrawingCacheEnabled(false);
                    commodity_newgoods_Figure1.setDrawingCacheEnabled(true);
                    filePaths.add(saveBitmap("p1"));
                    commodity_newgoods_Figure1.setDrawingCacheEnabled(false);
                    commodity_newgoods_Figure2.setDrawingCacheEnabled(true);
                    filePaths.add(saveBitmap("p2"));
                    commodity_newgoods_Figure2.setDrawingCacheEnabled(false);
                    commodity_newgoods_Figure3.setDrawingCacheEnabled(true);
                    filePaths.add(saveBitmap("p3"));
                    commodity_newgoods_Figure3.setDrawingCacheEnabled(false);
                    originalPrice = commodity_newgoods_commodityOriginalprice.getText().toString();
                    discountPrice = commodity_newgoods_commoditySellingprice.getText().toString();
                    discountInfo = commodity_newgoods_commodityDiscount.getText().toString();
                    stock = commodity_newgoods_commodityStock.getText().toString();
                    properties = commodity_newgoods_commodityBrand.getText().toString() + "@@" + commodity_newgoods_commodity_specification.getText().toString() + "@@" + commodity_newgoods_commodityAddress.getText().toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                addNewgoods();
                break;
            default:
                break;
        }
    }

    private void addNewgoods(){

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    startProgerssDialog();
                    post_str(name);
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

    private void post_str(String name) {

        try {
            String path = NetworkConfig.BASE_URL+"sellerapp/goods/giftAddOrUpdate";
          /*  List<String> filePaths = new ArrayList<>();
            filePaths.add(saveBitmap());*/

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            map.put("type",type);

            String str = imgPut(path, filePaths, map);
            returnMessage(str);

        } catch (Exception e) {
            e.printStackTrace();
            returnMessage("error");
        }
    }

    private String saveBitmap(String name) throws IOException {
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

            File imageFile = new File(strPath + "/"+ name + ".PNG");
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
        return strPath + "/"+ name + ".PNG";
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
           // commodity_newgoods_giftFigure.setDrawingCacheEnabled(false);
            switch (msg.what){
                case 0:
                   // Toast.makeText(CommodityNewgoodsGiftAdd.this,"上传成功！",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 1:
                   // Toast.makeText(CommodityNewgoodsGiftAdd.this,"上传失败！",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (10 == requestCode && data != null) {
            String inputcommoditychoose = data.getStringExtra("inputcommoditychoose");
            commodity_newgoods_type.setText(inputcommoditychoose);
            if (inputcommoditychoose.equals("虚拟商品")){
                type = "0";
                commodity_newgoods_Relayout_inventory.setVisibility(View.GONE);
                commodity_newgoods_Relayout_installation.setVisibility(View.GONE);
                commodity_newgoods_Relayout_royalty.setVisibility(View.GONE);
                commodity_newgoods_Relayout_cartype.setVisibility(View.GONE);
                commodity_newgoods_Relayout_freight.setVisibility(View.GONE);
            }else {
                type = "1";
            }
        }else  if (11 == requestCode && data != null) {
            inputcultivarchoose = data.getStringExtra("inputcultivarchoose");
            commodity_newgoods_cultivartypechoose.setText(inputcultivarchoose);
        }else if(12 == requestCode && data != null){
            String inputinstallchoose = data.getStringExtra("inputinstallchoose");
            commodity_newgoods_install.setText(inputinstallchoose);
        }else if(13 == requestCode && data != null){
            String inputcommoditychoose = data.getStringExtra("inputcommoditychoose");
            String inputhowmonet = data.getStringExtra("inputhowmonet");
            commodity_cultivar_type.setText(inputcommoditychoose);
            commodity_newgoods_textviewInstall.setText(inputhowmonet);
        }else if(14 == requestCode && data != null){
            brand_series = data.getStringExtra("inputcarchoose");
            commodity_newgoods_cartype_text.setText(brand_series);
        }else if(15 == requestCode && data != null){
            answer = data.getStringArrayListExtra("answer");
            question = data.getStringArrayListExtra("question");
        } else if(16 == requestCode && data != null){
            Uri uri = Uri.parse(data.getStringExtra("graphicDescription"));
            try {
                graphicDescription = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if(graphicDescription != null){
                    commodity_goodnews_description.setText("已设置");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (17 == requestCode && data != null){
            giftid = data.getStringExtra("inputid");
            commodity_newgoods_gift.setText(data.getStringExtra("inputgift"));
        }else if(18 == requestCode && data != null){
            commodity_newgoods_freight.setText("运费" + data.getStringExtra("inputfreight") + "元");
        }
        if (null == dialog) {
        } else {
            dialog.dismiss();
        }

        getTakePhoto().onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,requestCode,data);
    }

    @Override
    public void takeSuccess(TResult result) {
        try{
            String strPath = result.getImage().getCompressPath();
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            if(bitmap != null){
                switch (id_iamge) {
                    case R.id.commodity_newgoods_mainFigure:
                        // Glide.with(this).load(strPath).into(my_4s_shop_pic_1);
                        commodity_newgoods_mainFigure.setImageBitmap(bitmap);
                      //  my_4s_pic_1_bool = true;
                        break;
                    case R.id.commodity_newgoods_Figure1:
                        commodity_newgoods_Figure1.setImageBitmap(bitmap);
                        //my_4s_pic_2_bool = true;
                        break;
                    case R.id.commodity_newgoods_Figure2:
                        commodity_newgoods_Figure2.setImageBitmap(bitmap);
                        break;
                    case R.id.commodity_newgoods_Figure3:
                        commodity_newgoods_Figure1.setImageBitmap(bitmap);
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
        CookieJarImpl cookieJarImpl = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
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
                strBuf.append("Content-Disposition: form-data; name=\"" + filename  + "\"; filename=\"" + filename + "\"\r\n");
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
