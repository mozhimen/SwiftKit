package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateBoolean
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMDelegateBoolean(
    cacheKRMProvider: CacheKRM, key: String, default: Boolean = false
) : BaseCacheKDelegateBoolean<CacheKRM>(cacheKRMProvider, key, default)