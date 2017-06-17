package lvge.com.myapp.moduels.commodity_management;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lvge.com.myapp.R;

public class CommodityHomepageApplication extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return  inflater.inflate(R.layout.activity_commodity_homepage_application, container, false);

    }
}
