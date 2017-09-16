package lvge.com.myapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.OnTextChanged;
import lvge.com.myapp.R;

/**
 * Created by JGG on 2017-09-15.
 */

public class CustomKeyboard {

    private Context mContext;

    private Dialog dialog;

    private EditText mEditText;
    private View view;
    private View.OnTouchListener onTouchListener;
    private OnValidationLisnter onValidationLisnter;

    public CustomKeyboard(Context context) {
        mContext = context;

    }

    public void SetEditText(final EditText mEditText) {
        this.mEditText = mEditText;
        if (this.mEditText != null) {
            this.mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (!s.equals("") && mEditText.getCompoundDrawables().length != 0) {
                        Drawable mIconDelete = mContext.getResources().getDrawable(R.mipmap.validation_code_delete);
                        mIconDelete.setBounds(5, 5, 60, 50);
                        mEditText.setCompoundDrawables(null, null, mIconDelete, null);
                    } else {
                        mEditText.setCompoundDrawables(null, null, null, null);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = mEditText.getInputType();
                    mEditText.setInputType(InputType.TYPE_NULL);
                    show(v);
                    mEditText.setInputType(type);
                }
            });

            mEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Drawable drawable = mEditText.getCompoundDrawables()[2];
                    //如果右边没有图片，不再处理
                    if (drawable == null)
                        return false;
                    //如果不是按下事件，不再处理
                    if (event.getAction() != MotionEvent.ACTION_UP)
                        return false;
                    if (event.getX() > mEditText.getWidth()
                            - mEditText.getPaddingRight()
                            - drawable.getIntrinsicWidth()) {

                        int start = mEditText.getSelectionStart();
                        int end = mEditText.getSelectionEnd();


                        String textcontent = mEditText.getText().toString();
                        textcontent = textcontent.substring(0,start-1)+textcontent.substring(end+1,textcontent.length());
                        mEditText.setText(textcontent);
                        mEditText.setSelection(start-1);
                    }

                    return false;
                }
            });
        }
    }

    public void Init(View view) {

        this.onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String mEditTextContent = mEditText.getText().toString();
                int start = mEditText.getSelectionStart();
                int key = v.getId();
                switch (key) {

                    case R.id.key_0:
                        mEditTextContent = mEditTextContent.substring(0, start) + "0" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_1:
                        mEditTextContent = mEditTextContent.substring(0, start) + "1" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_2:
                        mEditTextContent = mEditTextContent.substring(0, start) + "2" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_3:
                        mEditTextContent = mEditTextContent.substring(0, start) + "3" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_4:
                        mEditTextContent = mEditTextContent.substring(0, start) + "4" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_5:
                        mEditTextContent = mEditTextContent.substring(0, start) + "5" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_6:
                        mEditTextContent = mEditTextContent.substring(0, start) + "6" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_7:
                        mEditTextContent = mEditTextContent.substring(0, start) + "7" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_8:
                        mEditTextContent = mEditTextContent.substring(0, start) + "8" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_9:
                        mEditTextContent = mEditTextContent.substring(0, start) + "9" + mEditTextContent.substring(start, mEditTextContent.length());
                        break;
                    case R.id.key_board_down:
                        dialog.dismiss();
                        break;
                    case R.id.key_board_validation:
                        onValidationLisnter.OnValidation();
                        break;
                }


               // mEditText.setText(mEditTextContent);
               // mEditText.setSelection(start + 1);
                return false;
            }
        };

        view.findViewById(R.id.key_0).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_1).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_2).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_3).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_4).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_5).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_6).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_7).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_8).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_9).setOnTouchListener(this.onTouchListener);

        view.findViewById(R.id.key_board_down).setOnTouchListener(this.onTouchListener);
        view.findViewById(R.id.key_board_validation).setOnTouchListener(this.onTouchListener);
    }

    public void show(View view) {
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        view = LayoutInflater.from(mContext).inflate(R.layout.custom_key_board_view, null);
        //初始化控件
        Init(view);
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setDimAmount(0f);

        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
     /*   lp.y = 20;//设置Dialog距离底部的距离*/
//       将属性设置给窗体
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    public void setValidationListner(OnValidationLisnter listner) {
        this.onValidationLisnter = listner;
    }


    public void deleteContent() {

    }

    public interface OnValidationLisnter {
        public void OnValidation();
    }
}

