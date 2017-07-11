package lvge.com.myapp.modules.customer_management;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lvge.com.myapp.R;

/**
 * Created by mac on 2017/7/11.
 */

public class MyCustomerAllFragement extends Fragment {
    public MyCustomerAllFragement(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.customer_listview_all,container,false);
    }
}
