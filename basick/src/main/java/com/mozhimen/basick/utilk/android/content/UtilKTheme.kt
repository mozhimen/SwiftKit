package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue

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

    @JvmStatic
    fun resolveAttribute(context: Context, resId: Int, outValue: TypedValue, resolveRefs: Boolean) =
        get(context).resolveAttribute(resId, outValue, resolveRefs)

    /**
     * 是否全屏
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreen(context: Context): Boolean {
        val typedArray = get(context).obtainStyledAttributes(intArrayOf(android.R.attr.windowFullscreen))
        val windowFullscreen = typedArray.getBoolean(0, false)
        typedArray.recycle()
        return windowFullscreen
    }
}