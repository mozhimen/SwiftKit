package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.android.os.cons.CBuild

/**
 * @ClassName UtilKThrowable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/23
 * @Version 1.0
 */
fun Throwable.getStrMessage(): String =
    UtilKThrowable.getStrMessage(this)

/////////////////////////////////////////////////////////////////////////

object UtilKThrowable {
    @JvmStatic
    fun getMessage(throwable: Throwable): String? =
        throwable.message

    @JvmStatic
    fun getStrMessage(throwable: Throwable): String {
        val message = getMessage(throwable)
        if (message.isNullOrEmpty())
            return CBuild.UNKNOWN
        return message
    }
}