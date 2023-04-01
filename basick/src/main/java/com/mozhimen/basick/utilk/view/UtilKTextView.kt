package com.mozhimen.basick.utilk.view

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKViewText
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:27
 * @Version 1.0
 */
object UtilKTextView {
    /**
     * 设置字体的细或粗
     * @param textView TextView
     * @param style Int
     */
    @JvmStatic
    fun setTextStyle(
        textView: TextView,
        @IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL
    ) {
        textView.typeface = Typeface.defaultFromStyle(style)
    }

    /**
     * 设置字体
     * @param textView TextView
     * @param fontPathWithName String
     */
    @JvmStatic
    fun setIconFont(
        textView: TextView,
        fontPathWithName: String = "fonts/iconfont.ttf"
    ) {
        textView.typeface = Typeface.createFromAsset(UtilKContext.getAssets(textView.context), fontPathWithName)
    }
}