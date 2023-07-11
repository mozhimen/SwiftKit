package com.mozhimen.basick.cachek.sharedpreferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyString
import com.mozhimen.basick.cachek.sharedpreferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPVarPropertyString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPVarPropertyString(
    cacheKSPProvider: CacheKSPProvider, key: String, default: String = ""
) : BaseCacheKVarPropertyString<CacheKSPProvider>(cacheKSPProvider, key, default)