package lvge.com.myapp.ui;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import lvge.com.myapp.R;
import lvge.com.myapp.broadcast.NetBroadcastReceiver;

/**
 * Created by JGG on 2017-08-23.
 */


public class NetworkCheckView extends LinearLayout {

    private String ErrorMessage;
    private View view;

    public NetworkCheckView(Context context) {
        this(context, null);
    }

    public NetworkCheckView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //加载视图的布局
       inflate(getContext(),R.layout.layout_frame_with_network_check,this);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkCheckView);
        ErrorMessage = a.getString(R.styleable.NetworkCheckView_ErrorMessage);

        //回收资源，这一句必须调用
        a.recycle();
    }


    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        TextView txt_network_check_message = (TextView) findViewById(R.id.txt_network_check_message);
        txt_network_check_message.bringToFront();

        NetBroadcastReceiver receiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(receiver, intentFilter);
    }
}
