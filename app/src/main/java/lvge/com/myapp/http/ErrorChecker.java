package lvge.com.myapp.http;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

import static lvge.com.myapp.http.Error.AuthFail;
import static lvge.com.myapp.http.Error.No_Network;

/**
 * 网络回调 的检查
 * Created xnq 15/12/18.
 */

public class ErrorChecker {


    public final static int SILENCE = 1 << 0;
    public final static int TOAST = 1 << 1;
    public final static int LOADING_LAYOUT = 1 << 2;
    public final static int PROGRESS_HUD = 1 << 3;

    @IntDef({SILENCE, TOAST, LOADING_LAYOUT, PROGRESS_HUD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CheckType {
    }

    private ErrorChecker() {
    }

    public static Error error(Throwable e) {
        String message = "";
        @Error.State int state = Error.Success;
        if (e instanceof HttpException) {
            if (e instanceof HttpException && (666 == ((HttpException) e).code())) {
                message = "授权失败，请重新登录";
                state = AuthFail;
            }
            else {
                try {
                    String msg = convertStreamToString(((HttpException) e).response().errorBody().byteStream());
                    JSONObject jsonObject = new JSONObject(msg);
                    String errorMessage = jsonObject.getString("error");
                    if (TextUtils.isEmpty(errorMessage)) {
                        errorMessage = e.getMessage();
                    }
                    state = No_Network;
                    message = errorMessage;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    state = No_Network;
                    message = "服务器状态异常";
                }
            }

        } else if(e instanceof SocketTimeoutException) {
            //网络超时
            state = No_Network;
            message = "网络超时";
        }else if (e instanceof APIException && ("60009".equals(((APIException) e).code))){
            message = "版本过低，请更新应用";
            state = Error.verionCode_low;
        } else if (e instanceof APIException) {
            message = e.getMessage();
            state = No_Network;
        } else if (e instanceof JsonSyntaxException || e instanceof MalformedJsonException) {
            state = No_Network;
            message = "数据解析异常！";
        }else {
            state = No_Network;
            message = "网络不给力！";
        }
        return new Error(message, state, e);
    }

    public static Error checkError(Throwable e) {
        return checkError(e, TOAST, null);
    }

    /*public static Error checkError(Throwable e, LoadingLayout layout) {
        return checkError(e, LOADING_LAYOUT, layout);
    }

    public static Error checkError(Throwable e, @CheckType int type) {
        return checkError(e, type, null);
    }

    */
    public static Error checkError(Throwable e, @CheckType int type, Object layout) {
        Error error = ErrorChecker.error(e);
        if (type == LOADING_LAYOUT && layout == null) {
            type = TOAST;
        }

        if (error.loadingState == AuthFail) {
            return null;
        }
        if (error.loadingState == Error.verionCode_low) {

            return null;
        }
        switch (type) {
            case SILENCE: {
                // 静默处理，不提示
            }
            break;
            case LOADING_LAYOUT: {

//                layout.setNoNetworkText(error.message);
            }
            break;
            case PROGRESS_HUD: {
                // onCompleted 处理提示
            }
            break;
            default: {

                if (layout != null) {
//                    layout.setStatus(LoadingLayout.Success);
                }
            }
            break;
        }
        return error;

    }

    public static String convertStreamToString(InputStream is) {

        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
