package com.mozhimen.basick.postk.encrypt.commons

import com.mozhimen.basick.postk.encrypt.bases.BaseEncryptConfig


/**
 * @ClassName IEncrypt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/21 18:40
 * @Version 1.0
 */
interface IEncrypt<C : BaseEncryptConfig, P : IEncryptProvider> {
    fun with(config: C): P
    fun with(): P
}