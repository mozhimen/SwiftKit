package com.mozhimen.basick.utilk.android.text

import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

/**
 * @ClassName UtilKStaticLayout
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/4
 * @Version 1.0
 */
object UtilKStaticLayout {
    @JvmStatic
    fun get(content: String, textPaint: TextPaint, width: Int, align: Layout.Alignment, spacingmult: Float, spacingadd: Float, includepad: Boolean): StaticLayout =
        StaticLayout(content, textPaint, width, align, spacingmult, spacingadd, includepad)

    @JvmStatic
    fun getLineCount(content: String, textPaint: TextPaint, width: Int, align: Layout.Alignment, spacingmult: Float, spacingadd: Float, includepad: Boolean) =
        get(content, textPaint, width, align, spacingmult, spacingadd, includepad).lineCount
}