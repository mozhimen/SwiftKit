package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyLong
import com.mozhimen.basick.cachek.room.CacheKRM

/**
 * @ClassName CacheKRMDelegateLong
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMVarPropertyLong(
    cacheKRMProvider: CacheKRM, key: String, default: Long = 0L
) : BaseCacheKVarPropertyLong<CacheKRM>(cacheKRMProvider, key, default)