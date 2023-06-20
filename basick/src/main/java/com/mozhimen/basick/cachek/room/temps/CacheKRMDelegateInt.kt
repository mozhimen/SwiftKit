package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateInt
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMDelegateInt(
    cacheKRMProvider: CacheKRM, key: String, default: Int = 0
) : BaseCacheKDelegateInt<CacheKRM>(cacheKRMProvider, key, default)