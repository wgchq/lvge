package lvge.com.myapp.modules.my_4s_management;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import lvge.com.myapp.R;

/**
 * Created by mac on 2017/7/4.
 */

public class SalesConsultantListview extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener {

    //手势动作探测器
    private GestureDetector mGestureDetector;

    //删除时间监听器
    public  interface OnDeleteListener{
        void onDelete(int index);
    }

    private OnDeleteListener mOnDeleteListener;

    //删除按钮
    private View mDeleteBtn;

    //列表项布局
    private ViewGroup mItemLayout;

    //选择的列表项
    private int mSeletedItem;

    //当前按钮是否显示出来
    private  boolean isDeleteShown;

    public SalesConsultantListview(Context context, AttributeSet attrs){
        super(context,attrs);

        //创建手势监听器对象
        mGestureDetector = new GestureDetector(getContext(),this);

        //监听onTouch事件
        setOnTouchListener(this);
    }

    //设置删除监听事件
    public void  setOnDeleteListener(OnDeleteListener  listener){
        mOnDeleteListener = listener;
    }

    //触摸监听事件
    public boolean onTouch(View v, MotionEvent event){
        if(isDeleteShown){
            hideDelete();
            return false;
        }else {
            return mGestureDetector.onTouchEvent(event);
        }
    }

    public boolean onDown(MotionEvent e){
        if(!isDeleteShown){
            mSeletedItem = pointToPosition((int)e.getX(),(int)e.getY());
        }
        return  false;
    }

    public boolean onFling(MotionEvent e1,MotionEvent e2, float velocityX,float velocityY){
        //如果当前删除按钮没有显示出来，并且X方向滑动的速度大于Y方向的滑动速度
        if(!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)){
            mDeleteBtn = LayoutInflater.from(getContext()).inflate(R.layout.delete_btn,null);

            mDeleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemLayout.removeView(mDeleteBtn);
                    mDeleteBtn = null;
                    isDeleteShown = false;
                    mOnDeleteListener.onDelete(mSeletedItem);
                }
            });

            mItemLayout = (ViewGroup)getChildAt(mSeletedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            mItemLayout.addView(mDeleteBtn,params);
            isDeleteShown = true;
        }

        return  false;
    }

    //隐藏删除按钮
    public void hideDelete(){
        mItemLayout.removeView(mDeleteBtn);
        mDeleteBtn = null;
        isDeleteShown = false;
    }

    public boolean isDeleteShown(){
        return  isDeleteShown;
    }

    public void onShowPress(MotionEvent e){

    }

    public boolean onSingleTapUp(MotionEvent e){
        return  false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY ){
        return  false;
    }

    public void onLongPress(MotionEvent e){

    }
}
