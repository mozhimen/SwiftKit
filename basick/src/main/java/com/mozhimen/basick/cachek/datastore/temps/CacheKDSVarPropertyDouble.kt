package com.mozhimen.basick.cachek.datastore.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyDouble
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKDSVarPropertyDouble(
    cacheKDSProvider: CacheKDSProvider, key: String, default: Double = 0.0
) : BaseCacheKVarPropertyDouble<CacheKDSProvider>(cacheKDSProvider, key, default)