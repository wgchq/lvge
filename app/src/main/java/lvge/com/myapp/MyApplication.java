package lvge.com.myapp;

import android.app.Application;

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
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(getApplicationContext())) )
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
