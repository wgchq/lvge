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

import java.io.File;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.R;

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

        commodity_newgoods_relayout_type.setOnClickListener(this);
        commodity_newgoods_Relayout_typechoose.setOnClickListener(this);
        commodity_newgoods_Relayout_installation.setOnClickListener(this);
        commodity_newgoods_Relayout_royalty.setOnClickListener(this);
        commodity_nowgoods_Relayout_parameter.setOnClickListener(this);
        commodity_newgoods_Relayout_cartype.setOnClickListener(this);
        commodity_newgoods_Realyout_question.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_commodity_management_list);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SalesConsultant.this, My4sManagementActivity.class);
                // startActivity(intent);
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
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (10 == requestCode && data != null) {
            String inputcommoditychoose = data.getStringExtra("inputcommoditychoose");
            commodity_newgoods_type.setText(inputcommoditychoose);
            if (inputcommoditychoose.equals("虚拟商品")){
                commodity_newgoods_Relayout_inventory.setVisibility(View.GONE);
                commodity_newgoods_Relayout_installation.setVisibility(View.GONE);
                commodity_newgoods_Relayout_royalty.setVisibility(View.GONE);
                commodity_newgoods_Relayout_cartype.setVisibility(View.GONE);
                commodity_newgoods_Relayout_freight.setVisibility(View.GONE);
            }
        }else  if (11 == requestCode && data != null) {
            String inputcultivarchoose = data.getStringExtra("inputcultivarchoose");
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
            String inputcarchoose = data.getStringExtra("inputcarchoose");
            commodity_newgoods_cartype_text.setText(inputcarchoose);
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
}
