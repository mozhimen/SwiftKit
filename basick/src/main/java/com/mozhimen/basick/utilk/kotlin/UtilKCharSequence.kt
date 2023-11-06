package com.mozhimen.basick.utilk.kotlin

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

/**
 * @ClassName UtilKCharSequence
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:39
 * @Version 1.0
 */
fun CharSequence.isEquals(chars: CharSequence): Boolean =
        UtilKCharSequence.isEquals(this, chars)

fun CharSequence.applyTextColor(@ColorInt intColor: Int,start: Int,end: Int):CharSequence =
    UtilKCharSequence.applyTextColor(this,intColor, start, end)

object UtilKCharSequence {
    @JvmStatic
    fun isEquals(chars1: CharSequence, char2: CharSequence): Boolean {
        if (chars1 === char2) return true
        var length: Int
        return if (chars1.length.also { length = it } == char2.length) {
            if (chars1 is String && char2 is String) {
                chars1 == char2
            } else {
                for (i in 0 until length) {
                    if (chars1[i] != char2[i]) return false
                }
                true
            }
        } else false
    }

    /**
     * 是否为空
     */
    @JvmStatic
    fun isNullOrEmpty(chars: CharSequence?): Boolean =
        chars.isNullOrEmpty()

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyTextColor(chars: CharSequence,@ColorInt intColor:Int,start:Int,end:Int):CharSequence{
        return SpannableString(chars).apply { setSpan(ForegroundColorSpan(intColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }
    }
}