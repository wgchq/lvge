package lvge.com.myapp.util;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class ViewUtil {
    protected ViewUtil() {
        throw new UnsupportedOperationException(); // 防止子类调用
    }

    private static String TAG = "ViewUtil";
    public static final int DATE_NULL = 0;
    public static class OnTouchListener_view_transparency implements View.OnTouchListener {

        static OnTouchListener_view_transparency _Instance;

        public static OnTouchListener_view_transparency Instance() {
            if (_Instance == null) {
                _Instance = new OnTouchListener_view_transparency();
            }

            return _Instance;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouch 触摸监听 OnTouchListener_view_transparency event.getAction():" + event.getAction());
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.5f);
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.setAlpha(1);
            }
            return false;
        }

    }

    public static class OnTouchListener_softInput_hide implements View.OnTouchListener {

        static OnTouchListener_softInput_hide _Instance;

        public static OnTouchListener_softInput_hide Instance() {
            if (_Instance == null) {
                _Instance = new OnTouchListener_softInput_hide();
            }

            return _Instance;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    break;
                case MotionEvent.ACTION_UP:

                    break;

            }

            return false;
        }
    }


    public static void setViewHigh(View view, float scale) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new SetViewHigh(view, scale));
        }
    }

    public static class SetViewHigh implements ViewTreeObserver.OnGlobalLayoutListener {
        private View view;
        private float scale;
//        private Boolean isSetMinHeight;

        public SetViewHigh(View view, float scale) {
            this.view = view;
            this.scale = scale;
//            this.isSetMinHeight=false;
        }

        public SetViewHigh(View view, float scale, Boolean isSetMinHeight) {
            this.view = view;
            this.scale = scale;
//            this.isSetMinHeight=isSetMinHeight;
        }

        @Override
        public void onGlobalLayout() {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            Log.d(TAG, "SetViewHigh.onGlobalLayout. w:"
                    + view.getMeasuredWidth() + ";h:"
                    + view.getMeasuredHeight());
            ViewGroup.LayoutParams lp_view = view.getLayoutParams();
            Log.d(TAG, "lp_view.height:" + lp_view.height);
            lp_view.height = (int) ((float) (view.getMeasuredWidth()) * scale);
            Log.d(TAG, "lp_view.height:" + lp_view.height);
            view.setLayoutParams(lp_view);

        }
    }
}
