package com.mozhimen.basick.postk.encrypt

import com.mozhimen.basick.postk.encrypt.bases.BaseEncryptConfig
import com.mozhimen.basick.postk.encrypt.commons.IEncrypt
import com.mozhimen.basick.postk.encrypt.commons.IEncryptProvider
import com.mozhimen.basick.postk.encrypt.helpers.EncryptDESProvider
import com.mozhimen.basick.postk.encrypt.mos.MEncryptDESConfig
import java.nio.charset.Charset

/**
 * @ClassName UtilKDES
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 20:21
 * @Version 1.0
 */
object PostKEncryptDES : IEncrypt<MEncryptDESConfig, EncryptDESProvider> {

    /**
     * With
     *
     * secretKey: String = "saaierForTodoKey",
     * charset: Charset = Charsets.UTF_8
     *
     * @return EncryptDESProvider
     */
    override fun with(
        config: MEncryptDESConfig
    ): EncryptDESProvider =
        EncryptDESProvider(config)


    override fun with(): EncryptDESProvider =
        with(MEncryptDESConfig())
}