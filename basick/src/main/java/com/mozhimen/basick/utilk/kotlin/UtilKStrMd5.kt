package com.mozhimen.basick.utilk.kotlin

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.mozhimen.basick.utilk.java.security.UtilKMd5
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

fun String.str2strMd5_16(): String =
    UtilKStrMd5.str2strMd5_16(this)

fun String.str2strMd5_32_lowerCase(): String =
    UtilKStrMd5.str2strMd5_32_lowerCase(this)

//fun String.str2strMd5_32(): String =
//    UtilKStrMd5.str2strMd5_32(this)

object UtilKStrMd5 {
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5(str: String): String =
        str.str2bytes().bytes2strMd5Hex()

    /**
     * md5 hash 16位
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5_16(str: String): String {
        val bytes: ByteArray = UtilKMd5.digest(str.str2bytes())
        var md5Str: String = BigInteger(1, bytes).toString(16)
        for (i in 0 until 32 - md5Str.length) md5Str = "0$md5Str"
        return md5Str
    }

    /**
     * MD5 32位小写哈希
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun str2strMd5_32_lowerCase(str: String): String {
        val bytes = UtilKMd5.digest(str.str2bytes())
        val stringBuilder = StringBuilder()
        for (byte in bytes.indices) {
            val value = bytes[byte].toInt() and 0xff
            if (value < 16) stringBuilder.append("0")
            stringBuilder.append(Integer.toHexString(value))
        }
        return stringBuilder.toString()
    }

    /**
     * MD5_32加密
     */
//    @JvmStatic
//    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
//    fun str2strMd5_32(str: String): String =
//        DigestUtils.md5Hex(str.str2bytes())
}