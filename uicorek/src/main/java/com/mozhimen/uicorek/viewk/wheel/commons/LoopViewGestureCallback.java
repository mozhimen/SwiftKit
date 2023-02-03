package com.mozhimen.uicorek.viewk.wheel.commons;

import android.view.MotionEvent;

import com.mozhimen.uicorek.viewk.wheel.ViewKWheel;


/**
 * 手势监听
 */
public final class LoopViewGestureCallback extends android.view.GestureDetector.SimpleOnGestureListener {

    private final ViewKWheel viewKWheel;

    public LoopViewGestureCallback(ViewKWheel viewKWheel) {
        this.viewKWheel = viewKWheel;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        viewKWheel.scrollBy(velocityY);
        return true;
    }
}
