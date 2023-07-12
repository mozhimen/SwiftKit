package com.mozhimen.basick.postk.encrypt

import com.mozhimen.basick.postk.encrypt.helpers.EncryptAESProvider
import com.mozhimen.basick.postk.encrypt.mos.MEncryptAESConfig
import java.nio.charset.Charset

/**
 * @ClassName UtilKEncryptAES
 * @Description  AESUtil.with("xxx","xxx").encrypt("xxx")
 * @Author Kolin Zhao
 * @Date 2021/10/14 15:13
 * @Version 1.0
 */
object PostKEncryptAES {

    /**
     * 默认参数配置
     * @param secretKey String 需要16位
     * @param ivString String 需要16位
     * @param secureKeyLength Int
     * @param defaultFill String
     * @param charset Charset
     * @param cipherAlgorithm String
     * @return UtilKAESProvider
     */
    @JvmStatic
    fun with(
        secretKey: String = "saaierForTodoKey",
        ivString: String = "ihaierForTodo_Iv",
        secureKeyLength: Int = 16,
        defaultFill: String = "0",
        charset: Charset = Charsets.UTF_8,
        cipherAlgorithm: String = "AES/CBC/PKCS5Padding"
    ): EncryptAESProvider =
        EncryptAESProvider(MEncryptAESConfig(secretKey, ivString, secureKeyLength, defaultFill, charset, cipherAlgorithm))
}