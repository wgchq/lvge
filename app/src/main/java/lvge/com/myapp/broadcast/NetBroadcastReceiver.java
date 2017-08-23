package lvge.com.myapp.broadcast;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;

import lvge.com.myapp.util.NetworkUtil;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * Created by zhang on 2017/8/19.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
  /*      new AlertDialog.Builder(context)
                .setTitle("Warnning")
                .setMessage(
                        "网络断开")
                .create().show();*/


        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

    }

}
