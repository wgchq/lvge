package lvge.com.myapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import lvge.com.myapp.R;
import lvge.com.myapp.util.AppUtil;
import lvge.com.myapp.util.NumberUtil;

/**
 * Created by android on 2017/10/11.
 */

public class GoodsDesView extends LinearLayout {

    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_price_des)
    TextView mTvPriceDes;
    private View view;

    public GoodsDesView(Context context) {
        this(context, null);
    }

    public GoodsDesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        setOrientation(VERTICAL);
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_des, this, true);
        ButterKnife.bind(view);
    }
    public void setHeader(String url){
        Glide.with(getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .placeholder(R.mipmap.a4)
                .error(R.mipmap.a4)
                .into(mIvHeader);
    }
    public void setMoney(double number){
        mTvMoney.setText("￥"+ NumberUtil.getFormatNumber(number));
    }
    public void setCount(int number){
        mTvCount.setText("￥"+ number);
    }
    public void setPriceDes(double money,int number,double freight){
        String n = String.format("共%s件商品实付：",NumberUtil.number2Chinese(number));
        String m = "￥"+NumberUtil.getFormatNumber(money);
        String f = String.format("（含运费￥%s）",NumberUtil.getFormatNumber(freight));
        SpannableString sps = new SpannableString(n+m+f);
        sps.setSpan(new ForegroundColorSpan(AppUtil.getColor(R.color.colorPrimary)), n.length(), n.length()+m.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvPriceDes.setText(sps);
    }

}
