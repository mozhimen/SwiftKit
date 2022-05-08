package com.mozhimen.basicsk.utilk

import android.view.MotionEvent
import kotlin.math.sqrt

/**
 * @ClassName UtilKGesture
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:05
 * @Version 1.0
 */
class UtilKGesture {

    /**
     * 计算手指间间距
     * @param event MotionEvent
     * @return Float
     */
    fun fingerDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }
}