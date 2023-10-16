package com.mozhimen.uicorek.recyclerk

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.android.view.cons.CMotionEvent
import kotlin.math.abs

/**
 * @ClassName RecyclerKNested
 * @Description 处理上下滑动时会出现左右滑动的问题
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 17:38
 * @Version 1.0
 */

class RecyclerKNested @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {
    private var _startX = 0
    private var _startY = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            CMotionEvent.ACTION_DOWN -> {
                _startX = ev.x.toInt()
                _startY = ev.y.toInt()
                //告诉viewGroup不要去拦截我
                parent.requestDisallowInterceptTouchEvent(true)
            }

            CMotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - _startX)
                val disY = abs(endY - _startY)
                //下拉的时候是false
                parent.requestDisallowInterceptTouchEvent(disX <= disY)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(true)
            else -> {}
        }
        return super.dispatchTouchEvent(ev)
    }
}
