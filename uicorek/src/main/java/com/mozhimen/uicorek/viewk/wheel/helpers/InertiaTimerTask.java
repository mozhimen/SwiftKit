package com.mozhimen.uicorek.viewk.wheel.helpers;

import com.mozhimen.uicorek.viewk.wheel.ViewKWheel;

import java.util.TimerTask;

/**
 * 滚动惯性的实现
 *
 * @author 小嵩
 * date:  2017-12-23 23:20:44
 */
public final class InertiaTimerTask extends TimerTask {

    private float mCurrentVelocityY; //当前滑动速度
    private final float mFirstVelocityY;//手指离开屏幕时的初始速度
    private final ViewKWheel mViewKWheel;

    /**
     * @param viewKWheel 滚轮对象
     * @param velocityY Y轴滑行速度
     */
    public InertiaTimerTask(ViewKWheel viewKWheel, float velocityY) {
        super();
        this.mViewKWheel = viewKWheel;
        this.mFirstVelocityY = velocityY;
        mCurrentVelocityY = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {

        //防止闪动，对速度做一个限制。
        if (mCurrentVelocityY == Integer.MAX_VALUE) {
            if (Math.abs(mFirstVelocityY) > 2000F) {
                mCurrentVelocityY = mFirstVelocityY > 0 ? 2000F : -2000F;
            } else {
                mCurrentVelocityY = mFirstVelocityY;
            }
        }

        //发送handler消息 处理平顺停止滚动逻辑
        if (Math.abs(mCurrentVelocityY) >= 0.0F && Math.abs(mCurrentVelocityY) <= 20F) {
            mViewKWheel.cancelFuture();
            mViewKWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }

        int dy = (int) (mCurrentVelocityY / 100F);
        mViewKWheel.setTotalScrollY(mViewKWheel.getTotalScrollY() - dy);
        if (!mViewKWheel.isLoop()) {
            float itemHeight = mViewKWheel.getItemHeight();
            float top = (-mViewKWheel.getInitPosition()) * itemHeight;
            float bottom = (mViewKWheel.getItemsCount() - 1 - mViewKWheel.getInitPosition()) * itemHeight;
            if (mViewKWheel.getTotalScrollY() - itemHeight * 0.25 < top) {
                top = mViewKWheel.getTotalScrollY() + dy;
            } else if (mViewKWheel.getTotalScrollY() + itemHeight * 0.25 > bottom) {
                bottom = mViewKWheel.getTotalScrollY() + dy;
            }

            if (mViewKWheel.getTotalScrollY() <= top) {
                mCurrentVelocityY = 40F;
                mViewKWheel.setTotalScrollY((int) top);
            } else if (mViewKWheel.getTotalScrollY() >= bottom) {
                mViewKWheel.setTotalScrollY((int) bottom);
                mCurrentVelocityY = -40F;
            }
        }

        if (mCurrentVelocityY < 0.0F) {
            mCurrentVelocityY = mCurrentVelocityY + 20F;
        } else {
            mCurrentVelocityY = mCurrentVelocityY - 20F;
        }

        //刷新UI
        mViewKWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }
}
