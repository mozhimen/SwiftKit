package com.mozhimen.basick.postk.crypto.helpers

import android.util.Base64
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.postk.crypto.commons.ICryptoProvider
import com.mozhimen.basick.postk.crypto.mos.MCryptoDESConfig
import java.io.IOException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * @ClassName EncryptDESProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 16:24
 * @Version 1.0
 */
class CryptoDESProvider(private val _config: MCryptoDESConfig) : ICryptoProvider {
    /**
     * 根据键值进行加密
     * @param str String 待加密数据
     * @return String
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun encryptWithBase64(str: String): String =
        Base64.encodeToString(encrypt(str.toByteArray(_config.charset)), Base64.DEFAULT)

    /**
     * 根据键值进行解密
     * @param str String 待解密数据
     * @return String
     * @throws Exception
     */
    @Throws(IOException::class, Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun decryptWithBase64(str: String): String =
        String(decrypt(Base64.decode(str.toByteArray(_config.charset), Base64.DEFAULT)), _config.charset)

    /**
     * 根据键值进行加密
     * @param bytes ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun encrypt(bytes: ByteArray): ByteArray {
        // 从原始密钥数据创建DESKeySpec对象
        val desKeySpec = DESKeySpec(_config.secretKey.toByteArray(_config.charset))
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val secureKey = SecretKeyFactory.getInstance(_config.keyAlgorithm).generateSecret(desKeySpec)
        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(_config.keyAlgorithm)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, SecureRandom()/*生成一个可信任的随机数源*/)
        // 正式执行加密操作
        return cipher.doFinal(bytes)
    }

    /**
     * 根据键值进行解密
     * @param bytes ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun decrypt(bytes: ByteArray): ByteArray {
        // 从原始密钥数据创建DESKeySpec对象
        val desKeySpec = DESKeySpec(_config.secretKey.toByteArray(_config.charset))
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        val secureKey = SecretKeyFactory.getInstance(_config.keyAlgorithm).generateSecret(desKeySpec)
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(_config.keyAlgorithm)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secureKey, SecureRandom()/*生成一个可信任的随机数源*/)
        // 正式执行解密操作
        return cipher.doFinal(bytes)
    }
}