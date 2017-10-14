package lvge.com.myapp.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by EthanGong on 2016/11/17.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dp2px(float dpValue) {
        final float scale = AppUtil.getResource().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }
    public static int sp2px(float spValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,  AppUtil.getResource().getDisplayMetrics());
    }

}
