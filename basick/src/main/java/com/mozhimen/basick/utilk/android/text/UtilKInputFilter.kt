package com.mozhimen.basick.utilk.android.text

import android.text.InputFilter.LengthFilter

/**
 * @ClassName UtilKInputFilter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/11/4 19:49
 * @Version 1.0
 */
object UtilKInputFilter {
    @JvmStatic
    fun getLengthFilter(max:Int):LengthFilter =
        LengthFilter(max)
}