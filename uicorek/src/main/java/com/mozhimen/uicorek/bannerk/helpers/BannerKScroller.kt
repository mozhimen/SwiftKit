package com.mozhimen.uicorek.bannerk.helpers

import android.content.Context
import android.widget.Scroller

/**
 * @ClassName BannerKScroller
 * @Description 用于设置滚动的时长
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 17:26
 * @Version 1.0
 */
class BannerKScroller : Scroller {

    constructor(context: Context) : this(context, 1000)
    constructor(context: Context, duration: Int) : super(context) {
        this._duration = duration
    }

    private var _duration = 1000//值越大,滑动越慢

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, _duration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, _duration)
    }
}