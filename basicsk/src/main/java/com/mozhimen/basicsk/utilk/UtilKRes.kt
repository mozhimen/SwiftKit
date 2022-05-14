package com.mozhimen.basicsk.utilk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
object UtilKRes {
    private val _context: Context
        get() = UtilKGlobal.instance.getApp() as Context

    /**
     * 获取字符串
     * @param resId Int
     * @return String
     */
    fun getString(@StringRes resId: Int): String =
        _context.getString(resId)

    /**
     * 获取字符串
     * @param resId Int
     * @param formatArgs Array<out Any?>
     * @return String
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        _context.getString(resId, *formatArgs)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    fun getColor(@ColorRes resId: Int): Int =
        ContextCompat.getColor(_context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    fun getColorStateList(@ColorRes resId: Int): ColorStateList? =
        ContextCompat.getColorStateList(_context, resId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    fun getDrawable(@DrawableRes drawableId: Int): Drawable? =
        ContextCompat.getDrawable(_context, drawableId)
}