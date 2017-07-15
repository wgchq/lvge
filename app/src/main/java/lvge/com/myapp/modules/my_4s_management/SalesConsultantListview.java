package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import lvge.com.myapp.R;
import lvge.com.myapp.model.PageResult;

/**
 * Created by mac on 2017/7/4.
 */

public class SalesConsultantListview extends ListView{

    private int touchSlop ;    //用户滑动的最小距离
    private boolean isSliding;   //是都响应滑动
    private int xDown;          //X坐标
    private int yDown;          //Y坐标
    private int xMove;          //移动X坐标
    private  int yMove;         //移动Y坐标

    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private int mPopupWindowHeight;
    private int mPopupWindowWidth;

    private Button mDeleBtn;

    private  DelButtonClickListener mListener;   //为删除按钮提供一个回调接口

    private View mCurrentView;    //当前手指触摸的View;
    private int mCurrentViewPos;   //手指触摸的位置


    public SalesConsultantListview(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);

        mLayoutInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View  view = mLayoutInflater.inflate(R.layout.delete_btn,null);
        mDeleBtn = (Button)findViewById(R.id.sales_consultant_listview_deletebutton);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        mPopupWindow.getContentView().measure(0,0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();

    }

    public boolean dispatchTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        int x = (int)ev.getX();
        int y = (int)ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;

                if (mPopupWindow.isShowing()) {
                    dismissPopWindow();
                    return false;
                }
                //获得当前手指按下时的item位置
                mCurrentViewPos = pointToPosition(xDown, yDown);
                //获得当前手指按下的item
                View view = getChildAt(mCurrentViewPos = getFirstVisiblePosition());
                mCurrentView = view;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                //判断是不是从右向左滑动
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev){
        int action = ev.getAction();

        if(isSliding){
            switch (action){
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    //获得当前item位置X,Y
                    mCurrentView.getLocationOnScreen(location);
                    mPopupWindow.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
                    mPopupWindow.update();
                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT|Gravity.TOP,
                            location[0]+mCurrentView.getWidth(),location[1] + mCurrentView.getHeight()/2 - mPopupWindowHeight/2);

                    mDeleBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mListener != null){
                                mListener.clickHappend(mCurrentViewPos);
                                mPopupWindow.dismiss();
                            }
                        }
                    });

                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopWindow(){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    public void setDelButtonClickListener(DelButtonClickListener listener){
        mListener = listener;
    }

    interface DelButtonClickListener{
        public void clickHappend(int position);
    }

}
