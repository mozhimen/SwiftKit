package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.java.security.UtilKMessageDigestMD5
import java.math.BigInteger
import java.security.NoSuchAlgorithmException

/**
 * @ClassName UtilKStrMd5
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 1:01
 * @Version 1.0
 */
fun String.str2strMd5(): String =
    UtilKStrMd5.str2strMd5(this)

fun String.str2strMd5_of16(): String =
    UtilKStrMd5.str2strMd5_of16(this)

fun String.str2strMd5_of32(): String =
    UtilKStrMd5.str2strMd5_of32(this)

//////////////////////////////////////////////////////////////////////////////////////////////////

object UtilKStrMd5 {
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5(str: String): String =
        str.str2bytes().bytes2strMd5Hex()

    //md5 hash 16位
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5_of16(str: String): String {
        val bytes: ByteArray = UtilKMessageDigestMD5.digest(str.str2bytes())
        var strMd5: String = BigInteger(1, bytes).toString(16)
        for (i in 0 until 32 - strMd5.length)
            strMd5 = "0$strMd5"
        return strMd5
    }

    //MD5 32位小写哈希
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5_of32(str: String): String {
        val bytes = UtilKMessageDigestMD5.digest(str.str2bytes())
        val stringBuilder = StringBuilder()
        for (byte in bytes.indices) {
            val value = bytes[byte].toInt() and 0xff
            if (value < 16)
                stringBuilder.append("0")
            stringBuilder.append(Integer.toHexString(value))
        }
        return stringBuilder.toString()
    }
}