package lvge.com.myapp.view;

/**
 * Created by xeq on 16/12/1.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lvge.com.myapp.R;
import lvge.com.myapp.util.AppUtil;
import lvge.com.myapp.util.DensityUtil;


public class LoadingLayout extends FrameLayout {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;
    private int state;

    private Context mContext;
    private View loadingPage;
    private View errorPage;
    private View emptyPage;
    private View networkPage;
//    private View defineLoadingPage;

    private ImageView errorImg;
    private ImageView emptyImg;
    private ImageView networkImg;

    private TextView errorText;
    private TextView emptyText;
    private TextView networkText;

    private TextView emptyReloadBtn;
    private TextView errorReloadBtn;
    private TextView networkReloadBtn;

    private View contentView;
    private LoadingLayout.OnReloadListener listener;
    private boolean isFirstVisible; //是否一开始显示contentview，默认不显示

    //配置
    private static LoadingLayout.Config mConfig = new LoadingLayout.Config();
    private static String emptyStr = "暂无数据";
    private static String errorStr = "加载失败，请稍后重试···";
    private static String netwrokStr = "无网络连接，请检查网络···";
    private static String reloadBtnStr = "点击重试";
    private static String emptyBtnStr = "刷新";
    private static int emptyImgId = R.mipmap.main_page_fence_management;
    private static int errorImgId = R.mipmap.main_page_fence_management;//需要定义错误
    private static int networkImgId = R.mipmap.main_page_fence_management;
    private static int reloadBtnId = R.drawable.view_retage_bule;//重试按钮 的边框
    private static int tipTextSize = 14;
    private static int buttonTextSize = 14;
    private static int tipTextColor = R.color.view_base_text_color_light;
    private static int buttonTextColor = R.color.view_base_text_color_light;
    private static int buttonWidth = -1;
    private static int buttonHeight = -1;
    private static int loadingLayoutId = R.layout.view_loading_page;
//    private AnimationDrawable ladingAnimaytion;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.view_LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.view_LoadingLayout_view_isFirstVisible, false);
        a.recycle();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.view_LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.view_LoadingLayout_view_isFirstVisible, false);
        a.recycle();
    }

    /**
     * 用来在java代码中实例化的时候，调用做初始化工作
     */
    public void init() {
        onFinishInflate();
    }

    public LoadingLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        if (!isFirstVisible) {
            contentView.setVisibility(View.GONE);
        }
        build();
    }

    private void build() {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        loadingPage = inflate.inflate(loadingLayoutId, null);
        errorPage = inflate.inflate(R.layout.view_widget_error_page, null);
        emptyPage = inflate.inflate(R.layout.view_widget_empty_page, null);
        networkPage = inflate.inflate(R.layout.view_widget_nonetwork_page, null);

        errorText = AppUtil.findViewById(errorPage, R.id.error_text);
        emptyText = AppUtil.findViewById(emptyPage, R.id.empty_text);
        networkText = AppUtil.findViewById(networkPage, R.id.no_network_text);

        errorImg = AppUtil.findViewById(errorPage, R.id.error_img);
        emptyImg = AppUtil.findViewById(emptyPage, R.id.empty_img);
        networkImg = AppUtil.findViewById(networkPage, R.id.no_network_img);

        emptyReloadBtn = AppUtil.findViewById(emptyPage, R.id.enpty_reload_btn);
        errorReloadBtn = AppUtil.findViewById(errorPage, R.id.error_reload_btn);
        networkReloadBtn = AppUtil.findViewById(networkPage, R.id.no_network_reload_btn);

//        ladingAnimaytion = (AnimationDrawable) loadingPage.findViewById(R.id.iv_loading).getBackground();
        emptyReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onReload(v);
                }
            }
        });
        errorReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onReload(v);
                }
            }
        });
        networkReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onReload(v);
                }
            }
        });

        errorText.setText(errorStr);
        emptyText.setText(emptyStr);
        networkText.setText(netwrokStr);

        errorText.setTextSize(tipTextSize);
        emptyText.setTextSize(tipTextSize);
        networkText.setTextSize(tipTextSize);

        errorText.setTextColor(AppUtil.getColor(tipTextColor));
        emptyText.setTextColor(AppUtil.getColor( tipTextColor));
        networkText.setTextColor(AppUtil.getColor(tipTextColor));

        errorImg.setImageResource(errorImgId);
        emptyImg.setImageResource(emptyImgId);
        networkImg.setImageResource(networkImgId);

        emptyReloadBtn.setBackgroundResource(reloadBtnId);
        errorReloadBtn.setBackgroundResource(reloadBtnId);
        networkReloadBtn.setBackgroundResource(reloadBtnId);

        emptyReloadBtn.setText(emptyBtnStr);
        errorReloadBtn.setText(reloadBtnStr);
        networkReloadBtn.setText(reloadBtnStr);

        emptyReloadBtn.setTextSize(buttonTextSize);
        errorReloadBtn.setTextSize(buttonTextSize);
        networkReloadBtn.setTextSize(buttonTextSize);

        emptyReloadBtn.setTextColor(AppUtil.getColor(buttonTextColor));
        errorReloadBtn.setTextColor(AppUtil.getColor(buttonTextColor));
        networkReloadBtn.setTextColor(AppUtil.getColor(buttonTextColor));

        if (buttonHeight != -1) {

            emptyReloadBtn.setHeight(DensityUtil.dp2px(mContext, buttonHeight));
            errorReloadBtn.setHeight(DensityUtil.dp2px(mContext, buttonHeight));
            networkReloadBtn.setHeight(DensityUtil.dp2px(mContext, buttonHeight));
        }
        if (buttonWidth != -1) {

            emptyReloadBtn.setWidth(DensityUtil.dp2px(mContext, buttonWidth));
            errorReloadBtn.setWidth(DensityUtil.dp2px(mContext, buttonWidth));
            networkReloadBtn.setWidth(DensityUtil.dp2px(mContext, buttonWidth));
        }

        this.addView(networkPage);
        this.addView(emptyPage);
        this.addView(errorPage);
        this.addView(loadingPage);
    }

    public void setStatus(@Flavour int status) {
        this.state = status;
//        public final static int Success = 0;
//        public final static int Empty = 1;
//        public final static int Error = 2;
//        public final static int No_Network = 3;
//        public final static int Loading = 4;
        switch (status) {
            case Success:

                contentView.setVisibility(View.VISIBLE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
//                ladingAnimaytion.stop();
                break;

            case Loading:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.VISIBLE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
//                ladingAnimaytion.start();
                break;

            case Empty:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.VISIBLE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
//                ladingAnimaytion.stop();
                break;

            case Error:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.VISIBLE);
                networkPage.setVisibility(View.GONE);
//                ladingAnimaytion.stop();
                break;

            case No_Network:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.VISIBLE);
//                ladingAnimaytion.stop();
                break;

            default:
                break;
        }

    }


    /**
     * 返回当前状态{Success, Empty, Error, No_Network, Loading}
     *
     * @return
     */
    public int getStatus() {

        return state;
    }

    /**
     * 设置Empty状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setEmptyText(String text) {

        emptyText.setText(text);
        return this;
    }

    /**
     * 设置Error状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setErrorText(String text) {

        errorText.setText(text);
        return this;
    }

    /**
     * 设置No_Network状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setNoNetworkText(String text) {

        networkText.setText(text);
        return this;
    }

    /**
     * 设置Empty状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setEmptyImage(@DrawableRes int id) {


        emptyImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Error状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setErrorImage(@DrawableRes int id) {

        errorImg.setImageResource(id);
        return this;
    }

    /**
     * 设置No_Network状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setNoNetworkImage(@DrawableRes int id) {

        networkImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Empty状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setEmptyTextSize(int sp) {

        emptyText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Error状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setErrorTextSize(int sp) {

        errorText.setTextSize(sp);
        return this;
    }

    /**
     * 设置No_Network状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setNoNetworkTextSize(int sp) {

        networkText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Empty状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setEmptyImageVisible(boolean bool) {

        if (bool) {
            emptyImg.setVisibility(View.VISIBLE);
        } else {
            emptyImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置Error状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setErrorImageVisible(boolean bool) {

        if (bool) {
            errorImg.setVisibility(View.VISIBLE);
        } else {
            errorImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置No_Network状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setNoNetworkImageVisible(boolean bool) {

        if (bool) {
            networkImg.setVisibility(View.VISIBLE);
        } else {
            networkImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置ReloadButton的文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setReloadButtonText(@NonNull String text) {
        emptyReloadBtn.setText(text);
        errorReloadBtn.setText(text);
        networkReloadBtn.setText(text);
        return this;
    }

    /**
     * 设置ReloadButton的文本字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setReloadButtonTextSize(int sp) {
        emptyReloadBtn.setTextSize(sp);
        errorReloadBtn.setTextSize(sp);
        networkReloadBtn.setTextSize(sp);
        return this;
    }

    /**
     * 设置ReloadButton的文本颜色，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonTextColor(@ColorRes int id) {
        emptyReloadBtn.setTextColor(AppUtil.getColor( id));
        errorReloadBtn.setTextColor(AppUtil.getColor(id));
        networkReloadBtn.setTextSize(AppUtil.getColor(id));
        return this;
    }

    /**
     * 设置ReloadButton的背景，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonBackgroundResource(@DrawableRes int id) {
        emptyReloadBtn.setBackgroundResource(id);
        errorReloadBtn.setBackgroundResource(id);
        networkReloadBtn.setBackgroundResource(id);
        return this;
    }

    /**
     * 设置ReloadButton的监听器
     *
     * @param listener
     * @return
     */
    public LoadingLayout setOnReloadListener(OnReloadListener listener) {

        this.listener = listener;
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @param view
     * @return
     */
    public LoadingLayout setLoadingPage(View view) {

        loadingPage = view;
        this.removeView(loadingPage);
        this.addView(view);
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setLoadingPage(@LayoutRes int id) {

        this.removeView(loadingPage);
        View view = LayoutInflater.from(mContext).inflate(id, null);
        loadingPage = view;
        this.addView(view);
        return this;
    }

    /**
     * 获取当前自定义的loadingpage
     *
     * @return
     */
    public View getLoadingPage() {

        return loadingPage;
    }


    /**
     * 获取全局使用的loadingpage
     *
     * @return
     */
    public View getGlobalLoadingPage() {

        return loadingPage;
    }

    @IntDef({Success, Empty, Error, No_Network, Loading})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flavour {

    }

    public interface OnReloadListener {

        void onReload(View v);
    }

    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static Config getConfig() {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {

        public Config setErrorText(@NonNull String text) {

            errorStr = text;
            return mConfig;
        }

        public Config setEmptyText(@NonNull String text) {

            emptyStr = text;
            return mConfig;
        }

        public Config setNoNetworkText(@NonNull String text) {

            netwrokStr = text;
            return mConfig;
        }

        public Config setReloadButtonText(@NonNull String text) {

            reloadBtnStr = text;
            return mConfig;
        }

        public Config setEmptyButtonText(@NonNull String text) {

            emptyBtnStr = text;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体大小
         *
         * @param sp
         * @return
         */
        public Config setAllTipTextSize(int sp) {

            tipTextSize = sp;
            return mConfig;
        }


        /**
         * 设置所有提示文本的字体颜色
         *
         * @param color
         * @return
         */
        public Config setAllTipTextColor(@ColorRes int color) {

            tipTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonTextSize(int sp) {

            buttonTextSize = sp;
            return mConfig;
        }

        public Config setReloadButtonTextColor(@ColorRes int color) {

            buttonTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonBackgroundResource(@DrawableRes int id) {

            reloadBtnId = id;
            return mConfig;
        }

        public Config setReloadButtonWidthAndHeight(int width_dp, int height_dp) {

            buttonWidth = width_dp;
            buttonHeight = height_dp;
            return mConfig;
        }

        public Config setErrorImage(@DrawableRes int id) {

            errorImgId = id;
            return mConfig;
        }

        public Config setEmptyImage(@DrawableRes int id) {

            emptyImgId = id;
            return this;
        }

        public Config setNoNetworkImage(@DrawableRes int id) {

            networkImgId = id;
            return mConfig;
        }

        public Config setLoadingPageLayout(@LayoutRes int id) {

            loadingLayoutId = id;
            return mConfig;
        }

    }

    //设置数据为空时刷新的按钮的显示与否
    public void setEmptyReloadBtnVisible(boolean isShow) {
        if (isShow) {
            emptyReloadBtn.setVisibility(VISIBLE);
        } else {
            emptyReloadBtn.setVisibility(GONE);
        }
    }
}

