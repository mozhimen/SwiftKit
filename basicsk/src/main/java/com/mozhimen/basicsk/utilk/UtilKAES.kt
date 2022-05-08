package com.mozhimen.basicsk.utilk

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @ClassName AESUtil
 * @Description  AESUtil.require("xxx","xxx").encrypt("xxx")
 * @Author Kolin Zhao
 * @Date 2021/10/14 15:13
 * @Version 1.0
 */
object UtilKAES {

    //region # constants
    //加密算法
    private const val KEY_ALGORITHM = "AES"

    //AES 的 密钥长度，32 字节，范围：16 - 32 字节
    private const val SECURE_KEY_LENGTH = 16

    //秘钥长度不足 16 个字节时，默认填充位数
    private const val DEFAULT_FILL = "0"

    //字符编码
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private val CHARSET_UTF8 = StandardCharsets.UTF_8

    //加解密算法/工作模式/填充方式
    private const val CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"

    //endregion
    private var _keyAlgorithm = KEY_ALGORITHM

    private var _secureKeyLength = SECURE_KEY_LENGTH

    private var _defaultFill = DEFAULT_FILL

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private var _charset = CHARSET_UTF8

    private var _cipherAlgorithm = CIPHER_ALGORITHM

    //key
    private var _secureKey: String? = null

    //IV
    private var _ivString: String? = null

    /**
     * 默认参数配置
     * @param secureKey String
     * @param _ivString String
     * @param keyAlgorithm String?
     * @param secureKeyLength Int?
     * @param defaultFill String?
     * @param charset Charset?
     * @param cipherAlgorithm String?
     * @return UtilKAES
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun require(
        secureKey: String, _ivString: String,
        keyAlgorithm: String? = KEY_ALGORITHM,
        secureKeyLength: Int? = SECURE_KEY_LENGTH,
        defaultFill: String? = DEFAULT_FILL,
        charset: Charset? = CHARSET_UTF8,
        cipherAlgorithm: String? = CIPHER_ALGORITHM
    ): UtilKAES {
        this._secureKey = secureKey
        this._ivString = _ivString
        keyAlgorithm?.let {
            this._keyAlgorithm = it
        }
        secureKeyLength?.let {
            this._secureKeyLength = it
        }
        defaultFill?.let {
            this._defaultFill = it
        }
        charset?.let {
            this._charset = it
        }
        cipherAlgorithm?.let {
            this._cipherAlgorithm = it
        }
        return this
    }

    /**
     * 采用AES128加密
     * @param content String 要加密的内容
     * @return String?
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun encrypt(content: String): String? {
        require(_secureKey != null && _ivString != null) {
            "secureKey or _ivString must not be null"
        }

        try {
            // 获得密匙数据
            val rawKeyData = getAESKey(_secureKey!!)
            // 从原始密匙数据创建KeySpec对象
            val key = SecretKeySpec(rawKeyData, _keyAlgorithm)
            // Cipher对象实际完成加密操作
            val cipher = Cipher.getInstance(_cipherAlgorithm)
            // 用密匙初始化Cipher对象
            val ivParameterSpec = IvParameterSpec(_ivString!!.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
            // 正式执行加密操作
            val encryptByte = cipher.doFinal(content.toByteArray())
            return Base64.encodeToString(encryptByte, Base64.NO_WRAP)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 采用AES128解密,API>=19
     * @param content String
     * @param secureKey String
     * @return String?
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun decrypt(content: String, secureKey: String): String? {
        require(_secureKey != null && _ivString != null) {
            "secureKey or _ivString must not be null"
        }

        val data: ByteArray = Base64.decode(content, Base64.NO_WRAP)
        try {
            // 获得密匙数据
            val rawKeyData = getAESKey(secureKey) // secureKey.getBytes();
            // 从原始密匙数据创建一个KeySpec对象
            val key = SecretKeySpec(rawKeyData, _keyAlgorithm)
            // Cipher对象实际完成解密操作
            val cipher = Cipher.getInstance(_cipherAlgorithm)
            // 用密匙初始化Cipher对象
            val initParam = _ivString!!.toByteArray()
            val ivParameterSpec = IvParameterSpec(initParam)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
            return String(cipher.doFinal(data), _charset)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * API>=19
     * @param key String
     * @return ByteArray
     * @throws UnsupportedEncodingException
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Throws(UnsupportedEncodingException::class)
    fun getAESKey(key: String): ByteArray {
        val keyBytes: ByteArray = key.toByteArray(_charset)
        val keyBytes16 = ByteArray(_secureKeyLength)
        System.arraycopy(
            keyBytes, 0, keyBytes16, 0,
            keyBytes.size.coerceAtMost(_secureKeyLength)
        )
        return keyBytes16
    }
}