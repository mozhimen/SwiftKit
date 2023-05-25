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
fun TextView.setTextStyle(@IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
    UtilKTextView.setTextStyle(this, style)
}

/**
 * 设置字体
 * @receiver TextView
 * @param iconFont String
 */
fun TextView.setIconFont(iconFont: String = "icons/iconfont.ttf") {
    UtilKTextView.setIconFont(this, iconFont)
}

/**
 * 获得文本
 * @receiver TextView
 * @return String
 */
fun TextView.getValue(): String =
    UtilKTextView.getValue(this)

/**
 * 获得文本如果不为空
 * @receiver TextView
 * @param invoke Function1<[@kotlin.ParameterName] String, Unit>
 */
fun TextView.getValueIfNotEmpty(invoke: (value: String) -> Unit) {
    UtilKTextView.getValueIfNotEmpty(this, invoke)
}