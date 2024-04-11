package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_AListener
import com.mozhimen.basick.elemk.commons.I_Listener

/**
 * @ClassName UtilKCharSequence
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:39
 * @Version 1.0
 */
fun CharSequence.isEquals(chars: CharSequence): Boolean =
    UtilKCharSequence.isEquals(this, chars)

fun <C : CharSequence> CharSequence.ifNotEmpty(block: IA_Listener<CharSequence>) {
    UtilKCharSequence.ifNotEmpty(this, block)
}

fun <C : CharSequence> CharSequence.ifNotEmptyOr(onNotEmpty: IA_Listener<CharSequence>, onEmpty: I_Listener) {
    UtilKCharSequence.ifNotEmptyOr(this, onNotEmpty, onEmpty)
}

fun <C : CharSequence> C?.ifNullOrEmpty(defaultValue: I_AListener<C>): C =
    UtilKCharSequence.ifNullOrEmpty(this, defaultValue)

//////////////////////////////////////////////////////////////////////////////

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

    //////////////////////////////////////////////////////////////////////////////

    //是否为空
    @JvmStatic
    fun isNullOrEmpty(chars: CharSequence?): Boolean =
        chars.isNullOrEmpty()

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun <C : CharSequence> ifNotEmpty(chars: C, block: IA_Listener<C>) {
        if (chars.isNotEmpty())
            block.invoke(chars)
    }

    @JvmStatic
    fun <C : CharSequence> ifNotEmptyOr(chars: C, onNotEmpty: IA_Listener<C>, onEmpty: I_Listener) {
        if (chars.isNotEmpty())
            onNotEmpty.invoke(chars)
        else
            onEmpty.invoke()
    }

    @JvmStatic
    fun <C : CharSequence> ifNullOrEmpty(chars: C?, onNullOrEmpty: I_AListener<C>): C =
        if (chars.isNullOrEmpty())
            onNullOrEmpty()
        else
            chars
}

