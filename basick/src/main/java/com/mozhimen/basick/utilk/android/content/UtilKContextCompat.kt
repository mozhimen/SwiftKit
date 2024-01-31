package com.mozhimen.basick.utilk.android.content

import android.content.Context
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
    fun getColor(context: Context, @ColorRes intResColor: Int): Int =
        ContextCompat.getColor(context, intResColor)

    @JvmStatic
    fun getColorStateList(context: Context, @ColorRes intResColor: Int): ColorStateList? =
        ContextCompat.getColorStateList(context, intResColor)

    @JvmStatic
    fun getDrawable(context: Context, @DrawableRes intResDrawable: Int): Drawable? =
        ContextCompat.getDrawable(context, intResDrawable)

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isSelfPermissionGranted(context: Context, permission: String): Boolean =
        checkSelfPermission(context, permission) == CPackageManager.PERMISSION_GRANTED

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun checkSelfPermission(context: Context, permission: String): Int =
        ContextCompat.checkSelfPermission(context, permission)
}