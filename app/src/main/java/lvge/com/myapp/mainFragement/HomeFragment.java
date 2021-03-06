package lvge.com.myapp.mainFragement;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import lvge.com.myapp.model.LoginResultModel;
import lvge.com.myapp.modules.validationtypescanqr.MipcaActivityCaptureActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.modules.PendingSendGoods.PendingSendGoodsActivity;
import lvge.com.myapp.modules.RefundAfterSale.RefundAfterSaleActivity;
import lvge.com.myapp.modules.ValidationHistory.*;
import lvge.com.myapp.modules.alert_client_management.AlertClientActivity;
import lvge.com.myapp.modules.car_data_management.CarDataManagementActivity;
import lvge.com.myapp.modules.commodity_management.CommodityManageHomepage;
import lvge.com.myapp.modules.coupon.CouponActivity;
import lvge.com.myapp.modules.crawl_client_management.CrawlClientActivity;
import lvge.com.myapp.modules.evaluation_management.EvaluationManagementActivity;
import lvge.com.myapp.modules.financial_management.FinancialManagementActivity;
import lvge.com.myapp.modules.maintain_client_management.MaintainClientActivity;
import lvge.com.myapp.modules.message_management.MessageManagementActivity;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivity;
import lvge.com.myapp.modules.royalty_management.RoyaltyManagementActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;
import lvge.com.myapp.modules.validationtypescanqr.ValidationTypeScanQRFailActivity;
import lvge.com.myapp.modules.validationtypescanqr.ValidationTypeScanQRSuccessActivity;
import lvge.com.myapp.view.CustomKeyboard;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private  View view;
    private  CustomKeyboard validation_keyboard;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {

            if (intentResult.getContents() == null) {
                if(resultCode ==-1){
                    Toast.makeText(getActivity(), "无法获取内容", Toast.LENGTH_SHORT).show();
                }
                else if (resultCode==2) {
                    validation_keyboard.show();

                }

            } else {
                String str_qr =  intentResult.getContents();
                EditText validation_code_input = (EditText)view.findViewById(R.id.validation_code_input);
                validation_code_input.setText(str_qr);
            }
        }
    }

    public void ValidationQR(String strqr) {
        try {
            OkHttpUtils.get()//get 方法
                    .url("http://www.lvgew.com/obdcarmarket/sellerapp/code/codeUse") //地址
                    .addParams("CODE_STR", strqr) //需要传递的参数
                    .build()
                    .execute(new Callback() {//通用的callBack

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
                                if (result.getOperationResult().getResultCode() == 0) {//当返回值为0时可登录

                                    Intent intent = new Intent(getActivity(), ValidationTypeScanQRSuccessActivity.class);
                                    startActivity(intent);
                                    if (validation_keyboard!=null)
                                    {
                                        validation_keyboard.close();
                                    }
                                } else {
                                    Toast.makeText(getActivity(),result.getOperationResult().getResultMsg(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), ValidationTypeScanQRFailActivity.class);
                                    startActivity(intent);
                                    if (validation_keyboard!=null)
                                    {
                                        validation_keyboard.close();
                                    }
                                }
                            } else {//当没有返回对象时，表示网络没有联通
                                Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "网络异常！", Toast.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView main_page_content_shop_order = (TextView) view.findViewById(R.id.main_page_content_shop_order);
        main_page_content_shop_order.setText(getArguments().getString("name"));
        //  main_page_content_shop_order.setText(fragmentTitle.getShop_title());

        //主页面跳转各个子模块

        TextView img_main_page_shop_management = (TextView) view.findViewById(R.id.img_main_page_shop_management);

        img_main_page_shop_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopManagementActivity.class);
                startActivity(intent);
            }
        });

        //车数据
        TextView img_main_page_car_data_management = (TextView) view.findViewById(R.id.img_main_page_car_data_management);
        img_main_page_car_data_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarDataManagementActivity.class);
                startActivity(intent);
            }
        });

        //商品管理
        TextView img_main_page_commodity_management = (TextView) view.findViewById(R.id.img_main_page_commodity_management);
        img_main_page_commodity_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommodityManageHomepage.class);
                startActivity(intent);
            }
        });

        //优惠券
        TextView img_main_page_coupon = (TextView) view.findViewById(R.id.img_main_page_coupon);
        img_main_page_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent);
            }
        });

        //评价管理
        TextView img_main_page_evaluation_management = (TextView) view.findViewById(R.id.img_main_page_evaluation_management);
        img_main_page_evaluation_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EvaluationManagementActivity.class);
                startActivity(intent);
            }
        });


        TextView img_main_page_my_4s_management = (TextView) view.findViewById(R.id.img_main_page_my_4s_management);
        img_main_page_my_4s_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My4sManagementActivity.class);
                startActivity(intent);
            }
        });

        //财务管理
        TextView img_main_page_financial_management = (TextView) view.findViewById(R.id.img_main_page_financial_management);
        img_main_page_financial_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinancialManagementActivity.class);
                startActivity(intent);
            }
        });

        //提成管理
        TextView img_main_page_royalty_management = (TextView) view.findViewById(R.id.img_main_page_royalty_management);
        img_main_page_royalty_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoyaltyManagementActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_validation_type_scan_QR = (TextView) view.findViewById(R.id.tv_validation_type_scan_QR);
        tv_validation_type_scan_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanBarcode(v);
            }
        });

        //验证历史
        TextView tv_validation_history = (TextView) view.findViewById(R.id.tv_validation_history);
        tv_validation_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ValidationHistoryActivity.class);
                startActivity(intent);
            }
        });

        //退款售后
        TextView tv_refund_after_sale = (TextView) view.findViewById(R.id.tv_refund_after_sale);
        tv_refund_after_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RefundAfterSaleActivity.class);
                startActivity(intent);
            }
        });

        //代发货
        TextView tv_pending_send_goods = (TextView) view.findViewById(R.id.tv_pending_send_goods);
        tv_pending_send_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PendingSendGoodsActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_maintain = (TextView) view.findViewById(R.id.tv_maintain);
        tv_maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MaintainClientActivity.class);
                startActivity(intent);
            }
        });

        //保养客户
        TextView tv_alert = (TextView) view.findViewById(R.id.tv_alert);
        tv_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlertClientActivity.class);
                startActivity(intent);
            }
        });

        //围栏客户
        TextView tv_crawl = (TextView) view.findViewById(R.id.tv_crawl);
        tv_crawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrawlClientActivity.class);
                startActivity(intent);
            }
        });
        final EditText validation_code_input = (EditText) view.findViewById(R.id.validation_code_input);

        validation_keyboard = new CustomKeyboard(getActivity());
        validation_keyboard.SetEditText(validation_code_input);
        validation_keyboard.setValidationListner(new CustomKeyboard.OnValidationLisnter() {
            @Override
            public void OnValidation() {
                String strvalidation_code_input = validation_code_input.getText().toString();
                ValidationQR(strvalidation_code_input);
            }
        });

        //消息
        ImageView ig_main_page_message = (ImageView) view.findViewById(R.id.ig_main_page_message);
        ig_main_page_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageManagementActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setPrompt("请对准二维码进行扫描"); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        // integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(MipcaActivityCaptureActivity.class); // 设置自定义的activity是CustomActivity

        integrator.initiateScan();// 初始化扫描

    }

    @Override
    public void onDestroy() {
       /* getActivity().unregisterReceiver(broadcastReceiver);*/
        super.onDestroy();
    }

}
