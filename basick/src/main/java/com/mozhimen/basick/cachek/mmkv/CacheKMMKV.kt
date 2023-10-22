package com.mozhimen.basick.cachek.mmkv

import com.mozhimen.basick.cachek.commons.ICacheK
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName CacheKMMKV
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 16:40
 * @Version 1.0
 */
class CacheKMMKV : ICacheK<CacheKMMKVProvider> {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ////////////////////////////////////////////////////////////////////////////

    private val _mmkvMap = ConcurrentHashMap<String, CacheKMMKVProvider>()

    ////////////////////////////////////////////////////////////////////////////

    override fun with(name: String): CacheKMMKVProvider =
        with(name, false)

    fun with(name: String, isMultiProcess: Boolean): CacheKMMKVProvider {
        var mmkv = _mmkvMap[name]
        if (mmkv == null) {
            mmkv = CacheKMMKVProvider(name, isMultiProcess)
            _mmkvMap[name] = mmkv
        }
        return mmkv
    }

    ////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = CacheKMMKV()
    }
}