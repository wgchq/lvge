package lvge.com.myapp.modules.shop_management;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lvge.com.myapp.R;

/**
 * Created by cnhao on 2017/8/29.
 */

public class HasPassAuthenticationFragment extends Fragment{
    public HasPassAuthenticationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_manage_has_pass_authentication, container, false);
//        Button btn_authentication_complete = (Button) view.findViewById(R.id.btn_authentication_complete);
//        btn_authentication_complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });

        return view;
    }

}
