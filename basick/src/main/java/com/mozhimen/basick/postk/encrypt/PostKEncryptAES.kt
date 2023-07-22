package com.mozhimen.basick.postk.encrypt

import com.mozhimen.basick.postk.encrypt.commons.IEncrypt
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
object PostKEncryptAES : IEncrypt<MEncryptAESConfig, EncryptAESProvider> {

    /**
     * 默认参数配置
     *
     * secretKey: String = "saaierForTodoKey",
     * ivString: String = "ihaierForTodo_Iv",
     * secureKeyLength: Int = 16,
     * defaultFill: String = "0",
     * charset: Charset = Charsets.UTF_8,
     * cipherAlgorithm: String = "AES/CBC/PKCS5Padding"
     *
     * @return EncryptAESProvider
     */
    override fun with(config: MEncryptAESConfig): EncryptAESProvider =
        EncryptAESProvider(config)

    override fun with(): EncryptAESProvider =
        with(MEncryptAESConfig())
}