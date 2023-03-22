package com.mozhimen.basick.utilk.exts

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.view.UtilKTextView

/**
 * @ClassName ExtsKViewText
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:56
 * @Version 1.0
 */
/**
 * 设置字体的细或粗
 * @receiver TextView
 * @param style Int
 */
fun TextView.fontStyle(@IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
    UtilKTextView.setFontStyle(this, style)
}

/**
 * 设置字体
 * @receiver TextView
 * @param iconFont String
 */
fun TextView.font(iconFont: String = "icons/iconfont.ttf") {
    UtilKTextView.setFont(this, iconFont)
}