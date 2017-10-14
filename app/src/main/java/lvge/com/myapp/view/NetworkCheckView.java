package lvge.com.myapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import lvge.com.myapp.R;
import lvge.com.myapp.util.NetworkUtil;

/**
 * Created by JGG on 2017-08-23.
 */


public class NetworkCheckView extends LinearLayout {

    private String ErrorMessage;
    private View view;
    BroadcastReceiver receiver;

    public NetworkCheckView(Context context) {
        this(context, null);
    }

    public NetworkCheckView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //加载视图的布局
        view  = inflate(getContext(), R.layout.layout_frame_with_network_check, this);
        view.setVisibility(GONE);
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkCheckView);
        ErrorMessage = a.getString(R.styleable.NetworkCheckView_ErrorMessage);

        //回收资源，这一句必须调用
        a.recycle();
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        getContext().unregisterReceiver(receiver);
    }


    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

         receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int status = NetworkUtil.getConnectivityStatusString(context);

                TextView txt_network_check_message = (TextView) findViewById(R.id.txt_network_check_message);
                if (status == 3) {

                    view.setVisibility(VISIBLE);
                } else {
                    view.setVisibility(GONE);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(receiver, intentFilter);
    }
}
