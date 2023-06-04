package com.mozhimen.basick.cachek.datastore

import com.mozhimen.basick.cachek.commons.ICacheK
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName CacheKDS
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/4 13:54
 * @Version 1.0
 */
class CacheKDS : ICacheK<CacheKDSProvider> {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _spMap = ConcurrentHashMap<String, CacheKDSProvider>()

    @Synchronized
    override fun with(spName: String): CacheKDSProvider {
        return if (_spMap[spName] != null && _spMap.containsKey(spName)) {
            _spMap[spName]!!
        } else {
            val provider = CacheKDSProvider(spName)
            _spMap[spName] = provider
            provider
        }
    }

    private object INSTANCE {
        val holder = CacheKDS()
    }
}