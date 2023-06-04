package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateLong
import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPDelegateLong(
    cacheKSPProvider: CacheKSPProvider, key: String, default: Long = 0L
) : BaseCacheKDelegateLong<CacheKSPProvider>(cacheKSPProvider, key, default)