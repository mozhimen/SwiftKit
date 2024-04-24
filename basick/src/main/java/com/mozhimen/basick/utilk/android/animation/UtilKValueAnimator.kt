package com.mozhimen.basick.utilk.android.animation

import android.animation.ValueAnimator

/**
 * @ClassName UtilKValueAnimator
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
object UtilKValueAnimator {
    @JvmStatic
    fun removeAllUpdateListeners(valueAnimator: ValueAnimator) {
        valueAnimator.removeAllUpdateListeners()
    }
}