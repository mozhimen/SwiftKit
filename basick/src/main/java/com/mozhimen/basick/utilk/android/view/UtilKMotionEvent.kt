package com.mozhimen.basick.utilk.android.view

import android.view.MotionEvent
import com.mozhimen.basick.elemk.android.view.cons.CMotionEvent
import com.mozhimen.basick.utilk.kotlin.math.UtilKPoint

/**
 * @ClassName UtilKGesture
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:05
 * @Version 1.0
 */
fun MotionEvent.motionEvent2str(): String =
    UtilKMotionEvent.motionEvent2str(this)

object UtilKMotionEvent {

    /**
     * 是否点击区域内
     */
    @JvmStatic
    fun isTapInArea(event: MotionEvent, left: Float, top: Float, right: Float, bottom: Float): Boolean =
        isTapInArea(event.x, event.y, left, top, right, bottom)

    /**
     * 是否点击区域内
     */
    @JvmStatic
    fun isTapInArea(x: Float, y: Float, left: Float, top: Float, right: Float, bottom: Float): Boolean =
        x > left && x < right && y > top && y < bottom

    /**
     * 计算手指间间距
     */
    @JvmStatic
    fun distance(motionEvent: MotionEvent): Float =
        if (motionEvent.pointerCount >= 2)
            UtilKPoint.distance(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1))
        else 0f

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun motionEvent2str(motionEvent: MotionEvent): String =
        intAction2str(motionEvent.action)

    @JvmStatic
    fun intAction2str(intAction: Int): String {
        when (intAction) {
            CMotionEvent.ACTION_DOWN -> return CMotionEvent.Str.ACTION_DOWN
            CMotionEvent.ACTION_UP -> return CMotionEvent.Str.ACTION_UP
            CMotionEvent.ACTION_CANCEL -> return CMotionEvent.Str.ACTION_CANCEL
            CMotionEvent.ACTION_OUTSIDE -> return CMotionEvent.Str.ACTION_OUTSIDE
            CMotionEvent.ACTION_MOVE -> return CMotionEvent.Str.ACTION_MOVE
            CMotionEvent.ACTION_HOVER_MOVE -> return CMotionEvent.Str.ACTION_HOVER_MOVE
            CMotionEvent.ACTION_SCROLL -> return CMotionEvent.Str.ACTION_SCROLL
            CMotionEvent.ACTION_HOVER_ENTER -> return CMotionEvent.Str.ACTION_HOVER_ENTER
            CMotionEvent.ACTION_HOVER_EXIT -> return CMotionEvent.Str.ACTION_HOVER_EXIT
            CMotionEvent.ACTION_BUTTON_PRESS -> return CMotionEvent.Str.ACTION_BUTTON_PRESS
            CMotionEvent.ACTION_BUTTON_RELEASE -> return CMotionEvent.Str.ACTION_BUTTON_RELEASE
        }
        val index = intAction and CMotionEvent.ACTION_POINTER_INDEX_MASK shr CMotionEvent.ACTION_POINTER_INDEX_SHIFT
        return when (intAction and CMotionEvent.ACTION_MASK) {
            CMotionEvent.ACTION_POINTER_DOWN -> "${CMotionEvent.Str.ACTION_POINTER_DOWN}($index)"
            CMotionEvent.ACTION_POINTER_UP -> "${CMotionEvent.Str.ACTION_POINTER_UP}($index)"
            else -> intAction.toString()
        }
    }
}