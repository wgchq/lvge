package lvge.com.myapp.util;

/**
 * Created by JGG on 2017-09-07.
 * <p>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 *
 * @author way
 * <p>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * @author way
 */
/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 *
 * @author way
 *
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;

import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import lvge.com.myapp.ProgressDialog.CustomProgressDialog;
import lvge.com.myapp.WelcomePageActivity;

public class CrashHandler implements UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
    private static CrashHandler INSTANCE;// CrashHandler实例
    private Context mContext;// 程序的Context对象

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {

    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     *            异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(Throwable ex) {
        if (ex == null || mContext == null)
            return false;
        final String crashReport = getCrashReport(mContext, ex);
        Log.i("error", crashReport);

        new Thread() {
            public void run() {
                Looper.prepare();
                File file = save2File(crashReport);
                sendAppCrashReport(mContext, crashReport, file);
                Looper.loop();
            }

        }.start();
        return true;
    }

    private File save2File(String crashReport) {
        // TODO Auto-generated method stub
        String fileName = "crash-" + System.currentTimeMillis() + ".txt";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + "crash");
                if (!dir.exists())
                    dir.mkdir();
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(crashReport.toString().getBytes());
                fos.close();
                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void sendAppCrashReport(final Context context,
                                    final String crashReport, final File file) {


      /*  if(CustomProgressDialog.getCustomProgressDialog()!=null)
        {
            CustomProgressDialog.getCustomProgressDialog().dismiss();
        }*/


        // TODO Auto-generated method stub
        AlertDialog mDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("程序出错啦");
        builder.setMessage("请把错误报告以邮件的形式提交给我们，谢谢！");
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 发送异常报告
                        try {

                            int i = EmailSender.send("lvgeservice@126.com", "lvge Exception", crashReport);


                        } catch (Exception e) {
                            Toast.makeText(context,
                                    "There are no email clients installed.",
                                    Toast.LENGTH_SHORT).show();
                        } finally {
                            dialog.dismiss();
                            // 退出

                            restartApp();

                          /*  android.os.Process.killProcess(android.os.Process
                                    .myPid());
                            System.exit(1);*/
                        }
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        restartApp();
                       /* // 退出
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                        System.exit(1);*/
                    }
                });
        mDialog = builder.create();


        if (isMIUIRom()) {

            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);

        } else {
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT); //其他大部分的系统
        }


      /*  mDialog.getWindow().setType(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);*/
        mDialog.show();
    }

    public static boolean isMIUIRom() {

        String property = getSystemProperty("ro.miui.ui.version.name");

        return !TextUtils.isEmpty(property);

    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }


    public void restartApp() {
        Intent intent = new Intent(mContext, WelcomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//结束进程之前可以把你程序的注销或者退出代码放在这段代码之前    
    }

    /**
     * 获取APP崩溃异常报告
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = getPackageInfo(context);
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: " + pinfo.versionName + "("
                + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
                + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    private PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // e.printStackTrace(System.err);
            // L.i("getPackageInfo err = " + e.getMessage());
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 当UncaughtException发生时会转入该重写的方法来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }


}