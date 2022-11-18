package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import com.mozhimen.uicorek.layoutk.commons.LayoutKFrame

/**
 * @ClassName ViewKTouch
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:05
 * @Version 1.0
 */
typealias ILayoutKTouchListener = () -> Unit

class LayoutKTouch @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    LayoutKFrame(context, attrs, defStyleAttr, defStyleRes) {
    //region # variate
    private var _viewKTouchListener: ILayoutKTouchListener? = null
    private var _touchAreaRect: Rect? = null
    //endregion

    /**
     * 触摸的区域范围(内)
     * @param areaRect Rect
     */
    fun setTouchArea(areaRect: Rect) {
        _touchAreaRect = areaRect
    }

    /**
     * 设置出发监听器
     * @param listener Function0<Unit>
     */
    fun setOnTouchListener(listener: ILayoutKTouchListener) {
        _viewKTouchListener = listener
    }

    init {
        initFlag()
    }

    override fun initFlag() {
        setBackgroundColor(Color.TRANSPARENT)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        _touchAreaRect ?: return false
        val touchX = event.x
        val touchY = event.y
        if (touchX >= _touchAreaRect!!.left && touchX <= _touchAreaRect!!.right && touchY >= _touchAreaRect!!.top && touchY <= _touchAreaRect!!.bottom) {
            return false
        } else if (event.action == MotionEvent.ACTION_UP) {
            _viewKTouchListener?.invoke()
        }
        return true
    }
}