package com.mozhimen.basick.utilk.view

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.IntRange

/**
 * @ClassName UtilKViewText
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:27
 * @Version 1.0
 */
object UtilKViewText {
    /**
     * 设置字体的细或粗
     * @param textView TextView
     * @param style Int
     */
    @JvmStatic
    fun fontStyle(
        textView: TextView,
        @IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL
    ) {
        textView.typeface = Typeface.defaultFromStyle(style)
    }

    /**
     * 设置字体
     * @param textView TextView
     * @param fontPath String
     */
    @JvmStatic
    fun font(
        textView: TextView,
        fontPath: String = "fonts/iconfont.ttf"
    ) {
        val typeface = Typeface.createFromAsset(textView.context.assets, fontPath)
        textView.typeface = typeface
    }
}