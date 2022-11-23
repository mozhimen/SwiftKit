package com.mozhimen.basick.utilk

import android.graphics.Rect
import android.view.Gravity

/**
 * @ClassName UtilKGravity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 0:08
 * @Version 1.0
 */
object UtilKGravity {
    @JvmStatic
    fun computeGravity(sourceRect: Rect, destRect: Rect): Int {
        var gravity = Gravity.NO_GRAVITY
        val xDelta = sourceRect.centerX() - destRect.centerX()
        val yDelta = sourceRect.centerY() - destRect.centerY()
        if (xDelta == 0) {
            gravity = if (yDelta == 0) Gravity.CENTER else Gravity.CENTER_HORIZONTAL or if (yDelta > 0) Gravity.BOTTOM else Gravity.TOP
        }
        if (yDelta == 0) {
            gravity = if (xDelta == 0) Gravity.CENTER else Gravity.CENTER_VERTICAL or if (xDelta > 0) Gravity.RIGHT else Gravity.LEFT
        }
        if (gravity == Gravity.NO_GRAVITY) {
            gravity = if (xDelta > 0) Gravity.RIGHT else Gravity.LEFT
            gravity = gravity or if (yDelta > 0) Gravity.BOTTOM else Gravity.TOP
        }
        return gravity
    }
}