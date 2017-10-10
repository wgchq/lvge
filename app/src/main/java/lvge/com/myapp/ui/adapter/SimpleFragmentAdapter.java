package lvge.com.myapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;
import lvge.com.myapp.model.OrderTabItem;
import lvge.com.myapp.ui.fragment.OrderItemFragment;
import lvge.com.myapp.util.AppUtil;


public class SimpleFragmentAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;

    private Context mContext;
    private List<OrderTabItem> mItemList;
    private List<OrderItemFragment> mFragmentList = new ArrayList<>(10);
    public SimpleFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }
    public SimpleFragmentAdapter(Context context, List<OrderTabItem> orderTabItemList, FragmentManager fm) {
        this(context,fm);
        this.mItemList = orderTabItemList;
        for (OrderTabItem item: mItemList){
            mFragmentList.add(OrderItemFragment.newInstance());
        }
    }

    @Override
    public Fragment getItem(int position) {
        int type;
        /*switch (position) {
            case 0:
                type = Constants.TYPE_TIMELINE_PUBLIC;
                break;
            case 1:
                type = Constants.TYPE_TIMELINE_FRIEND;
                break;
            case 2:
                type = Constants.TYPE_TIMELINE_MINE;
                break;
            default:
                type = Constants.TYPE_TIMELINE_PUBLIC;
                break;
        }*/
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        OrderTabItem item = mItemList.get(position);
        String name = item.name+"\n"+item.count;
        SpannableString sps = new SpannableString(name);
        int i = name.indexOf("\n");
        sps.setSpan(new AbsoluteSizeSpan((int) AppUtil.getResource().getDimension(R.dimen.x24),true),i+2,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sps;
    }
}