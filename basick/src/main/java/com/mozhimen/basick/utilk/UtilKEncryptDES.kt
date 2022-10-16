package com.mozhimen.basick.utilk

import android.util.Base64
import java.io.IOException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * @ClassName UtilKDES
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 20:21
 * @Version 1.0
 */
object UtilKEncryptDES {
    private const val DES = "DES"
    private const val ENCODE = "UTF-8"

    /**
     * 根据键值进行加密
     * @param content String 待加密数据
     * @param secretKey String 密钥
     * @return String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun encrypt(content: String, secretKey: String): String {
        val bt = encrypt(content.toByteArray(charset(ENCODE)), secretKey.toByteArray(charset(ENCODE)))
        return Base64.encodeToString(bt, Base64.DEFAULT)
    }

    /**
     * 根据键值进行解密
     * @param content String 待解密数据
     * @param secretKey String 密钥
     * @return String
     * @throws IOException
     * @throws Exception
     */
    @JvmStatic
    @Throws(IOException::class, Exception::class)
    fun decrypt(content: String, secretKey: String): String {
        val buf = Base64.decode(content.toByteArray(), Base64.DEFAULT)
        val bt = decrypt(buf, secretKey.toByteArray(charset(ENCODE)))
        return String(bt, charset(ENCODE))
    }

    /**
     * 根据键值进行加密
     * @param content ByteArray
     * @param secretKey ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    private fun encrypt(content: ByteArray, secretKey: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(secretKey)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr)
        return cipher.doFinal(content)
    }

    /**
     * 根据键值进行解密
     * @param content ByteArray
     * @param secretKey ByteArray 加密键byte数组
     * @return ByteArray
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    private fun decrypt(content: ByteArray, secretKey: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(secretKey)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr)
        return cipher.doFinal(content)
    }
}