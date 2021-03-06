package lvge.com.myapp.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by EthanGong on 2016/12/17.
 */

public class Error {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;
    public final static int AuthFail = 100;
    public final static int verionCode_low = 101;

    @IntDef({Success, Empty, Error, No_Network, Loading, AuthFail,verionCode_low})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}


    /* 原始错误数据 */
    public Throwable originException;
    public @State int loadingState;
    public String message;

    public Error(String message, @State int loadingState, Throwable originException) {
        this.message = message;
        this.loadingState = loadingState;
        this.originException = originException;
    }
}
