package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyDouble
import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPVarPropertyDouble(
    cacheKSPProvider: CacheKSPProvider, key: String, default: Double = 0.0
) : BaseCacheKVarPropertyDouble<CacheKSPProvider>(cacheKSPProvider, key, default)