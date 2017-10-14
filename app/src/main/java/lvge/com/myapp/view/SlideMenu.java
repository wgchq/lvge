package lvge.com.myapp.view;

/**
 * Created by JGG on 2017/5/26.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

import lvge.com.myapp.R;
import lvge.com.myapp.util.ScreenUtils;


public class SlideMenu extends HorizontalScrollView {
    private  final String TAG = getClass().getName();
    /**
     * 灞忓箷瀹藉害
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int mMenuRightPadding;
    /**
     * 鑿滃崟鐨勫搴�
     */
    private int mMenuWidth;
    private int mHalfMenuWidth;

    private boolean isOpen;

    private boolean once;

    private ViewGroup mMenu;
    private ViewGroup mContent;
    private  View mengBanContainer;

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScreenWidth = ScreenUtils.getScreenWidth(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50f,
                                    getResources().getDisplayMetrics()));// 榛樿涓�10DP
                    break;
            }
        }
        a.recycle();

    }

    public SlideMenu(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         */
        if (!once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mContent = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mScreenWidth;


        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void iniView()
    {
        if(null==mengBanContainer)
        {
            mengBanContainer = LayoutInflater.from(mContent.getContext()).inflate(R.layout.layout_mengban, null);
            mContent.addView(mengBanContainer);
            mengBanContainer.setVisibility(GONE);
            FrameLayout.LayoutParams params =
                    new android.widget.FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.width = mContent.getWidth();
            params.height = mContent.getHeight();
            mengBanContainer.setLayoutParams(params);

            mengBanContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeMenu();
                }
            });
        }


    }
    private void mengBanShow()
    {
        iniView();
        mengBanContainer.setVisibility(VISIBLE);
    }
    private void mengBanHide()
    {
        iniView();
        mengBanContainer.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
//        LogUtil.i(TAG,"ev.getX(): "+ev.getX());
        switch (action) {

            case MotionEvent.ACTION_DOWN:

                if (ev.getX()>100)
                    return false;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                    mengBanHide();

                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                    mengBanShow();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 鎵撳紑鑿滃崟
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
        mengBanShow();
    }

    /**
     * 鍏抽棴鑿滃崟
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
        mengBanHide();
    }

    /**
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

    }

}

