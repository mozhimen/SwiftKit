package com.mozhimen.uicoremk.refreshmk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mozhimen.basicsmk.utilmk.UtilMKScroll;
import com.mozhimen.uicoremk.refreshmk.commons.IRefreshMK;
import com.mozhimen.uicoremk.refreshmk.commons.RefreshMKOverView;
import com.mozhimen.uicoremk.refreshmk.helpers.RefreshMKGestureDetector;

public class RefreshMKLayout extends FrameLayout implements IRefreshMK {
    private RefreshMKOverView.RefreshMKStatus mkStatus;
    private GestureDetector mGestureDetector;
    private RefreshMKListener mRefreshMKListener;
    protected RefreshMKOverView mkOverView;
    private int mLastY;
    private AutoScroller mAutoScroller;

    //刷新时是否禁止滚动
    private boolean disableRefreshScroll;

    public RefreshMKLayout(@NonNull Context context) {
        super(context, null);
        init();
    }

    public RefreshMKLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RefreshMKLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), mkGestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        final View head = getChildAt(0);
        mkOverView.onFinish();
        mkOverView.setStatus(RefreshMKOverView.RefreshMKStatus.STATE_INIT);
        final int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mkStatus = RefreshMKOverView.RefreshMKStatus.STATE_INIT;
    }

    @Override
    public void setRefreshListener(RefreshMKListener refreshListener) {
        this.mRefreshMKListener = refreshListener;
    }

    @Override
    public void setRefreshOverView(RefreshMKOverView refreshOverView) {
        if (mkOverView != null) {
            removeView(mkOverView);
        }
        this.mkOverView = refreshOverView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mkOverView, 0, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //事件分发处理
        if (!mAutoScroller.isFinished()) {
            return false;
        }

        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //松开手
            if (head.getBottom() > 0) {
                if (mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_REFRESH) {//非正在刷新的状态
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((consumed || (mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_INIT && mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_REFRESH)) && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);//让父类接受不到真实的事件
            return super.dispatchTouchEvent(ev);
        }

        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //定义head和child的排列位置
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            if (mkStatus == RefreshMKOverView.RefreshMKStatus.STATE_REFRESH) {
                head.layout(0, mkOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, mkOverView.mPullRefreshHeight);
                child.layout(0, mkOverView.mPullRefreshHeight, right, mkOverView.mPullRefreshHeight + head.getMeasuredHeight());
            } else {
                head.layout(0, childTop - head.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }
            View other;
            //让HiRefreshLayout节点下两个以上的child能够不跟随手势移动以实现一些特殊效果，如悬浮的效果
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
        }
    }

    /**
     * 恢复头部
     *
     * @param distance 滚动的距离
     */
    private void recover(int distance) {
        if (mRefreshMKListener != null && distance > mkOverView.mPullRefreshHeight) {
            //滚动到指定位置distance-mkOverView.mPullRefreshHeight
            mAutoScroller.recover(distance - mkOverView.mPullRefreshHeight);
            mkStatus = RefreshMKOverView.RefreshMKStatus.STATE_OVER_RELEASE;
        } else {
            mAutoScroller.recover(distance);
        }
    }

    RefreshMKGestureDetector mkGestureDetector = new RefreshMKGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY) || mRefreshMKListener != null && !mRefreshMKListener.enableRefresh()) {
                //横向滑动,或刷新被禁止则不处理
                return false;
            }
            if (disableRefreshScroll && mkStatus == RefreshMKOverView.RefreshMKStatus.STATE_REFRESH) {//刷新时是否禁止滚动
                return true;
            }

            View head = getChildAt(0);
            View child = UtilMKScroll.INSTANCE.findScrollableChild(RefreshMKLayout.this);
            if (UtilMKScroll.INSTANCE.childScrolled(child)) {
                //如果列表发生了滚动则不处理.
                return false;
            }
            //没有刷新或者内有达到可以刷新的距离,且头部已经划出或下拉
            if ((mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_REFRESH || head.getBottom() <= mkOverView.mPullRefreshHeight) && (head.getBottom() > 0 || distanceY <= 0.0f)) {
                //还在滑动中
                if (mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_OVER_RELEASE) {
                    int seed;
                    //速度计算
                    if (child.getTop() < mkOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / mkOverView.minDamp);
                    } else {
                        seed = (int) (mLastY / mkOverView.maxDamp);
                    }
                    //如果是在正在刷新状态,则不允许在滑动的时候改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) (-distanceY);
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动触发
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) {//异常情况的补充
            offsetY = -child.getTop();
            //移动head与child的位置到原始位置
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mkStatus != RefreshMKOverView.RefreshMKStatus.STATE_REFRESH) {
                mkStatus = RefreshMKOverView.RefreshMKStatus.STATE_INIT;
            }
        } else if (mkStatus == RefreshMKOverView.RefreshMKStatus.STATE_REFRESH && childTop > mkOverView.mPullRefreshHeight) {
            //如果正在下拉刷新中,禁止继续下拉
            return false;
        } else if (childTop <= mkOverView.mPullRefreshHeight) {//还没有超出设定的刷新距离
            if (mkOverView.getStatus() != RefreshMKOverView.RefreshMKStatus.STATE_VISIBLE && nonAuto) {//头部开始显示
                mkOverView.onVisible();
                mkOverView.setStatus(RefreshMKOverView.RefreshMKStatus.STATE_VISIBLE);
                mkStatus = RefreshMKOverView.RefreshMKStatus.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mkOverView.mPullRefreshHeight && mkStatus == RefreshMKOverView.RefreshMKStatus.STATE_OVER_RELEASE) {
                //开始下拉刷新
                refresh();
            }
        } else {
            if (mkOverView.getStatus() != RefreshMKOverView.RefreshMKStatus.STATE_OVER && nonAuto) {
                //超出刷新位置
                mkOverView.onOver();
                mkOverView.setStatus(RefreshMKOverView.RefreshMKStatus.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mkOverView != null) {
            mkOverView.onScroll(head.getBottom(), mkOverView.mPullRefreshHeight);
        }
        return true;
    }

    /**
     * 开始刷新
     */
    private void refresh() {
        if (mRefreshMKListener != null) {
            mkStatus = RefreshMKOverView.RefreshMKStatus.STATE_REFRESH;
            mkOverView.onRefresh();
            mkOverView.setStatus(RefreshMKOverView.RefreshMKStatus.STATE_REFRESH);
            mRefreshMKListener.onRefresh();
        }
    }

    /**
     * 借助Scroller实现视图的自动滚动
     */
    private class AutoScroller implements Runnable {

        private Scroller mScroller;
        private int mLastY;
        private boolean mIsFinished;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                //还未滚动完成
                //moveDown mLastY-mScroller.getCurrY()
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int distance) {
            if (distance <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mScroller.startScroll(0, 0, 0, distance, 500);
            post(this);
        }

        boolean isFinished() {
            return mIsFinished;
        }
    }
}
