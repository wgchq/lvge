package lvge.com.myapp.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by adnroid on 17/5/24.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();

        /*builder.header("Authorization", String.format("Bearer %s", MainApp.getToken()))
                .header("LoyoPlatform", cellInfo.getLoyoPlatform())
                .header("LoyoAgent", cellInfo.getLoyoAgent())
                .header("LoyoOSVersion", cellInfo.getLoyoOSVersion())
                .header("LoyoVersionName", Global.getVersionName())
                .header("LoyoVersionCode", String.valueOf(Global.getVersion()))
                .header("uuid", StringUtil.getUUID())
                .build();*/
        Request request = builder.build();
        return chain.proceed(request);
    }
}
