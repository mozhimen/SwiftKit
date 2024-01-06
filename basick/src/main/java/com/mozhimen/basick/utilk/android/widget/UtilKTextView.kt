package com.mozhimen.basick.utilk.android.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.text.UtilKInputFilter
import com.mozhimen.basick.utilk.kotlin.ifOrElse
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim
import com.mozhimen.basick.utilk.kotlin.whether

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

//////////////////////////////////////////////////////////////////////////////////////

fun TextView.applySingleLine() {
    UtilKTextView.applySingleLine(this)
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

fun TextView.applyTextColorStateList(colors: ColorStateList) {
    UtilKTextView.applyTextColorStateList(this, colors)
}

fun TextView.applyLengthFilter(max: Int) {
    UtilKTextView.applyLengthFilter(this, max)
}

object UtilKTextView {
    @JvmStatic
    fun get(
        context: Context,
        singleLine: Boolean = false,
        ems: Int = 10,
        truncateAt: TextUtils.TruncateAt? = TextUtils.TruncateAt.END,
        gravity: Int? = Gravity.CENTER,
        colorResId: Int? = android.R.color.black,
        textSize: Float? = 16f
    ): TextView {
        val textView = TextView(context)
        singleLine.whether { textView.setSingleLine() }//设置显示为1行
        if (ems > 0) textView.setEms(ems)//设置最多显示多少个字
        truncateAt?.let { textView.ellipsize = it }//设置省略号在尾部
        gravity?.let { textView.gravity = it }
        colorResId?.let { textView.setTextColor(ContextCompat.getColor(context, it)) }
        textSize?.let { textView.textSize = it }
        return textView
    }

    @JvmStatic
    fun getValue(textView: TextView): String =
        textView.text.obj2stringTrim()

    @JvmStatic
    fun getValueIfNotEmpty(textView: TextView, invoke: IA_Listener<String>/*(value: String) -> Unit*/) {
        val value = getValue(textView)
        if (value.isNotEmpty()) invoke.invoke(value)
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applySingleLine(textView: TextView) {
        textView.setSingleLine()
        textView.maxLines = 1
    }

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

    @JvmStatic
    fun applyLengthFilter(textView: TextView, max: Int) {
        if (max > 0) {
            textView.filters = arrayOf(UtilKInputFilter.getLengthFilter(max))
        }
    }
}