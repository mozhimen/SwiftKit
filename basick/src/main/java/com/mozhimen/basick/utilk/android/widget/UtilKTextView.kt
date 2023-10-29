package com.mozhimen.basick.utilk.android.widget

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.IntRange
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim

/**
 * @ClassName UtilKViewText
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:27
 * @Version 1.0
 */

val TextView.value: String get() = UtilKTextView.getValue(this)

fun TextView.getValueIfNotEmpty(invoke: IA_Listener<String>/*(value: String) -> Unit*/) {
    UtilKTextView.getValueIfNotEmpty(this, invoke)
}

fun TextView.applyTextStyle(@IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
    UtilKTextView.applyTextStyle(this, style)
}

fun TextView.applyTextStyleBold() {
    UtilKTextView.applyTextStyleBold(this)
}

fun TextView.applyTextStyleNormal() {
    UtilKTextView.applyTextStyleNormal(this)
}

fun TextView.applyIconFont(iconFont: String = "icons/iconfont.ttf") {
    UtilKTextView.applyIconFont(this, iconFont)
}

fun TextView.applyValueIfNotEmpty(str: String?) {
    UtilKTextView.applyValueIfNotEmpty(this, str)
}

fun TextView.applyTextColorStateList( colors: ColorStateList) {
    UtilKTextView.applyTextColorStateList(this,colors)
}

object UtilKTextView {
    @JvmStatic
    fun getValue(textView: TextView): String =
        textView.text.obj2stringTrim()

    @JvmStatic
    fun getValueIfNotEmpty(textView: TextView, invoke: IA_Listener<String>/*(value: String) -> Unit*/) {
        val value = getValue(textView)
        if (value.isNotEmpty()) invoke.invoke(value)
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 设置字体的细或粗
     */
    @JvmStatic
    fun applyTextStyle(textView: TextView, @IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
        textView.typeface = Typeface.defaultFromStyle(style)
    }

    @JvmStatic
    fun applyTextStyleBold(textView: TextView) {
        applyTextStyle(textView, Typeface.BOLD)
    }

    @JvmStatic
    fun applyTextStyleNormal(textView: TextView) {
        applyTextStyle(textView, Typeface.NORMAL)
    }

    /**
     * 设置字体
     */
    @JvmStatic
    fun applyIconFont(textView: TextView, fontPathWithName: String = "fonts/iconfont.ttf") {
        textView.typeface = Typeface.createFromAsset(UtilKContext.getAssets(textView.context), fontPathWithName)
    }

    @JvmStatic
    fun applyValueIfNotEmpty(textView: TextView, str: String?): Boolean {
        return if (!str.isNullOrEmpty()) {
            textView.text = str
            true
        } else false
    }

    @JvmStatic
    fun applyTextColorStateList(textView: TextView, colors: ColorStateList) {
        textView.setTextColor(colors)
    }
}