package com.mozhimen.basick.utilk.androidx.core

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

/**
 * @ClassName UtilKResourceCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/26 14:09
 * @Version 1.0
 */
object UtilKResourcesCompat {
    @JvmStatic
    fun getColor(res: Resources, @ColorRes intResColor: Int, theme: Resources.Theme?): Int =
        ResourcesCompat.getColor(res, intResColor, theme)

    @JvmStatic
    fun getColorStateList(res: Resources, @ColorRes intResColor: Int, theme: Resources.Theme?): ColorStateList? =
        ResourcesCompat.getColorStateList(res, intResColor, theme)

    @JvmStatic
    fun getDrawable(res: Resources, @DrawableRes intResDrawable: Int, theme: Resources.Theme?): Drawable? =
        ResourcesCompat.getDrawable(res, intResDrawable, theme)

    @JvmStatic
    fun getDrawableForDensity(res: Resources, @DrawableRes intResDrawable: Int, density: Int, theme: Resources.Theme?): Drawable? =
        ResourcesCompat.getDrawableForDensity(res, intResDrawable, density, theme)

    @JvmStatic
    fun getFloat(res: Resources, @DimenRes intResDimen: Int): Float =
        ResourcesCompat.getFloat(res, intResDimen)

    @JvmStatic
    fun getFont(context: Context, @FontRes intResFont: Int): Typeface? =
        ResourcesCompat.getFont(context, intResFont)
}