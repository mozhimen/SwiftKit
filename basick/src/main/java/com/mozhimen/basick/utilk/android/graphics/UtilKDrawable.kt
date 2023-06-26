package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

/**
 * @ClassName UtilKDrawable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 22:42
 * @Version 1.0
 */
fun Drawable.applyColorFilter(colorResId: Int) {
    UtilKDrawable.applyColorFilter(this, colorResId)
}

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

    @JvmStatic
    fun applyColorFilter(drawable: Drawable, colorResId: Int) {
        drawable.mutate().setColorFilter(colorResId, PorterDuff.Mode.SRC_IN)
    }
}