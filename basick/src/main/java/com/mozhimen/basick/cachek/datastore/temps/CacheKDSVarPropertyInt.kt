package com.mozhimen.basick.cachek.datastore.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyInt
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKDSVarPropertyInt(
    cacheKDSProvider: CacheKDSProvider, key: String, default: Int = 0
) : BaseCacheKVarPropertyInt<CacheKDSProvider>(cacheKDSProvider, key, default)