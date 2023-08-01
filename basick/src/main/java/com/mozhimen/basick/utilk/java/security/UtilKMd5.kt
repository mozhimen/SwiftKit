package com.mozhimen.basick.utilk.java.security

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.asHexStr
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
fun InputStream.asMd5Str2(): String =
    UtilKMd5.inputStream2md5Str2(this)

fun ByteArray.asMd5HexStr(): String =
    UtilKMd5.bytes2md5HexStr(this)

object UtilKMd5 : BaseUtilK() {

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun get(): MessageDigest =
        UtilKMessageDigest.getMD5()

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun digest(bytes: ByteArray): ByteArray =
        get().digest(bytes)

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun bytes2md5HexStr(bytes: ByteArray): String =
        digest(bytes).asHexStr()

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun hash(str: String): String =
        str.toByteArray().asMd5HexStr()

    /**
     * md5 hash 16位
     * @param str String
     * @return String
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun hash16(str: String): String {
        val bytes: ByteArray = digest(str.toByteArray())
        var md5Str: String = BigInteger(1, bytes).toString(16)
        for (i in 0 until 32 - md5Str.length) md5Str = "0$md5Str"
        return md5Str
    }

    /**
     * MD5 32位小写哈希
     * @param str String
     * @return String
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun hash32_lowerCase(str: String): String {
        val bytes = digest(str.toByteArray())
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
     * @param str String
     * @return String
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun hash32(str: String): String =
        DigestUtils.md5Hex(str.toByteArray())

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2md5Str(inputStream: InputStream): String {
        val messageDigest = get()
        var length: Int
        val bytes = ByteArray(1024 * 1024)
        while (inputStream.read(bytes).also { length = it } > 0) messageDigest.update(bytes, 0, length)
        return messageDigest.digest().asHexStr()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2md5Str2(inputStream: InputStream): String {
        val messageDigest: MessageDigest = get()
        var length: Int
        val bytes = ByteArray(1024)
        while (inputStream.read(bytes, 0, 1024).also { length = it } != -1) messageDigest.update(bytes, 0, length)
        return BigInteger(1, messageDigest.digest()).toString(16)
    }
}