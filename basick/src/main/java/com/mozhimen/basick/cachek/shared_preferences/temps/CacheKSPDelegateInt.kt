package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateInt
import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPDelegateInt(
    cacheKSPProvider: CacheKSPProvider, key: String, default: Int = 0
) : BaseCacheKDelegateInt<CacheKSPProvider>(cacheKSPProvider, key, default)