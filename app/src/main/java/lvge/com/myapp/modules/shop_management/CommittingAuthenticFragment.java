package lvge.com.myapp.modules.shop_management;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lvge.com.myapp.R;

/**
 * Created by cnhao on 2017/8/29.
 */

@SuppressLint({"NewApi", "ValidFragment"})
public class CommittingAuthenticFragment extends Fragment{
    public CommittingAuthenticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_manage_committing_authentication, container, false);

        return view;
    }
}
