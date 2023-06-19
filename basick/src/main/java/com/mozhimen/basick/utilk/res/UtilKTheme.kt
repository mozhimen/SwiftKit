package com.mozhimen.basick.utilk.res

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/18 20:57
 * @Version 1.0
 */
object UtilKTheme {
    /**
     * 获取主题
     * @return Theme
     */
    @JvmStatic
    fun get(context: Context): Theme =
        UtilKRes.getTheme(context)

    /**
     * 获取主题
     * @return Theme
     */
    @JvmStatic
    fun get(): Theme =
        UtilKRes.getTheme()

    /**
     * resolveAttribute
     * @param context Context
     * @param resId Int
     * @param outValue TypedValue
     * @param resolveRefs Boolean
     * @return Boolean
     */
    @JvmStatic
    fun resolveAttribute(context: Context, resId: Int, outValue: TypedValue, resolveRefs: Boolean) =
        get(context).resolveAttribute(resId, outValue, resolveRefs)
}