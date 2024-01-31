package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKContextWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/25
 * @Version 1.0
 */

object UtilKContextWrapper {
    @JvmStatic
    fun gainColor(context: Context, @ColorRes intResColor: Int): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            UtilKContext.getColor(context, intResColor)
        } else {
            UtilKResources.getAppColor(context, intResColor)
        }

    @JvmStatic
    fun gainDrawable(context: Context, @DrawableRes intResDrawable: Int): Drawable? =
        if (UtilKBuildVersion.isAfterV_21_5_L()) {
            UtilKContext.getDrawable(context, intResDrawable)
        } else {
            UtilKResources.getAppDrawable(context, intResDrawable)
        }
}