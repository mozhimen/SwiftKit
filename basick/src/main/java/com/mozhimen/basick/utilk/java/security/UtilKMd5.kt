package com.mozhimen.basick.utilk.java.security

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.bytes2strHex
import com.mozhimen.basick.utilk.kotlin.str2bytes
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @ClassName UtilKEncryptMd5
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/11 17:13
 * @Version 1.0
 */
fun ByteArray.bytes2strMd5Hex(): String =
    UtilKMd5.bytes2strMd5Hex(this)

fun String.str2strMd5(): String =
    UtilKMd5.str2strMd5(this)

fun String.str2strMd5_16(): String =
    UtilKMd5.str2strMd5_16(this)

fun String.str2strMd5_32_lowerCase(): String =
    UtilKMd5.str2strMd5_32_lowerCase(this)

fun String.str2strMd5_32(): String =
    UtilKMd5.str2strMd5_32(this)

fun InputStream.inputStream2strMd5(): String =
    UtilKMd5.inputStream2strMd5(this)

fun InputStream.inputStream2strMd52(): String =
    UtilKMd5.inputStream2strMd52(this)

object UtilKMd5 : BaseUtilK() {

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun get(): MessageDigest =
        UtilKMessageDigest.getMD5()

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun digest(bytes: ByteArray): ByteArray =
        get().digest(bytes)

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun bytes2strMd5Hex(bytes: ByteArray): String =
        digest(bytes).bytes2strHex()

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
        val bytes: ByteArray = digest(str.str2bytes())
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
        val bytes = digest(str.str2bytes())
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
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun str2strMd5_32(str: String): String =
        DigestUtils.md5Hex(str.str2bytes())

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd5(inputStream: InputStream): String {
        val messageDigest = get()
        var length: Int
        val bytes = ByteArray(1024 * 1024)
        while (inputStream.read(bytes).also { length = it } != -1)
            messageDigest.update(bytes, 0, length)
        return messageDigest.digest().bytes2strHex()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd52(inputStream: InputStream): String {
        val messageDigest: MessageDigest = get()
        var length: Int
        val bytes = ByteArray(1024)
        while (inputStream.read(bytes, 0, 1024).also { length = it } != -1)
            messageDigest.update(bytes, 0, length)
        return BigInteger(1, messageDigest.digest()).toString(16)
    }
}