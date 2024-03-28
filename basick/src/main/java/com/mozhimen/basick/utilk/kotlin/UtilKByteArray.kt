package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.InputStream

/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */
object UtilKByteArray : BaseUtilK() {

    @JvmStatic
    fun get(size: Int): ByteArray =
        ByteArray(size)

    @JvmStatic
    fun get(inputStream: InputStream): ByteArray =
        ByteArray(inputStream.available())

    //cs校验
    @JvmStatic
    fun getCS(bytes: ByteArray): Byte {
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