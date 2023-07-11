package com.mozhimen.basick.cachek.sharedpreferences

import com.mozhimen.basick.cachek.commons.ICacheK
import com.mozhimen.basick.cachek.sharedpreferences.helpers.CacheKSPProvider
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilKSharedPreferences
 * @Description CacheKSP.INSTANCE.with("123").getAll()
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:09
 * @Version 1.0
 */

@Deprecated(
    message = "replace with datastore 用datastore替代sharedPreferences",
    replaceWith = ReplaceWith(
        "CacheKDS",
        imports = ["com.mozhimen.basic.cachek.datastore.CacheKDS"]
    )
)
class CacheKSP : ICacheK<CacheKSPProvider> {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private val _spMap = ConcurrentHashMap<String, CacheKSPProvider>()

    /////////////////////////////////////////////////////////////////////////////////////

    @Synchronized
    override fun with(spName: String): CacheKSPProvider {
        return if (_spMap[spName] != null && _spMap.containsKey(spName)) {
            _spMap[spName]!!
        } else {
            val provider = CacheKSPProvider(spName)
            _spMap[spName] = provider
            provider
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = CacheKSP()
    }
}