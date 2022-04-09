package com.mozhimen.uicorek.bannerk.helpers;

import android.content.Context;
import android.widget.Scroller;

public class BannerKScroller extends Scroller {

    /**
     * 值越大,滑动越慢
     *
     * @param context
     */
    private int mDuration = 1000;

    public BannerKScroller(Context context) {
        super(context);
    }

    public BannerKScroller(Context context, int duration) {
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
