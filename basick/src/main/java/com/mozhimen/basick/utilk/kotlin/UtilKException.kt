package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.android.os.cons.CBuild

/**
 * @ClassName UtilKException
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:40
 * @Version 1.0
 */
fun Exception.getStrMessageForException(): String =
    UtilKException.getStrMessageForException(this)

object UtilKException {
    @JvmStatic
    fun getStrMessageForException(exception: Exception): String {
        val message = exception.message
        if (message.isNullOrEmpty()) {
            return CBuild.UNKNOWN
        }
        return message
    }
}