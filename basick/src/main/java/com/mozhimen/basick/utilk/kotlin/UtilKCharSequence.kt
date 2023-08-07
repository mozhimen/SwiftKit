package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKCharSequence
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:39
 * @Version 1.0
 */
fun CharSequence.isEquals(charSequence: CharSequence): Boolean =
        UtilKCharSequence.isEquals(this, charSequence)

object UtilKCharSequence {
    @JvmStatic
    fun isEquals(charSequence1: CharSequence, charSequence2: CharSequence): Boolean {
        if (charSequence1 === charSequence2) return true
        var length: Int
        return if (charSequence1.length.also { length = it } == charSequence2.length) {
            if (charSequence1 is String && charSequence2 is String) {
                charSequence1 == charSequence2
            } else {
                for (i in 0 until length) {
                    if (charSequence1[i] != charSequence2[i]) return false
                }
                true
            }
        } else false
    }

    /**
     * 是否为空
     */
    @JvmStatic
    fun isNullOrEmpty(str: CharSequence?): Boolean =
        str.isNullOrEmpty()
}