package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyDouble
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKRMDelegateDouble
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMVarPropertyDouble(
    cacheKRMProvider: CacheKRM, key: String, default: Double = 0.0
) : BaseCacheKVarPropertyDouble<CacheKRM>(cacheKRMProvider, key, default)