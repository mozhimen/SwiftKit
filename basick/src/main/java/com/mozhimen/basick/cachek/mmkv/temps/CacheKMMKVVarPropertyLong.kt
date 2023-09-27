package com.mozhimen.basick.cachek.mmkv.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyLong
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider

/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKMMKVVarPropertyLong(
    cacheKMMKVProvider: CacheKMMKVProvider, key: String, default: Long = 0L
) : BaseCacheKVarPropertyLong<CacheKMMKVProvider>(cacheKMMKVProvider, key, default)