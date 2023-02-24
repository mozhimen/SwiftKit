package com.mozhimen.basick.utilk.graphics

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

/**
 * @ClassName UtilKDrawable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 22:42
 * @Version 1.0
 */
object UtilKDrawable {
    /**
     * 是否正常的drawable
     * @param drawable Drawable
     * @return Boolean
     */
    @JvmStatic
    fun isColorDrawableValid(drawable: Drawable): Boolean {
        return drawable !is ColorDrawable || drawable.color != Color.TRANSPARENT
    }
}