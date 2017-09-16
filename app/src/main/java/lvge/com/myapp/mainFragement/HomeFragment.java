package lvge.com.myapp.mainFragement;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.broadcast.NetBroadcastReceiver;
import lvge.com.myapp.modules.PendingSendGoods.PendingSendGoodsActivity;
import lvge.com.myapp.modules.RefundAfterSale.RefundAfterSaleActivity;
import lvge.com.myapp.modules.ValidationHistory.ValidationHistoryActivity;
import lvge.com.myapp.modules.ValidationTypeScanQR.ValidationTypeScanQRActivity;
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
import lvge.com.myapp.ui.CustomKeyboard;
import lvge.com.myapp.util.KeyboardUtil;
import lvge.com.myapp.util.NetworkUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver broadcastReceiver;
    private Context ctx;
    private Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

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


        TextView img_main_page_car_data_management = (TextView) view.findViewById(R.id.img_main_page_car_data_management);
        img_main_page_car_data_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarDataManagementActivity.class);
                startActivity(intent);
            }
        });
        TextView img_main_page_commodity_management = (TextView) view.findViewById(R.id.img_main_page_commodity_management);
        img_main_page_commodity_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommodityManageHomepage.class);
                startActivity(intent);
            }
        });


        TextView img_main_page_coupon = (TextView) view.findViewById(R.id.img_main_page_coupon);
        img_main_page_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent);
            }
        });
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


        TextView img_main_page_financial_management = (TextView) view.findViewById(R.id.img_main_page_financial_management);
        img_main_page_financial_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinancialManagementActivity.class);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(getActivity(), ValidationTypeScanQRActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_validation_history = (TextView) view.findViewById(R.id.tv_validation_history);
        tv_validation_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ValidationHistoryActivity.class);
                startActivity(intent);
            }
        });


        TextView tv_refund_after_sale = (TextView) view.findViewById(R.id.tv_refund_after_sale);
        tv_refund_after_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RefundAfterSaleActivity.class);
                startActivity(intent);
            }
        });

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

        TextView tv_alert = (TextView) view.findViewById(R.id.tv_alert);
        tv_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlertClientActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_crawl = (TextView) view.findViewById(R.id.tv_crawl);
        tv_crawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrawlClientActivity.class);
                startActivity(intent);
            }
        });
        final EditText validation_code_input = (EditText) view.findViewById(R.id.validation_code_input);
       /* act = getActivity();
        ctx =getActivity();

        validation_code_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int input = validation_code_input.getInputType();

                validation_code_input.setInputType(InputType.TYPE_NULL);

                new KeyboardUtil(act, ctx, validation_code_input).showKeyboard();
                return false;
            }
        });*/
           CustomKeyboard validation_keyboard = new CustomKeyboard(getActivity());
        validation_keyboard.SetEditText(validation_code_input);
        validation_keyboard.setValidationListner(new CustomKeyboard.OnValidationLisnter() {
            @Override
            public void OnValidation() {
                Toast.makeText(getActivity(),"验证通过",Toast.LENGTH_SHORT).show();
            }
        });

        ImageView ig_main_page_message = (ImageView) view.findViewById(R.id.ig_main_page_message);
        ig_main_page_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageManagementActivity.class);
                startActivity(intent);
            }
        });


        ;

        return view;
    }

    @Override
    public void onDestroy() {
       /* getActivity().unregisterReceiver(broadcastReceiver);*/
        super.onDestroy();
    }

}
