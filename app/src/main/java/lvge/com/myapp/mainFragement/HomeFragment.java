package lvge.com.myapp.mainFragement;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;
import lvge.com.myapp.modules.car_data_management.CarDataManagementActivity;
import lvge.com.myapp.modules.commodity_management.CommodityManageHomepage;
import lvge.com.myapp.modules.coupon.CouponActivity;
import lvge.com.myapp.modules.evaluation_management.EvaluationManagementActivity;
import lvge.com.myapp.modules.fence_management.FenceManagementActivity;
import lvge.com.myapp.modules.financial_management.FinancialManagementActivity;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivity;
import lvge.com.myapp.modules.performance_analysis_management.PerformanceAnalysisManagementActivity;
import lvge.com.myapp.modules.royalty_management.RoyaltyManagementActivity;
import lvge.com.myapp.modules.shop_management.ShopManagementActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //主页面跳转各个子模块

        ImageView img_main_page_shop_management = (ImageView) view.findViewById(R.id.img_main_page_shop_management);

        img_main_page_shop_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopManagementActivity.class);
                startActivity(intent);
            }
        });


        ImageView img_main_page_car_data_management = (ImageView) view.findViewById(R.id.img_main_page_car_data_management);
        img_main_page_car_data_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarDataManagementActivity.class);
                startActivity(intent);
            }
        });
        ImageView img_main_page_commodity_management = (ImageView) view.findViewById(R.id.img_main_page_commodity_management);
        img_main_page_commodity_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommodityManageHomepage.class);
                startActivity(intent);
            }
        });


        ImageView img_main_page_coupon = (ImageView) view.findViewById(R.id.img_main_page_coupon);
        img_main_page_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent);
            }
        });
        ImageView img_main_page_evaluation_management = (ImageView) view.findViewById(R.id.img_main_page_evaluation_management);
        img_main_page_evaluation_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EvaluationManagementActivity.class);
                startActivity(intent);
            }
        });


        ImageView img_main_page_my_4s_management = (ImageView) view.findViewById(R.id.img_main_page_my_4s_management);
        img_main_page_my_4s_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My4sManagementActivity.class);
                startActivity(intent);
            }
        });


        ImageView img_main_page_financial_management = (ImageView) view.findViewById(R.id.img_main_page_financial_management);
        img_main_page_financial_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinancialManagementActivity.class);
                startActivity(intent);
            }
        });

        ImageView img_main_page_royalty_management = (ImageView) view.findViewById(R.id.img_main_page_royalty_management);
        img_main_page_royalty_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoyaltyManagementActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
