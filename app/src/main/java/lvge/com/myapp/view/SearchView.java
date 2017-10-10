package lvge.com.myapp.view;

/**
 * Created by JGG on 2017-09-09.
 */

import android.support.v7.widget.AppCompatEditText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import lvge.com.myapp.R;


public class SearchView extends AppCompatEditText {

    float searchSize = 0;
    float textSize = 0;
    String hintText ="搜索";
    int textColor = 0xFF000000;
    Drawable mDrawable;
    Paint paint;

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitResource(context, attrs);
        InitPaint();
    }

    private void InitResource(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        float density = context.getResources().getDisplayMetrics().density;
        searchSize = mTypedArray.getDimension(R.styleable.SearchView_imageWidth, 18 * density + 0.5F);
        textColor = mTypedArray.getColor(R.styleable.SearchView_textColor, 0xFF848484);
        textSize = mTypedArray.getDimension(R.styleable.SearchView_textSize, 14 * density + 0.5F);
        hintText = mTypedArray.getString(R.styleable.SearchView_hintText);
        mTypedArray.recycle();
    }

    private void InitPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawSearchIcon(canvas);
    }

    private void DrawSearchIcon(Canvas canvas) {
        if (this.getText().toString().length() == 0) {
            float textWidth = paint.measureText(hintText);
            float textHeight = getFontLeading(paint);

            float dx = (getWidth() - searchSize - textWidth - 8) / 2;
            float dy = (getHeight() - searchSize) / 2;

            canvas.save();
         //   canvas.translate(getScrollX() + dx, getScrollY() + dy);
            canvas.translate(getScrollX()+20+5, getScrollY()+dy);
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
            canvas.drawText(hintText, getScrollX() + searchSize + 8, getScrollY() + (getHeight() - (getHeight() - textHeight) / 2) - paint.getFontMetrics().bottom - dy, paint);
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mDrawable == null) {
            try {
                mDrawable = getContext().getResources().getDrawable(R.mipmap.ic_common_search);
                mDrawable.setBounds(0, 0, (int) searchSize, (int) searchSize);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            mDrawable = null;
        }
        super.onDetachedFromWindow();
    }

    public float getFontLeading(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }

}
