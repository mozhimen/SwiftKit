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

    override fun with(name: String): CacheKSPProvider {
        var sp = _spMap[name]
        if (sp == null) {
            sp = CacheKSPProvider(name)
            _spMap[name] = sp
        }
        return sp
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = CacheKSP()
    }
}