package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.annotation.StyleableRes
import com.mozhimen.basick.elemk.cons.CPackage

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/18 20:57
 * @Version 1.0
 */
object UtilKTheme {
    @JvmStatic
    fun get(context: Context): Theme =
        UtilKContext.getTheme(context)

    ////////////////////////////////////////////////////////////////////

    /**
     * 是否全屏
     */
    @JvmStatic
    fun isFullScreen(context: Context): Boolean =
        obtainStyledAttributes(context, intArrayOf(CPackage.ANDROID_R_ATTR_WINDOWFULLSCREEN)).use { it.getBoolean(0, false) }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun resolveAttribute(context: Context, resId: Int, outValue: TypedValue, resolveRefs: Boolean) =
        get(context).resolveAttribute(resId, outValue, resolveRefs)

    @JvmStatic
    fun obtainStyledAttributes(context: Context, @StyleableRes attrs: IntArray): TypedArray =
        get(context).obtainStyledAttributes(attrs)

}