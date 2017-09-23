package lvge.com.myapp.modules.shop_management;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lvge.com.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint({"NewApi", "ValidFragment"})
public class NotPassAuthenticationFragment extends Fragment {

    private Handler mHandler;
    public NotPassAuthenticationFragment()
    {
    }
    public NotPassAuthenticationFragment(Handler mHandler) {
        // Required empty public constructor
        this.mHandler = mHandler;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_shop_manage_not_pass_autenticaiton, container, false);
        Button btn_edit_authentication_information = (Button) view.findViewById(R.id.btn_edit_authentication_information);
        btn_edit_authentication_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ShopManageAuthenticationActivity.class);
//                startActivity(intent);
                mHandler.sendEmptyMessage(ShopManagementParameter.AUTHENTIC_NOTAUTHENTIC_PAGE);
            }
        });
//        return inflater.inflate(R.layout.fragment_shop_manage_not_pass_autenticaiton, container, false);
        return view;
    }

    public void show(View view){

    }

}
