package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.graphics.Rect
import com.mozhimen.basick.elemk.android.view.cons.CGravity

/**
 * @ClassName UtilKGravity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 0:08
 * @Version 1.0
 */
object UtilKGravity {
    @SuppressLint("RtlHardcoded")
    @JvmStatic
    fun computeGravity(sourceRect: Rect, destRect: Rect): Int {
        var gravity = CGravity.NO_GRAVITY
        val xDelta = sourceRect.centerX() - destRect.centerX()
        val yDelta = sourceRect.centerY() - destRect.centerY()
        if (xDelta == 0)
            gravity = if (yDelta == 0) CGravity.CENTER else CGravity.CENTER_HORIZONTAL or if (yDelta > 0) CGravity.BOTTOM else CGravity.TOP
        if (yDelta == 0)
            gravity = if (xDelta == 0) CGravity.CENTER else CGravity.CENTER_VERTICAL or if (xDelta > 0) CGravity.RIGHT else CGravity.LEFT
        if (gravity == CGravity.NO_GRAVITY) {
            gravity = if (xDelta > 0) CGravity.RIGHT else CGravity.LEFT
            gravity = gravity or if (yDelta > 0) CGravity.BOTTOM else CGravity.TOP
        }
        return gravity
    }
}