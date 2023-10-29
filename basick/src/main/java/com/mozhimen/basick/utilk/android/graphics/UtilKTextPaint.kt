package com.mozhimen.basick.utilk.android.graphics

import android.text.TextPaint


/**
 * @ClassName UtilKPaint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 16:32
 * @Version 1.0
 */
object UtilKTextPaint {
    /**
     * 多行高度
     */
    @JvmStatic
    fun getMultiLineStrHeight(textPaint: TextPaint, str: String): Float =
        str.split("\n").toTypedArray().size * textPaint.textSize
}