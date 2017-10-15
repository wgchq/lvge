package lvge.com.myapp.ui.binder;

import android.content.Context;
import android.content.Intent;
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
import lvge.com.myapp.ui.activity.OrderDetailActivity;
import lvge.com.myapp.view.GoodsDesView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by android on 2017/10/13.
 */

public class OrderItemCommonBinder extends ItemViewBinder<OrderItemModel, OrderItemCommonBinder.ViewHolder> {

    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.goodsDesView)
    GoodsDesView mGoodsDesView;
    @BindView(R.id.tv_action)
    TextView mTvAction;
    @BindView(R.id.tv_detail)
    TextView mTvDetail;

    private Context mContext;
    public OrderItemCommonBinder(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.include_item_order, parent, true);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull OrderItemModel item) {
        setAction(item);

    }

    private void setAction(@NonNull OrderItemModel item) {
        String actionName = "";
        String statusName = "";
        mTvAction.setTag(item);
        View.OnClickListener listener = null;
        switch (item.status){
            case  OrderItemModel.WAIT_VERIFY:
                actionName = "扫描验证";
                statusName = "待验证";
                listener= onVerifyListener;
                break;
            case  OrderItemModel.SENDING:
                actionName = "";
                statusName = "";
                break;
            case  OrderItemModel.WAIT_INSTALL:
                actionName = "待安装";
                statusName = "";
                break;
            case  OrderItemModel.WAIT_SEND:
                actionName = "发货";
                statusName = "待发货";
                listener= onCommonListener;
                break;
            case  OrderItemModel.FINISHED:
                actionName = "";
                statusName = "已完成";
                listener= onCommonListener;
                break;
        }
        mTvAction.setText(actionName);
        mTvStatus.setText(statusName);
        mTvAction.setOnClickListener(listener);

    }
    private View.OnClickListener onVerifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderItemModel model = (OrderItemModel) v.getTag();
            // TODO: 2017/10/13 启动验证页面
        }
    };
    private View.OnClickListener onCommonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderItemModel model = (OrderItemModel) v.getTag();
            if (model == null)
                return;
            Intent intent = new Intent();
            intent.putExtra("OrderItemModel",model);
            intent.setClass(mContext,OrderDetailActivity.class);
            mContext.startActivity(intent);
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
