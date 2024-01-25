package com.mozhimen.basick.utilk.android.content

import android.content.Context
import androidx.annotation.ColorRes
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKContextWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/25
 * @Version 1.0
 */
fun Context.gainColor(@ColorRes resId: Int): Int =
    UtilKContextWrapper.gainColor(this, resId)

///////////////////////////////////////////////////////////////////////////////////

object UtilKContextWrapper {
    @JvmStatic
    fun gainColor(context: Context, @ColorRes resId: Int): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            UtilKContext.getColor(context, resId)
        } else {
            UtilKResource.getColor(context, resId)
        }
}