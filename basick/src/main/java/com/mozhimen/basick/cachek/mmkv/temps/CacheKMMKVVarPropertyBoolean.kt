package com.mozhimen.basick.cachek.mmkv.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyBoolean
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKMMKVVarPropertyBoolean(
    cacheKMMKVProvider: CacheKMMKVProvider, key: String, default: Boolean = false
) : BaseCacheKVarPropertyBoolean<CacheKMMKVProvider>(cacheKMMKVProvider, key, default)