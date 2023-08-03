package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager

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

    @JvmStatic
    fun isSelfPermissionGranted(context: Context, permission: String): Boolean =
            checkSelfPermission(context, permission) == CPackageManager.PERMISSION_GRANTED
}