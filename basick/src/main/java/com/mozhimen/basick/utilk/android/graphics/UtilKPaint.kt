package com.mozhimen.basick.utilk.android.graphics

import android.text.TextPaint


/**
 * @ClassName UtilKPaint
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 16:32
 * @Version 1.0
 */
object UtilKPaint {
    /**
     * 多行高度
     * @param textPaint TextPaint
     * @param text String
     * @return Float
     */
    @JvmStatic
    fun getMultiLineTextHeight(textPaint: TextPaint, text: String): Float {
        return text.split("\n").toTypedArray().size * textPaint.textSize
    }
}