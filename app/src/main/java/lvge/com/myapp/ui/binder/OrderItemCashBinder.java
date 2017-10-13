package lvge.com.myapp.ui.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lvge.com.myapp.R;
import lvge.com.myapp.model.order.OrderItemModel;
import lvge.com.myapp.util.NumberUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by android on 2017/10/13.
 */

public class OrderItemCashBinder extends ItemViewBinder<OrderItemModel, OrderItemCashBinder.ViewHolder> {


    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_money)
    TextView mTvMoney;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_order_cash, parent, true);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull OrderItemModel item) {
        mTvOrderNo.setText(item.getOrderNO());
        mTvMoney.setText(NumberUtil.getFormatNumber(item.getTotalPrice()));

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
