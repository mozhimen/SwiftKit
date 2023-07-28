package com.mozhimen.basick.postk.crypto.commons

import com.mozhimen.basick.postk.crypto.bases.BaseCryptoConfig


/**
 * @ClassName IEncrypt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/21 18:40
 * @Version 1.0
 */
interface ICrypto<C : BaseCryptoConfig, P : ICryptoProvider> {
    fun with(config: C): P
    fun with(): P
}