package lvge.com.myapp.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {
    public static <T> T getResult(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /*public static <T> ArrayList<T> getResult2(String json, Class<T> classOfT){
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<T>>(){}.getType());

    }*/
    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
    public static  <T> List<T> parseString2List(String json, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(json, type);
        return list;
    }

    private static  class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
