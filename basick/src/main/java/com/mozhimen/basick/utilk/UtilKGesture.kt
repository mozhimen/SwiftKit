package com.mozhimen.basick.utilk

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

/**
 * @ClassName UtilKGesture
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:05
 * @Version 1.0
 */
object UtilKGesture {

    private const val TAG = "UtilKGesture>>>>>"

    /**
     * 计算手指间间距
     * @param event MotionEvent
     * @return Float
     */
    @JvmStatic
    fun distance(event: MotionEvent): Float {
        return if (event.pointerCount >= 2) {
            UtilKNumber.distance(
                event.getX(0),
                event.getY(0),
                event.getX(1),
                event.getY(1)
            )
        } else 0f
    }

    /**
     * 是否点击区域内
     * @param event MotionEvent
     * @param ax Float
     * @param ay Float
     * @param bx Float
     * @param by Float
     * @return Boolean
     */
    @JvmStatic
    fun isTapInArea(event: MotionEvent, left: Float, right: Float, top: Float, bottom: Float): Boolean =
        event.x in left..right && event.y >= top && event.y < bottom

    @JvmStatic
    fun motionEvent2String(action: Int): String {
        when (action) {
            MotionEvent.ACTION_DOWN -> return "ACTION_DOWN"
            MotionEvent.ACTION_UP -> return "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> return "ACTION_CANCEL"
            MotionEvent.ACTION_OUTSIDE -> return "ACTION_OUTSIDE"
            MotionEvent.ACTION_MOVE -> return "ACTION_MOVE"
            MotionEvent.ACTION_HOVER_MOVE -> return "ACTION_HOVER_MOVE"
            MotionEvent.ACTION_SCROLL -> return "ACTION_SCROLL"
            MotionEvent.ACTION_HOVER_ENTER -> return "ACTION_HOVER_ENTER"
            MotionEvent.ACTION_HOVER_EXIT -> return "ACTION_HOVER_EXIT"
            MotionEvent.ACTION_BUTTON_PRESS -> return "ACTION_BUTTON_PRESS"
            MotionEvent.ACTION_BUTTON_RELEASE -> return "ACTION_BUTTON_RELEASE"
        }
        val index = action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
        return when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_POINTER_DOWN -> "ACTION_POINTER_DOWN($index)"
            MotionEvent.ACTION_POINTER_UP -> "ACTION_POINTER_UP($index)"
            else -> action.toString()
        }
    }
}