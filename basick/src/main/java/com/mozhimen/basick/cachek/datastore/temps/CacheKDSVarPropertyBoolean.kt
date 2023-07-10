package com.mozhimen.basick.cachek.datastore.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyBoolean
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKDSVarPropertyBoolean(
    cacheKDSProvider: CacheKDSProvider, key: String, default: Boolean = false
) : BaseCacheKVarPropertyBoolean<CacheKDSProvider>(cacheKDSProvider, key, default)