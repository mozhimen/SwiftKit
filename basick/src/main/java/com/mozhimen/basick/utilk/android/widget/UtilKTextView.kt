package com.mozhimen.basick.utilk.android.widget

import android.graphics.Typeface
import android.text.InputFilter
import android.widget.TextView

/**
 * @ClassName UtilKTextView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKTextView {
    @JvmStatic
    fun getText(textView: TextView): CharSequence =
        textView.text

    @JvmStatic
    fun setTypeface(textView: TextView, tf: Typeface?) {
        textView.typeface = tf
    }

    @JvmStatic
    fun setFilters(textView: TextView, filters: Array<InputFilter>) {
        textView.filters = filters
    }
}