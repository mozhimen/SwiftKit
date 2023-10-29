package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

/**
 * @ClassName UtilKColorDrawable
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 20:28
 * @Version 1.0
 */
object UtilKColorDrawable {
    /**
     * 是否正常的drawable
     */
    @JvmStatic
    fun isColorDrawableNormal(drawable: Drawable): Boolean {
        return drawable !is ColorDrawable || drawable.color != Color.TRANSPARENT
    }
}