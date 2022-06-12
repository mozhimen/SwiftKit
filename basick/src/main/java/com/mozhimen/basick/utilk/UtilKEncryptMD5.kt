package com.mozhimen.basick.utilk

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
    fun encrypt(data: String): String {
        val secretBytes: ByteArray? = try {
            MessageDigest.getInstance("md5").digest(data.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("no such md5 algo!")
        }
        var md5code: String = BigInteger(1, secretBytes).toString(16)
        for (i in 0 until 32 - md5code.length) {
            md5code = "0$md5code"
        }
        return md5code
    }
}