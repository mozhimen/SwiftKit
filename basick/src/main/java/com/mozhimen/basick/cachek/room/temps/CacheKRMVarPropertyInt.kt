package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyInt
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKRMDelegateInt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMVarPropertyInt(
    cacheKRMProvider: CacheKRM, key: String, default: Int = 0
) : BaseCacheKVarPropertyInt<CacheKRM>(cacheKRMProvider, key, default)