package com.mozhimen.uicorek.viewk.wheel.helpers;

import com.mozhimen.uicorek.viewk.wheel.ViewKWheel;

import java.util.TimerTask;

/**
 * 平滑滚动的实现
 *
 * @author 小嵩
 */
public final class SmoothScrollTimerTask extends TimerTask {

    private int realTotalOffset;
    private int realOffset;
    private int offset;
    private final ViewKWheel viewKWheel;

    public SmoothScrollTimerTask(ViewKWheel viewKWheel, int offset) {
        this.viewKWheel = viewKWheel;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分成10小份，按10小份单位来重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            viewKWheel.cancelFuture();
            viewKWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            viewKWheel.setTotalScrollY(viewKWheel.getTotalScrollY() + realOffset);

            //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
            if (!viewKWheel.isLoop()) {
                float itemHeight = viewKWheel.getItemHeight();
                float top = (float) (-viewKWheel.getInitPosition()) * itemHeight;
                float bottom = (float) (viewKWheel.getItemsCount() - 1 - viewKWheel.getInitPosition()) * itemHeight;
                if (viewKWheel.getTotalScrollY() <= top || viewKWheel.getTotalScrollY() >= bottom) {
                    viewKWheel.setTotalScrollY(viewKWheel.getTotalScrollY() - realOffset);
                    viewKWheel.cancelFuture();
                    viewKWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            viewKWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
