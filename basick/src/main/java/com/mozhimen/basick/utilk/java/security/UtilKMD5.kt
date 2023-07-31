package com.mozhimen.basick.utilk.java.security

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.kotlin.UtilKByteArray
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
object UtilKMD5 : BaseUtilK() {

    /**
     * 获取md5
     * @return MessageDigest
     */
    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun get(): MessageDigest =
        MessageDigest.getInstance("MD5")

    @JvmStatic
    fun hash(str: String): String =
        bytes2Md5Str(str.toByteArray())

    /**
     * md5 hash 16位
     * @param str String
     * @return String
     */
    @JvmStatic
    fun hash16(str: String): String {
        val md5Bytes: ByteArray? = try {
            get().digest(str.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            e.message?.et(TAG)
            throw RuntimeException("hash16: no such md5 algo! ${e.message}")
        }
        var md5Str: String = BigInteger(1, md5Bytes).toString(16)
        for (i in 0 until 32 - md5Str.length) md5Str = "0$md5Str"
        return md5Str
    }

    /**
     * MD5 32位小写哈希
     * @param str String
     * @return String
     */
    @JvmStatic
    fun hash32LowerCase(str: String): String {
        val md5Bytes = try {
            get().digest(str.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            throw RuntimeException("hash32LowerCase: no such md5 algo! ${e.message}")
        }
        val md5StringBuilder = StringBuilder()
        for (i in md5Bytes.indices) {
            val value = md5Bytes[i].toInt() and 0xff
            if (value < 16) md5StringBuilder.append("0")
            md5StringBuilder.append(Integer.toHexString(value))
        }
        return md5StringBuilder.toString()
    }

    /**
     * MD5_32加密
     * @param str String
     * @return String
     */
    @JvmStatic
    fun hash32(str: String): String {
        return try {
            DigestUtils.md5Hex(str.toByteArray())
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            e.message?.et(TAG)
            throw RuntimeException("hash32: no such md5 algo!  ${e.message}")
        }
    }

    /**
     * MD5 hash
     * @param bytes ByteArray
     * @return String
     */
    @JvmStatic
    fun bytes2Md5Str(bytes: ByteArray): String =
        UtilKByteArray.bytes2hexStr(get().digest(bytes))

    /**
     * MD5 hash
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2Md5Str(inputStream: InputStream): String {
        val messageDigest = get()
        val buffer = ByteArray(1024 * 1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) messageDigest.update(buffer, 0, length)
        return UtilKByteArray.bytes2hexStr(messageDigest.digest())
    }
}