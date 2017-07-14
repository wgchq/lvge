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

public class SalesConsultantListview extends SwipeMenuListView implements View.OnTouchListener,GestureDetector.OnGestureListener{

    private GestureDetector gestureDetector;  //手势识别
    private View  btnDelete;   //滑动时出现的按钮
    private ViewGroup viewGroup;   //ListView的每一个item布局
    private int selectedItem;     //选中的item
    private boolean isDeleteShow;    //是否显示删除按钮
    private OnItemDeleteListener onItemDeleteListener;

    public SalesConsultantListview(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
        gestureDetector = new GestureDetector(getContext(),this);
        setOnTouchListener(this);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener){
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //得到当前触摸的条目
        if(!isDeleteShow){
            selectedItem = pointToPosition((int)e.getX(),(int)e.getY());
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(!isDeleteShow && Math.abs(velocityX) > Math.abs(velocityY)){
            btnDelete = LayoutInflater.from(getContext()).inflate(R.layout.delete_btn,null);
            btnDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewGroup.removeView(btnDelete);
                    btnDelete = null;
                    isDeleteShow = false;
                    onItemDeleteListener.onItemDelete(selectedItem);
                }
            });

            viewGroup = (ViewGroup)getChildAt(selectedItem - getFirstVisiblePosition());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           // layoutParams.(LinearLayout.ALIGN_PARENT_RIGHT);
           // layoutParams.addRule(LinearLayout.CENTER_VERTICAL);
            btnDelete.setLayoutParams(layoutParams);
            viewGroup.addView(btnDelete);
            btnShow(btnDelete);
            isDeleteShow = true;
        }else {
            setOnTouchListener(this);
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //得到当前触摸的条目
        selectedItem = pointToPosition((int)event.getX(),(int)event.getY());
        //如果删除按钮已经显示，那么隐藏
        if(isDeleteShow){
            btnHide(btnDelete);
            viewGroup.removeView(btnDelete);
            btnDelete = null;
            isDeleteShow = false;
            return false;
        }else {
            //手势判断，调用onFling
            return gestureDetector.onTouchEvent(event);
        }
    }



    public interface OnItemDeleteListener{
        public void onItemDelete(int selectedItem);
    }

    private void btnShow(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.delete_btn_show));
    }

    private void btnHide(View v){
        v.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.delete_btn_hide));
    }

}
