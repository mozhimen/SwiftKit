package com.mozhimen.basick.utilk.android.text

import android.text.SpannableString
import android.text.Spanned
import androidx.annotation.ColorInt

/**
 * @ClassName UtilKSpannableString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/12
 * @Version 1.0
 */
object UtilKSpannableString {
    @JvmStatic
    fun getStrSpan(str: CharSequence): SpannableString =
        SpannableString(str)

    @JvmStatic
    fun getStrSpanOfForeColor(chars: CharSequence, @ColorInt intColor: Int, start: Int, end: Int): SpannableString =
        getStrSpan(chars).apply { setSpan(UtilKCharacterStyle.getForegroundColorSpan(intColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }
}