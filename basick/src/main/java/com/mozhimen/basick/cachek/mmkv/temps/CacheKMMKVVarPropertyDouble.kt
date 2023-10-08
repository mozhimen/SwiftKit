package com.mozhimen.basick.cachek.mmkv.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyDouble
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKMMKVVarPropertyDouble(
    cacheKMMKVProvider: CacheKMMKVProvider, key: String, default: Double = 0.0
) : BaseCacheKVarPropertyDouble<CacheKMMKVProvider>(cacheKMMKVProvider, key, default)