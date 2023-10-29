package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */
object UtilKByteArray : BaseUtilK() {

    /**
     * cs校验
     */
    @JvmStatic
    fun getOfCS(bytes: ByteArray): Byte {
        try {
            var num = 0
            for (i in bytes.indices)
                num = (num + bytes[i]) % 256
            return num.toByte()
        } catch (e: Exception) {
        }
        return 0
    }
}