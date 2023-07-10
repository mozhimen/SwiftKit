package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyFloat
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKRMDelegateFloat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMVarPropertyFloat(
    cacheKRMProvider: CacheKRM, key: String, default: Float = 0f
) : BaseCacheKVarPropertyFloat<CacheKRM>(cacheKRMProvider, key, default)