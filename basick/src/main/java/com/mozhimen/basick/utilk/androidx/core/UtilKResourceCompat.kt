package com.mozhimen.basick.utilk.androidx.core

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

/**
 * @ClassName UtilKResourceCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/26 14:09
 * @Version 1.0
 */
object UtilKResourceCompat {
    @JvmStatic
    fun getDrawable(res: Resources, @DrawableRes id: Int, theme: Resources.Theme?): Drawable? =
        ResourcesCompat.getDrawable(res, id, theme)
}