package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by mac on 2017/7/17.
 */

public class CustomerSearchListview extends ListView{
    public CustomerSearchListview(Context context) {
        super(context);
    }

    public CustomerSearchListview(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public CustomerSearchListview(Context context,AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }
}
