package com.mozhimen.uicoremk.refreshmk.commons;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mozhimen.basicsmk.utilmk.UtilMKDisplay;


/**
 * 下拉数显的Overlay视图,可以重载这个类来定义自己的Overlay
 */
public abstract class RefreshMKOverView extends FrameLayout {

    public enum RefreshMKStatus {
        /**
         * 初始态
         */
        STATE_INIT,
        /**
         * Header展示的状态
         */
        STATE_VISIBLE,
        /**
         * 刷新中的状态
         */
        STATE_REFRESH,
        /**
         * 头部超出可刷新距离的状态
         */
        STATE_OVER,
        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }

    protected RefreshMKStatus mkStatus = RefreshMKStatus.STATE_INIT;

    /**
     * 触发下拉刷新需要的最小高度
     */
    public int mPullRefreshHeight;

    /**
     * 最小阻尼
     */
    public float minDamp = 1.6f;
    /**
     * 最大阻尼
     */
    public float maxDamp = 2.2f;

    public RefreshMKOverView(@NonNull Context context) {
        super(context, null);
        preInit();
    }

    public RefreshMKOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public RefreshMKOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = UtilMKDisplay.INSTANCE.dp2px(80);
        init();
    }

    /**
     * 初始化
     */
    public abstract void init();

    public abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    public abstract void onVisible();

    /**
     * 超过Overlay,释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 加载完成
     */
    public abstract void onFinish();

    /**
     * 设置下拉刷新头部状态
     */
    public void setStatus(RefreshMKStatus status) {
        this.mkStatus = status;
    }

    /**
     * 获取状态
     *
     * @return mkStatus
     */
    public RefreshMKStatus getStatus() {
        return mkStatus;
    }
}
