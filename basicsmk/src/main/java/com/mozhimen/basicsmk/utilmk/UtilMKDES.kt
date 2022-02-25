package com.mozhimen.basicsmk.utilmk

import android.util.Base64
import java.io.IOException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * @ClassName UtilMKDES
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 20:21
 * @Version 1.0
 */
object UtilMKDES {
    private const val DES = "DES"
    private const val ENCODE = "UTF-8"

    /**
     * Description 根据键值进行加密
     * @param data 待加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encrypt(data: String, key: String): String? {
        val bt = encrypt(data.toByteArray(charset(ENCODE)), key.toByteArray(charset(ENCODE)))
        return Base64.encodeToString(bt, Base64.DEFAULT)
    }

    /**
     * 根据键值进行解密
     * @param data 待解密数据
     * @param key    密钥
     * @return
     * @throws IOException
     * @throws Exception
     */
    @Throws(IOException::class, Exception::class)
    fun decrypt(data: String?, key: String): String? {
        if (data == null) {
            return null
        }
        val buf = Base64.decode(data.toByteArray(), Base64.DEFAULT)
        val bt = decrypt(buf, key.toByteArray(charset(ENCODE)))
        return String(bt, charset(ENCODE))
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     * 加密键byte数组
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun encrypt(data: ByteArray, key: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(key)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr)
        return cipher.doFinal(data)
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun decrypt(data: ByteArray, key: ByteArray): ByteArray {
        // 生成一个可信任的随机数源
        val sr = SecureRandom()
        // 从原始密钥数据创建DESKeySpec对象
        val dks = DESKeySpec(key)
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val keyFactory = SecretKeyFactory.getInstance(DES)
        val securekey = keyFactory.generateSecret(dks)
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(DES)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr)
        return cipher.doFinal(data)
    }
}