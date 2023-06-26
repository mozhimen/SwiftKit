package com.mozhimen.basick.utilk.javax.crypto

import android.util.Base64
import com.mozhimen.basick.elemk.cons.CAlgorithm
import com.mozhimen.basick.elemk.cons.CCharsetName
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
object UtilKDES {
    private val _charset by lazy { charset(CCharsetName.UTF_8) }

    /**
     * 根据键值进行加密
     * @param data String 待加密数据
     * @param secretKey String 密钥
     * @return String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun encryptWithBase64(data: String, secretKey: String): String {
        return Base64.encodeToString(
            encrypt(data.toByteArray(_charset), secretKey.toByteArray(_charset)), Base64.DEFAULT
        )
    }

    /**
     * 根据键值进行解密
     * @param data String 待解密数据
     * @param secretKey String 密钥
     * @return String
     * @throws IOException
     * @throws Exception
     */
    @JvmStatic
    @Throws(IOException::class, Exception::class)
    fun decryptWithBase64(data: String, secretKey: String): String {
        return String(
            decrypt(
                Base64.decode(data.toByteArray(_charset), Base64.DEFAULT), secretKey.toByteArray(_charset)
            ),
            _charset
        )
    }

    /**
     * 根据键值进行加密
     * @param data ByteArray
     * @param secretKey ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    private fun encrypt(data: ByteArray, secretKey: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(secretKey)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(CAlgorithm.DES)
        val secureKey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(CAlgorithm.DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr)
        return cipher.doFinal(data)
    }

    /**
     * 根据键值进行解密
     * @param data ByteArray
     * @param secretKey ByteArray 加密键byte数组
     * @return ByteArray
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    private fun decrypt(data: ByteArray, secretKey: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(secretKey)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(CAlgorithm.DES)
        val secureKey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(CAlgorithm.DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secureKey, sr)
        return cipher.doFinal(data)
    }
}