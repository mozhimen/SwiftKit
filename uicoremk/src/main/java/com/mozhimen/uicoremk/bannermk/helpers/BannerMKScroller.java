package com.mozhimen.uicoremk.bannermk.helpers;

import android.content.Context;
import android.widget.Scroller;

public class BannerMKScroller extends Scroller {

    /**
     * 值越大,滑动越慢
     *
     * @param context
     */
    private int mDuration = 1000;

    public BannerMKScroller(Context context) {
        super(context);
    }

    public BannerMKScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
