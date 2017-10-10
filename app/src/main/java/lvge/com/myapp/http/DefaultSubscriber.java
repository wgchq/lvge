package lvge.com.myapp.http;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lvge.com.myapp.util.AppUtil;

/**
 * Created by android on 17/8/16.
 */

public abstract class DefaultSubscriber<T> implements Subscriber<T> {

    private static final String TAG = "DefaultSubscriber";
    @ErrorChecker.CheckType
    int type;
    private Dialog mDialog;
    @Override
    public void onSubscribe(Subscription s) {
        Log.i(TAG, "onSubscribe: ");
        s.request(Long.MAX_VALUE);
        if (mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }
    public DefaultSubscriber(){
        type = ErrorChecker.TOAST;
    }
    public DefaultSubscriber(Dialog dialog){
        this.mDialog = dialog;
        type = ErrorChecker.TOAST;
    }
    @Override
    public void onNext(T response) {
            /*String code = response.code;
            isLoading = false;
//            view.hideLoading();*/
        onSucced(response);
            /*try {
                *//*JSONObject jsonObject = new JSONObject(json.toString());
                String code = jsonObject.getString("code");*//*

                //登陆错误
//                if (code.equals("5002")) {
//                    String msg = jsonObject.getString("msg");
//                    showError(msg);
//                    return;
//                }
                if (code.equals("0")) {
                    Log.i(TAG, "onNext: success = "+code);

                } else {
                    String msg = jsonObject.getString("msg");
                    Log.i(TAG, "showError:大白兔" + msg);
                    showError(msg);
                }
            } catch (JSONException e) {
                Log.i(TAG, "JSONException");
                showError(e.getMessage());
            }*/
    }

    public abstract void onSucced(T result);

    @Override
    public void onError(Throwable t) {
        Log.i(TAG, "onError");
        Error error = ErrorChecker.checkError(t);
        switch (type){
            case ErrorChecker.TOAST:
                showError(error.message);
                break;
        }

//            view.hideLoading();
        if (t.getMessage() != null) showError(t.getMessage());
        else showError("服务器连接出错");
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete");
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    protected void showError(String msg) {
        Log.i(TAG, "showError: msg  " + msg.toString());
        Toast.makeText(AppUtil.getAppContext(),msg.toString(),Toast.LENGTH_LONG).show();
    }
}


