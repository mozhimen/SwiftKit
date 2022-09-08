package com.mozhimen.uicorek.layoutk.slider.mos

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * @ClassName ViewKTouch
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:05
 * @Version 1.0
 */
typealias IViewKTouchListener = () -> Unit

private class ViewKTouch(context: Context, viewRect: Rect) : FrameLayout(context) {
    private val viewRect: Rect

    private var _viewKTouchListener: IViewKTouchListener? = null

    fun setCallback(callback: IViewKTouchListener) {
        this.callback = callback
    }

    init {
        this.viewRect = viewRect
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        if (x >= viewRect.left && x <= viewRect.right && y >= viewRect.top && y <= viewRect.bottom) {
            return false
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (callback != null) {
                callback!!.onClicked()
            }
        }
        return true
    }
}