package com.mozhimen.basick.cachek.mmkv.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyFloat
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKMMKVVarPropertyFloat(
    cacheKMMKVProvider: CacheKMMKVProvider, key: String, default: Float = 0f
) : BaseCacheKVarPropertyFloat<CacheKMMKVProvider>(cacheKMMKVProvider, key, default)