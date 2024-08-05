package com.mozhimen.basick.utilk.android.text

import android.graphics.Rect
import android.text.TextPaint

/**
 * @ClassName UtilKTextPaint
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/8/4 16:22
 * @Version 1.0
 */
object UtilKTextPaint {
    @JvmStatic
    fun getTextRect(textPaint: TextPaint): Rect {
        val bounds = Rect()
        textPaint.getTextBounds(mText, 0, mText.length(), bounds)
        val height = bounds.height()
    }
}