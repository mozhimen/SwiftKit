package com.mozhimen.basick.utilk.kotlin.sequences

import com.mozhimen.basick.elemk.commons.I_AListener

/**
 * @ClassName UtilKSequence
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKSequence {
    @JvmStatic
    fun <T> lazySequenceOf(vararg producers: I_AListener<T>): Sequence<T> =
        producers.asSequence().map { it() }
}