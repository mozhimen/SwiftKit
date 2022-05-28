package com.mozhimen.basick.utilk

import android.util.TypedValue

/**
 * @ClassName KDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilKDisplay {
    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * dp转px
     * @param dp Float
     * @return Int
     */
    fun dp2px(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, _context.resources?.displayMetrics).toInt()

    /**
     * sp转px
     * @param dp Float
     * @return Int
     */
    fun sp2px(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, _context.resources?.displayMetrics).toInt()

    /**
     * 获取屏幕密度
     * @return Int
     */
    fun getDensityDpi(): Int = _context.resources.configuration.densityDpi
}
