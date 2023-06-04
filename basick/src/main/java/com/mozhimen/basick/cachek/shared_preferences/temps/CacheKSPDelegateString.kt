package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateString
import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider


/**
 * @ClassName CacheKSPDelegateString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPDelegateString(
    cacheKSPProvider: CacheKSPProvider, key: String, default: String = ""
) : BaseCacheKDelegateString<CacheKSPProvider>(cacheKSPProvider, key, default)