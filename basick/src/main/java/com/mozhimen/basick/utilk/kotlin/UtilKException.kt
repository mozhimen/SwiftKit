package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.android.os.cons.CBuild

/**
 * @ClassName UtilKException
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:40
 * @Version 1.0
 */
fun Exception.getStrMessage(): String =
    UtilKException.getStrMessage(this)

/////////////////////////////////////////////////////////////////////////

object UtilKException {
    @JvmStatic
    fun getMessage(exception: Exception): String? =
        exception.message

    @JvmStatic
    fun getStrMessage(exception: Exception): String {
        val message = getMessage(exception)
        if (message.isNullOrEmpty())
            return CBuild.UNKNOWN
        return message
    }
}