package lvge.com.myapp;

import android.app.Application;
import android.content.Intent;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.net.CookiePolicy;

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
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(getApplicationContext())) )
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build();
        OkHttpUtils.initClient(okHttpClient);
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);// 程序崩溃时触发线程  以下用来捕获程序崩溃异常      

    }
    // 创建服务用于捕获崩溃异常  
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            restartApp();//发生崩溃异常时,重启应用 
        }
    };

    public void restartApp() {
        Intent intent = new Intent(instance,WelcomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//结束进程之前可以把你程序的注销或者退出代码放在这段代码之前    
    }
}
