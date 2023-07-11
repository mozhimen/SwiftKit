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

    ////////////////////////////////////////////////////////////////////////////

    private val _dsMap = ConcurrentHashMap<String, CacheKDSProvider>()

    ////////////////////////////////////////////////////////////////////////////

    override fun with(name: String): CacheKDSProvider {
        var sp = _dsMap[name]
        if (sp == null) {
            sp = CacheKDSProvider(name)
            _dsMap[name] = sp
        }
        return sp
    }

    ////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = CacheKDS()
    }
}