package lvge.com.myapp.ProgressDialog;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import lvge.com.myapp.R;

public class CustomProgressDialog extends Dialog {

    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme){
        super(context,theme);
    }

    public static CustomProgressDialog createDialog(Context context){
        customProgressDialog = new CustomProgressDialog(context,R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.activity_custom_progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){
        if(customProgressDialog == null){
            return;
        }
        ImageView imageView = (ImageView)customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getBackground();
        animationDrawable.start();
    }

    public CustomProgressDialog setTitle(String strTitle){
        return customProgressDialog;
    }

    public CustomProgressDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loading);
        if(tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }
}
