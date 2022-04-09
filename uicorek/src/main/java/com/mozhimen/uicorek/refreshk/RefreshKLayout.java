package com.mozhimen.uicorek.refreshk;

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

import com.mozhimen.basicsk.utilk.UtilKScroll;
import com.mozhimen.uicorek.refreshk.commons.IRefreshK;
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView;
import com.mozhimen.uicorek.refreshk.helpers.RefreshKGestureDetector;

public class RefreshKLayout extends FrameLayout implements IRefreshK {
    private RefreshKOverView.RefreshKStatus kStatus;
    private GestureDetector mGestureDetector;
    private RefreshKListener mRefreshKListener;
    protected RefreshKOverView kOverView;
    private int mLastY;
    private AutoScroller mAutoScroller;

    //刷新时是否禁止滚动
    private boolean disableRefreshScroll;

    public RefreshKLayout(@NonNull Context context) {
        super(context, null);
        init();
    }

    public RefreshKLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RefreshKLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), kGestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        final View head = getChildAt(0);
        kOverView.onFinish();
        kOverView.setStatus(RefreshKOverView.RefreshKStatus.STATE_INIT);
        final int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        kStatus = RefreshKOverView.RefreshKStatus.STATE_INIT;
    }

    @Override
    public void setRefreshListener(RefreshKListener refreshListener) {
        this.mRefreshKListener = refreshListener;
    }

    @Override
    public void setRefreshOverView(RefreshKOverView refreshOverView) {
        if (kOverView != null) {
            removeView(kOverView);
        }
        this.kOverView = refreshOverView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(kOverView, 0, params);
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
                if (kStatus != RefreshKOverView.RefreshKStatus.STATE_REFRESH) {//非正在刷新的状态
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((consumed || (kStatus != RefreshKOverView.RefreshKStatus.STATE_INIT && kStatus != RefreshKOverView.RefreshKStatus.STATE_REFRESH)) && head.getBottom() != 0) {
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
            if (kStatus == RefreshKOverView.RefreshKStatus.STATE_REFRESH) {
                head.layout(0, kOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, kOverView.mPullRefreshHeight);
                child.layout(0, kOverView.mPullRefreshHeight, right, kOverView.mPullRefreshHeight + head.getMeasuredHeight());
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
        if (mRefreshKListener != null && distance > kOverView.mPullRefreshHeight) {
            //滚动到指定位置distance-kOverView.mPullRefreshHeight
            mAutoScroller.recover(distance - kOverView.mPullRefreshHeight);
            kStatus = RefreshKOverView.RefreshKStatus.STATE_OVER_RELEASE;
        } else {
            mAutoScroller.recover(distance);
        }
    }

    RefreshKGestureDetector kGestureDetector = new RefreshKGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY) || mRefreshKListener != null && !mRefreshKListener.enableRefresh()) {
                //横向滑动,或刷新被禁止则不处理
                return false;
            }
            if (disableRefreshScroll && kStatus == RefreshKOverView.RefreshKStatus.STATE_REFRESH) {//刷新时是否禁止滚动
                return true;
            }

            View head = getChildAt(0);
            View child = UtilKScroll.INSTANCE.findScrollableChild(RefreshKLayout.this);
            if (UtilKScroll.INSTANCE.childScrolled(child)) {
                //如果列表发生了滚动则不处理.
                return false;
            }
            //没有刷新或者内有达到可以刷新的距离,且头部已经划出或下拉
            if ((kStatus != RefreshKOverView.RefreshKStatus.STATE_REFRESH || head.getBottom() <= kOverView.mPullRefreshHeight) && (head.getBottom() > 0 || distanceY <= 0.0f)) {
                //还在滑动中
                if (kStatus != RefreshKOverView.RefreshKStatus.STATE_OVER_RELEASE) {
                    int seed;
                    //速度计算
                    if (child.getTop() < kOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / kOverView.minDamp);
                    } else {
                        seed = (int) (mLastY / kOverView.maxDamp);
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
            if (kStatus != RefreshKOverView.RefreshKStatus.STATE_REFRESH) {
                kStatus = RefreshKOverView.RefreshKStatus.STATE_INIT;
            }
        } else if (kStatus == RefreshKOverView.RefreshKStatus.STATE_REFRESH && childTop > kOverView.mPullRefreshHeight) {
            //如果正在下拉刷新中,禁止继续下拉
            return false;
        } else if (childTop <= kOverView.mPullRefreshHeight) {//还没有超出设定的刷新距离
            if (kOverView.getStatus() != RefreshKOverView.RefreshKStatus.STATE_VISIBLE && nonAuto) {//头部开始显示
                kOverView.onVisible();
                kOverView.setStatus(RefreshKOverView.RefreshKStatus.STATE_VISIBLE);
                kStatus = RefreshKOverView.RefreshKStatus.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == kOverView.mPullRefreshHeight && kStatus == RefreshKOverView.RefreshKStatus.STATE_OVER_RELEASE) {
                //开始下拉刷新
                refresh();
            }
        } else {
            if (kOverView.getStatus() != RefreshKOverView.RefreshKStatus.STATE_OVER && nonAuto) {
                //超出刷新位置
                kOverView.onOver();
                kOverView.setStatus(RefreshKOverView.RefreshKStatus.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (kOverView != null) {
            kOverView.onScroll(head.getBottom(), kOverView.mPullRefreshHeight);
        }
        return true;
    }

    /**
     * 开始刷新
     */
    private void refresh() {
        if (mRefreshKListener != null) {
            kStatus = RefreshKOverView.RefreshKStatus.STATE_REFRESH;
            kOverView.onRefresh();
            kOverView.setStatus(RefreshKOverView.RefreshKStatus.STATE_REFRESH);
            mRefreshKListener.onRefresh();
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
