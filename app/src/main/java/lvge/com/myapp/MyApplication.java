package lvge.com.myapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.net.CookiePolicy;

import lvge.com.myapp.util.CrashHandler;
import lvge.com.myapp.util.EmailSender;
import okhttp3.OkHttpClient;

/**
 * Created by JGG on 2017/7/11.
 */

public class MyApplication extends Application {

    protected static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(getApplicationContext())))
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build();
        OkHttpUtils.initClient(okHttpClient);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
       // Thread.setDefaultUncaughtExceptionHandler(restartHandler);// 程序崩溃时触发线程  以下用来捕获程序崩溃异常      
    }

    // 创建服务用于捕获崩溃异常  
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            final String ex = e.getMessage();
            new Thread(new Runnable() {
                @Override
                public void run() {

                /*    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("程序出错啦");
                    builder.setMessage("是否发送邮件给系统管理员，寻求帮助！");
                    builder.create().show();*/

             /*         // TODO Auto-generated method stub
                    AlertDialog mDialog = null;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("程序出错啦");
                    builder.setMessage("是否发送邮件给系统管理员，寻求帮助！");
                    builder.setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // 发送异常报告
                                    try {
                                        int i = EmailSender.send("lvgeservice@126.com", "lvge Exception", "sfjsdjfsdf");

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(),
                                                "There are no email clients installed.",
                                                Toast.LENGTH_SHORT).show();
                                    } finally {
                                        dialog.dismiss();
                                        // 退出
                                        android.os.Process.killProcess(android.os.Process
                                                .myPid());
                                        System.exit(1);
                                    }
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // 退出
                                    android.os.Process.killProcess(android.os.Process
                                            .myPid());
                                    System.exit(1);
                                }
                            });
                    mDialog = builder.create();
                    mDialog.getWindow().setType(
                            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    mDialog.show();

*/

                    int i = EmailSender.send("lvgeservice@126.com", "lvge Exception", ex);
                    Process.killProcess(Process.myPid());
                    System.exit(1);
                }
            }).start();


        }
    };

    public void restartApp() {
        Intent intent = new Intent(instance, WelcomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//结束进程之前可以把你程序的注销或者退出代码放在这段代码之前    
    }
}
