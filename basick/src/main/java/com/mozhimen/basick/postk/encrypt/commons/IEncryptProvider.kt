package com.mozhimen.basick.postk.encrypt.commons

/**
 * @ClassName IEncrypt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 15:56
 * @Version 1.0
 */
interface IEncryptProvider {
    fun encryptWithBase64(str: String): String
    fun decryptWithBase64(str: String): String
    fun encrypt(byteArray: ByteArray): ByteArray
    fun decrypt(byteArray: ByteArray): ByteArray
}