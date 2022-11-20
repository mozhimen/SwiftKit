package com.mozhimen.basick.utilk.encrypt

import android.util.Log
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils
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
object UtilKEncryptMD5 {
    private const val TAG = "UtilKEncryptMD5>>>>>"

    /**
     * 加密
     * @param data String
     * @return String
     */
    @JvmStatic
    fun encrypt16(data: String): String {
        val secretBytes: ByteArray? = try {
            MessageDigest.getInstance("MD5").digest(data.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            throw RuntimeException("encrypt: no such md5 algo! ${e.message}")
        }
        var md5code: String = BigInteger(1, secretBytes).toString(16)
        for (i in 0 until 32 - md5code.length) {
            md5code = "0$md5code"
        }
        return md5code
    }

    /**
     * MD5 32位小写加密
     * @param data String
     * @return String
     */
    @JvmStatic
    fun encryptLower32(data: String): String {
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            val md5Bytes = md5.digest(data.toByteArray())
            val hexValue = StringBuilder()
            for (i in md5Bytes.indices) {
                val value = md5Bytes[i].toInt() and 0xff
                if (value < 16) {
                    hexValue.append("0")
                }
                hexValue.append(Integer.toHexString(value))
            }
            return hexValue.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "encrypt32: ${e.message}")
        }
        return data
    }

    /**
     * MD5_32加密
     * @param data String
     * @return String
     */
    @JvmStatic
    fun encrypt32(data: String): String {
        return try {
            DigestUtils.md5Hex(data.toByteArray())
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            throw RuntimeException("encrypt322: ${e.message}")
        }
    }
}