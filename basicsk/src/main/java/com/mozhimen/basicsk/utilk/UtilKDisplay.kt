package com.mozhimen.basicsk.utilk

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.TypedValue
import android.view.WindowManager

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
}
