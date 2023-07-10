package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyBoolean
import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPVarPropertyBoolean(
    cacheKSPProvider: CacheKSPProvider, key: String, default: Boolean = false
) : BaseCacheKVarPropertyBoolean<CacheKSPProvider>(cacheKSPProvider, key, default)