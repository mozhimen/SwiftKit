package com.mozhimen.basick.utilk.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.res.UtilKRes

/**
 * @ClassName UtilKContextCompat
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:39
 * @Version 1.0
 */
object UtilKContextCompat {
    @JvmStatic
    fun getColor(context: Context, @ColorRes resId: Int): Int =
        ContextCompat.getColor(context, resId)

    @JvmStatic
    fun getColorStateList(context: Context, @ColorRes resId: Int): ColorStateList? =
        ContextCompat.getColorStateList(context, resId)

    @JvmStatic
    fun getDrawable(context: Context, @DrawableRes drawableId: Int): Drawable? =
        ContextCompat.getDrawable(context, drawableId)

    @JvmStatic
    fun checkSelfPermission(context: Context, permission: String): Int =
        ContextCompat.checkSelfPermission(context, permission)
}