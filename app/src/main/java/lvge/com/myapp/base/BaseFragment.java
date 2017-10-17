package lvge.com.myapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import lvge.com.myapp.MyApplication;
import lvge.com.myapp.event.AppBus;
import lvge.com.myapp.util.LogUtil;
import lvge.com.myapp.view.SweetAlertDialogView;


public abstract class BaseFragment extends com.trello.rxlifecycle2.components.support.RxFragment {

    protected MyApplication app = MyApplication.getInstance();
    protected Activity mActivity;
//    protected OnLoadSuccessCallback callback;
    protected int mId;
    public SweetAlertDialogView sweetAlertDialogView;
//    public LoyoProgressHUD hud;
    public String TAG = getClass().getName();
    protected View parentView;

    protected abstract @LayoutRes int getLayoutResId();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(),container ,false);
        LogUtil.d(TAG,"onCreateView");
        return parentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG,"onViewCreated");
        ButterKnife.bind(this, view);
        attachView();
        initDatas();
        configViews();
    }

    public abstract void attachView();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MyApplication.getInstance();
        AppBus.getInstance().register(this);
        sweetAlertDialogView = new SweetAlertDialogView(getActivity());
        LogUtil.d(TAG,"onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
//        cancelCommitLoading();
        LogUtil.d(TAG,"onDestroy");
        AppBus.getInstance().unregister(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d(TAG,"onAttach");
        mActivity = activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(TAG,"onDetach");
        this.mActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG,"onResume");

    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG,"onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG,"onDestroyView");

    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG,"onPause");

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEvent(Object object) {

    }

    /**
     * 展示带成功失败动画加载框
     */
    /*public void showCommitLoading(boolean outTouch) {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
        hud = LoyoProgressHUD.commitHUD(mActivity)
                .setCancellable(outTouch)
                .show();
    }

    public void showCommitLoading() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
        hud = LoyoProgressHUD.commitHUD(mActivity).show();
    }

    *//**
     * 关闭带成功失败动画加载框
     *//*
    public void cancelCommitLoading() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
    }*/

    /**
     * 关闭软键盘
     */
    public void hideInputKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }


    /**
     * 手动 显示软键盘
     */
    public void showInputKeyboard(final EditText view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        final Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (imm != null) {
                            view.requestFocus();
                            imm.showSoftInput(view, 0);
                        }
                    }
                });
            }
        }, 100);
    }

    public Activity getSupportActivity() {
        return mActivity;
    }
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    /**
     * 关闭SweetAlertDialog
     */
    /*public void cancelDialog() {
        sweetAlertDialogView.sweetAlertDialog.dismiss();
    }

    *//**
     * 加载loading的方法
     *//*
    public void showLoading2(String msg) {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
        hud = LoyoProgressHUD.spinHUD(mActivity)
                .setLabel(msg)
                .show();
    }

    public void showLoading2(String msg, boolean cancelable) {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
        hud = LoyoProgressHUD.spinHUD(mActivity)
                .setLabel(msg)
                .setCancellable(cancelable)
                .show();
    }

    public void cancelLoading2() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
    }*/

}
