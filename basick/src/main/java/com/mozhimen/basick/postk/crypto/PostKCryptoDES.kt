package com.mozhimen.basick.postk.crypto

import com.mozhimen.basick.postk.crypto.commons.ICrypto
import com.mozhimen.basick.postk.crypto.helpers.CryptoDESProvider
import com.mozhimen.basick.postk.crypto.mos.MCryptoDESConfig

/**
 * @ClassName UtilKDES
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 20:21
 * @Version 1.0
 */
object PostKCryptoDES : ICrypto<MCryptoDESConfig, CryptoDESProvider> {

    /**
     * With
     *
     * secretKey: String = "saaierForTodoKey",
     * charset: Charset = Charsets.UTF_8
     *
     * @return EncryptDESProvider
     */
    override fun with(
        config: MCryptoDESConfig
    ): CryptoDESProvider =
        CryptoDESProvider(config)


    override fun with(): CryptoDESProvider =
        with(MCryptoDESConfig())
}