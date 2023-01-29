package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKGrid

/**
 * @ClassName LayoutKGridUnTouch
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/28 23:53
 * @Version 1.0
 */
class LayoutKGridUnTouch @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseLayoutKGrid(context, attrs, defStyleAttr, defStyleRes) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}