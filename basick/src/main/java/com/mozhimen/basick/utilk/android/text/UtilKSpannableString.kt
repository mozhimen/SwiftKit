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
    fun get(str: CharSequence): SpannableString =
        SpannableString(str)

    @JvmStatic
    fun get_ofForeColor(chars: CharSequence, @ColorInt intColor: Int, start: Int, end: Int): SpannableString =
        get(chars).apply { setSpan(UtilKCharacterStyle.getForegroundColorSpan(intColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }
}