package com.mozhimen.basick.utilk.javax.crypto

import javax.crypto.Cipher

/**
 * @ClassName UtilKCipher
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/7
 * @Version 1.0
 */
object UtilKCipher {
    @JvmStatic
    fun get(value: String): Cipher =
        Cipher.getInstance(value)
}