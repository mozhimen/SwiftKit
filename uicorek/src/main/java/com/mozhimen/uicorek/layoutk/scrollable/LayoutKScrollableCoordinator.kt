package com.mozhimen.uicorek.layoutk.scrollable

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * @ClassName LayoutKScrollableCoordinator
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/6 10:19
 * @Version 1.0
 */
class LayoutKScrollableCoordinator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    CoordinatorLayout(context, attrs, defStyleAttr) {
    private var _scrollable = true

    ///////////////////////////////////////////////////////////////

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
        if (!_scrollable) false else super.onInterceptTouchEvent(ev)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (_scrollable) super.onTouchEvent(ev)
        return true
    }

    ///////////////////////////////////////////////////////////////

    fun setScrollable(scrollable: Boolean) {
        _scrollable = scrollable
    }
}