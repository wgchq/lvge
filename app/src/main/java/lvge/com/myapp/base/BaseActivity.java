package lvge.com.myapp.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lvge.com.myapp.MyApplication;
import lvge.com.myapp.R;
import lvge.com.myapp.event.AppBus;
import lvge.com.myapp.util.AppUtil;
import lvge.com.myapp.util.LogUtil;
import lvge.com.myapp.util.SystemBarTintManager;
import lvge.com.myapp.util.ViewUtil;

/**
 * activity 基类
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected MyApplication app;
    public Context mContext;
    /**
     * 四种Activity跳转动画
     */
    public static final int ENTER_TYPE_NO = 0;
    public static final int ENTER_TYPE_TOP = 1;
    public static final int ENTER_TYPE_BUTTOM = 2;
    public static final int ENTER_TYPE_LEFT = 3;
    public static final int ENTER_TYPE_RIGHT = 4;

    public String TAG = getClass().getName();

    //使用这个框架，主要是为了兼容4.4系统沉浸状态栏，当不需要兼容4.4的时候，可以直接删除，把内部逻辑拿出来
    public SystemBarTintManager tintManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplicationContext();
        mContext = this;
//        sweetAlertDialogView = new SweetAlertDialogView(this);

        //注册eventBus
        AppBus.getInstance().register(this);
        //注册监听网络变化
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registerReceiver(baseReceiver, filter);
        registerBaseReceiver();

        initStatusBar();
        LogUtil.i(TAG, "onCreate");
    }

    //处理沉浸式的状态栏和导航栏
    private void initStatusBar() {
        // 创建状态栏的管理实例
        tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个颜色给系统栏
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 设置状态栏灰色透明，主要是针对部分顶部是白色的布局，如果全透明，会导致状态栏显示不友好。
     *
     * @param translucent 如果为ture是半透明，为false是全透明，不设置是默认的蓝色
     */
    public void setStatusBarColor(boolean translucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (translucent) {
                getWindow().setStatusBarColor(Color.parseColor("#55000000"));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4的系统，直接使用半透明效果
            tintManager.setTintColor(Color.parseColor("#55000000"));

        }
    }

    /**
     * 设置全屏，包括状态栏和导航栏，主要用于引导页面
     */
    public void setFullScreen() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager.setStatusBarTintEnabled(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
//        cancelCommitLoading();
        AppBus.getInstance().unregister(this);
        unregisterReceiver(baseReceiver);
        unRegisterBaseReceiver();
        //关闭键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG, "onStart");

    }

    @Subscribe
    public void onEvent(Object object) {

    }

    protected BroadcastReceiver baseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == intent) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (null != info && info.isAvailable() && info.isConnected()) {
                    onNetworkChanged(true);
                } else {
                    onNetworkChanged(false);
                }
            }
        }
    };

    /**
     * 网络状态变化回调方法
     *
     * @param available true 表示网络可用
     */
    protected void onNetworkChanged(boolean available) {

    }

    /**
     * 注册基类广播
     */
    protected void registerBaseReceiver() {

    }

    /**
     * 解除注册基类广播
     */
    protected void unRegisterBaseReceiver() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
//        outState.putString("token", MainApp.getToken());
//        outState.putSerializable("user", MainApp.user);
    }

    /**
     * bugfix: FragmentActivity.onBackPressed Can not perform this action after onSaveInstanceState
     * <p>
     * http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
     */

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void invokeFragmentManagerNoteStateNotSaved() {
        /**
         * For post-Honeycomb devices
         */
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        try {
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!"Activity".equals(cls.getSimpleName()));
            Field fragmentMgrField = cls.getDeclaredField("mFragments");
            fragmentMgrField.setAccessible(true);

            Object fragmentMgr = fragmentMgrField.get(this);
            cls = fragmentMgr.getClass();

            Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved", new Class[]{});
            noteStateNotSavedMethod.invoke(fragmentMgr, new Object[]{});
            Log.d("DLOutState", "Successful call for noteStateNotSaved!!!");
        } catch (Exception ex) {
            Log.e("DLOutState", "Exception on worka FM.noteStateNotSaved", ex);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        //老代码，作用大概是点击屏幕隐藏输入软键盘
        getWindow().getDecorView().setOnTouchListener(ViewUtil.OnTouchListener_softInput_hide.Instance());
        LogUtil.i(TAG, "onResume");
        super.onResume();
    }

    protected void setTitle(String title) {
//        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }

    protected void setTitle(int id, String title) {
        ((TextView) findViewById(id)).setText(title);
    }


    private Toast mCurrentToast;

    protected void Toast(String msg) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(app.getBaseContext(), msg, Toast.LENGTH_SHORT);
        mCurrentToast.setGravity(Gravity.CENTER, 0, 0);
        mCurrentToast.show();
    }

    protected void Toast(int resId) {
        Toast(app.getString(resId));
    }


    /*************软键盘显示隐藏操作*****************/
    /**
     * 关闭软键盘
     */
    public void hideInputKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * 手动 显示软键盘
     */
    public void showInputKeyboard(final EditText view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 关闭软键盘
     */
    public void hideInputKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 手动 显示软键盘
     */
    public void showInputKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /*************等待对话框的相关操作*****************/
    /**
     * 展示带成功失败动画加载框
     */
    /*public void showCommitLoading() {
        if (hud != null) {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            hud = null;
        }
        hud = LoyoProgressHUD.commitHUD(this).show();
    }

    *//**
     * 关闭带成功失败动画加载框
     *//*
    public void cancelCommitLoading() {
        if (hud != null) {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            hud = null;
        }
    }

    *//**
     * 加载loading的方法
     *//*
    public void showLoading2(String msg) {
        if (hud != null) {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            hud = null;
        }
        hud = LoyoProgressHUD.spinHUD(this).setLabel(msg).show();
    }

    public void showLoading2(String msg, boolean cancelable) {
        if (hud != null) {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            hud = null;
        }
        hud = LoyoProgressHUD.spinHUD(this).setLabel(msg).setCancellable(cancelable).show();
    }

    public void cancelLoading2() {
        if (hud != null) {
            if (hud.isShowing()) {
                hud.dismiss();
            }
            hud = null;
        }
    }

    *//**
     * SweetAlertDialog关闭
     *//*
    public void dismissSweetAlert() {
        sweetAlertDialogView.sweetAlertDialog.dismiss();
    }*/

    /**
     * 重启当前Activity
     */
    public void restartActivity() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    /*******页面启动方式相关********/
    public void startActivityWithoutAnimate(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
    }

    public void startActivity(Intent intent, int animType) {
        super.startActivity(intent);
        overridePendingTransitionByType(animType);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
    }

    public void startActivityForResult(Intent intent, int requestCode, int animType) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransitionByType(animType);
    }

    /*启动新页面，关闭老页面的时候，请使用本方法，不然会造成动画混乱*/
    public void finishWithoutAnimate() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_lefttoright, R.anim.exit_lefttoright);
    }

    /**
     * 提交数据以后，展示提交后的信息，延迟关闭
     */
    public void finishWithDelay() {
        AppUtil.runOnUIDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    public void finish(int animType) {
        super.finish();
        overridePendingTransitionByType(animType);
    }

    //这里基类名字，避免和子类方法冲突，循环调用
    public void onBaseBack() {
        super.finish();
        overridePendingTransition(R.anim.enter_lefttoright, R.anim.exit_lefttoright);
    }

    public void onBaseBack(int animType) {
        finish(animType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_lefttoright, R.anim.exit_lefttoright);

    }

    private void overridePendingTransitionByType(int animType) {
        switch (animType) {
            case ENTER_TYPE_NO:
                break;
            case ENTER_TYPE_TOP:
                overridePendingTransition(R.anim.enter_toptobuttom, R.anim.exit_toptobuttom);
                break;
            case ENTER_TYPE_BUTTOM:
                overridePendingTransition(R.anim.enter_buttomtotop, R.anim.exit_buttomtotop);
                break;
            case ENTER_TYPE_LEFT:
                overridePendingTransition(R.anim.enter_lefttoright, R.anim.exit_lefttoright);
                break;
            case ENTER_TYPE_RIGHT:
                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                break;
        }
    }
}
