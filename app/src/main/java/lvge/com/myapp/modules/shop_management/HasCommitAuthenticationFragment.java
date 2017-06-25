package lvge.com.myapp.modules.shop_management;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lvge.com.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HasCommitAuthenticationFragment extends Fragment {


    public HasCommitAuthenticationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_manage_has_commit_authentication, container, false);
        Button btn_authentication_complete = (Button) view.findViewById(R.id.btn_authentication_complete);
        btn_authentication_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

}
