package com.mozhimen.basick.postk.crypto.helpers

import android.util.Base64
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.postk.crypto.commons.ICryptoProvider
import com.mozhimen.basick.postk.crypto.mos.MCryptoAESConfig
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.bytes2strBase64
import com.mozhimen.basick.utilk.kotlin.bytesBase642bytes
import com.mozhimen.basick.utilk.kotlin.str2bytes
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @ClassName EncryptAESProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/12 15:58
 * @Version 1.0
 */
class CryptoAESProvider(private val _config: MCryptoAESConfig) : ICryptoProvider {

    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun encryptWithBase64(str: String): String =
        encrypt(str.str2bytes(_config.charset)).bytes2strBase64(Base64.DEFAULT)

    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun decryptWithBase64(str: String): String =
        decrypt(str.str2bytes(_config.charset).bytesBase642bytes(Base64.DEFAULT)).bytes2str(_config.charset)

    /**
     * 采用AES128加密
     * @param bytes ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun encrypt(bytes: ByteArray): ByteArray {
        // 从原始密匙数据创建KeySpec对象
        val secretKeySpec = SecretKeySpec(getAESKey(_config.secretKey.toByteArray(_config.charset)), _config.keyAlgorithm)
        // 用密匙初始化Cipher对象
        val ivParameterSpec = IvParameterSpec(_config.ivString.toByteArray(_config.charset))
        // Cipher对象实际完成加密操作
        val cipher = Cipher.getInstance(_config.cipherAlgorithm)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        // 正式执行加密操作
        return cipher.doFinal(bytes)
    }

    /**
     * 采用AES128解密,API>=19
     * @param bytes ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    override fun decrypt(bytes: ByteArray): ByteArray {
        // 从原始密匙数据创建一个KeySpec对象
        val secretKeySpec = SecretKeySpec(getAESKey(_config.secretKey.toByteArray(_config.charset)) /*secureKey.getBytes();*/, _config.keyAlgorithm)
        // 用密匙初始化Cipher对象
        val ivParameterSpec = IvParameterSpec(_config.ivString.toByteArray(_config.charset))
        // Cipher对象实际完成解密操作
        val cipher = Cipher.getInstance(_config.cipherAlgorithm)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        // 正式执行解密操作
        return cipher.doFinal(bytes)
    }

    /**
     * >=19
     * @param bytes ByteArray
     * @return ByteArray
     * @throws Exception
     */
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_19_44_K)
    fun getAESKey(bytes: ByteArray): ByteArray {
        val keyBytes16 = ByteArray(_config.secureKeyLength)
        System.arraycopy(bytes, 0, keyBytes16, 0, bytes.size.coerceAtMost(_config.secureKeyLength))
        return keyBytes16
    }
}