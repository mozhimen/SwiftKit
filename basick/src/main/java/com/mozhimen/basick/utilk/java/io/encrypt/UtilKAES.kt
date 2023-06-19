package com.mozhimen.basick.utilk.java.io.encrypt

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @ClassName UtilKEncryptAES
 * @Description  AESUtil.with("xxx","xxx").encrypt("xxx")
 * @Author Kolin Zhao
 * @Date 2021/10/14 15:13
 * @Version 1.0
 */
object UtilKAES {

    /**
     * 默认参数配置
     * @param secretKey String 需要16位
     * @param ivString String 需要16位
     * @param keyAlgorithm String
     * @param secureKeyLength Int
     * @param defaultFill String
     * @param charset Charset
     * @param cipherAlgorithm String
     * @return UtilKAESProvider
     */
    @JvmStatic
    fun with(
        charset: Charset = Charsets.UTF_8,
        secretKey: String = "saaierForTodoKey",
        ivString: String = "ihaierForTodo_Iv",
        keyAlgorithm: String = "AES",
        secureKeyLength: Int = 16,
        defaultFill: String = "0",
        cipherAlgorithm: String = "AES/CBC/PKCS5Padding"
    ): UtilKAESProvider {
        return UtilKAESProvider(UtilKEncryptAESConfig(secretKey, ivString, keyAlgorithm, secureKeyLength, defaultFill, charset, cipherAlgorithm))
    }

    data class UtilKEncryptAESConfig(
        var secretKey: String,//key
        var ivString: String, //IV
        var keyAlgorithm: String,//加密算法
        var secureKeyLength: Int,  //AES 的 密钥长度，32 字节，范围：16 - 32 字节
        var defaultFill: String,    //秘钥长度不足 16 个字节时，默认填充位数
        var charset: Charset,  //字符编码
        var cipherAlgorithm: String    //加解密算法/工作模式/填充方式
    )

    class UtilKAESProvider(
        private val _config: UtilKEncryptAESConfig
    ) {

        fun encryptWithBase64(data: String): String {
            return Base64.encodeToString(
                encrypt(data.toByteArray(_config.charset)), Base64.DEFAULT
            )
        }

        fun decryptWithBase64(data: String): String {
            return String(
                decrypt(
                    Base64.decode(data.toByteArray(_config.charset), Base64.DEFAULT)
                ) ?: throw Exception("decode by aes fail"),
                _config.charset
            )
        }

        /**
         * 采用AES128加密
         * @param data String 要加密的内容
         * @return String?
         */
        @Throws(Exception::class)
        fun encrypt(data: ByteArray): ByteArray {
            // 获得密匙数据
            val rawKeyData = getAESKey(_config.secretKey.toByteArray(_config.charset))
            // 从原始密匙数据创建KeySpec对象
            val key = SecretKeySpec(rawKeyData, _config.keyAlgorithm)
            // Cipher对象实际完成加密操作
            val cipher = Cipher.getInstance(_config.cipherAlgorithm)
            // 用密匙初始化Cipher对象
            val ivParameterSpec = IvParameterSpec(_config.ivString.toByteArray(_config.charset))
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
            // 正式执行加密操作
            return cipher.doFinal(data)
        }

        /**
         * 采用AES128解密,API>=19
         * @param data String
         * @return String?
         */
        @Throws(Exception::class)
        fun decrypt(data: ByteArray): ByteArray? {
            // 获得密匙数据
            val rawKeyData = getAESKey(_config.secretKey.toByteArray(_config.charset)) // secureKey.getBytes();
            // 从原始密匙数据创建一个KeySpec对象
            val key = SecretKeySpec(rawKeyData, _config.keyAlgorithm)
            // Cipher对象实际完成解密操作
            val cipher = Cipher.getInstance(_config.cipherAlgorithm)
            // 用密匙初始化Cipher对象
            val initParam = _config.ivString.toByteArray(_config.charset)
            val ivParameterSpec = IvParameterSpec(initParam)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
            return cipher.doFinal(data)
        }

        /**
         * >=19
         * @param byteArray ByteArray
         * @return ByteArray
         * @throws UnsupportedEncodingException
         */
        @Throws(Exception::class)
        fun getAESKey(byteArray: ByteArray): ByteArray {
            val keyBytes16 = ByteArray(_config.secureKeyLength)
            System.arraycopy(byteArray, 0, keyBytes16, 0, byteArray.size.coerceAtMost(_config.secureKeyLength))
            return keyBytes16
        }
    }
}