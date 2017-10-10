package lvge.com.myapp.view.qrcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lvge.com.myapp.R;
import lvge.com.myapp.util.DensityUtil;


public class BarCodeView extends LinearLayout {
    private final float textSize;
    private final float imgHeight;
    private final String text;

    public BarCodeView(Context context) {
        this(context, null, 0);
    }

    public BarCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BarCodeView);
        textSize = a.getFloat(R.styleable.BarCodeView_textSize, 12);
        text = a.getString(R.styleable.BarCodeView_textSize);
        imgHeight = a.getFloat(R.styleable.BarCodeView_imgHeight, DensityUtil.dp2px(150));
        a.recycle();
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        ImageView imageView = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) imgHeight);
        imageView.setLayoutParams(layoutParams);
        Ecoad ecoad = new Ecoad(imageView.getWidth(), imageView.getHeight());
        try {
            Bitmap bitmap = ecoad.createBar(text);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addView(imageView);
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setText(text);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(textSize);
        addView(textView);
    }

}
