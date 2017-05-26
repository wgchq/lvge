package lvge.com.myapp;

import android.animation.FloatEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvge.com.myapp.ui.SlideMenu;

import static lvge.com.myapp.constant.Constant.CONSTACTS;
import static lvge.com.myapp.constant.Constant.SETTINGS;


public class MainPageActivity extends Activity {

    private SlideMenu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);
        mMenu = (SlideMenu) findViewById(R.id.id_menu);

    }

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }
}
