package lvge.com.myapp.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by JGG on 2017/4/23.
 */

public class JsonUtil {
    private static Gson gson = new Gson();

    public static  String   ObjectToJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static Object jsonStrToObject(String str, Type typeofT) {
        return gson.fromJson(str,typeofT);
    }

}

