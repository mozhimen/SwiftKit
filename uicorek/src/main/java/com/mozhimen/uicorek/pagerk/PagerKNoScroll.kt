package com.mozhimen.uicorek.pagerk

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ClassName ViewPagerMKNoScroll
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/27 21:39
 * @Version 1.0
 */
open class PagerKNoScroll @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ViewPager(context, attrs) {
    protected var _isEnableScroll = true

    /**
     * 是否enableScroll
     * @param isEnableScroll Boolean
     */
    fun setEnableScroll(isEnableScroll: Boolean) {
        _isEnableScroll = isEnableScroll
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        //return false// 可行,不消费,传给父控件
        //return true// 可行,消费,拦截事件
        //super.onTouchEvent(ev) //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewGroup,还是会调用这个方法.
        return if (_isEnableScroll) {
            super.onTouchEvent(ev)
        } else {
            true// 可行,消费,拦截事件
        }
    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // return false//可行,不拦截事件,
        return if (_isEnableScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }
}