package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @ClassName UtilMKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
object UtilMKRes {
    private val context: Context
        get() = UtilMKGlobal.instance.getApp() as Context

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, resId)
    }

    fun getDrawable(@DrawableRes drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }
}