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
        String status = NetworkUtil.getConnectivityStatusString(context);
        /*ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
       *//* Class.forName(cn.getClassName().toString());*//*
        new AlertDialog.Builder()
                .setTitle("Warnning")
                .setMessage(
                        "You forget to write the message. Do you want to fill out it ??")
                .create().show();*/

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

    }

}
