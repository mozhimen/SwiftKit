package com.mozhimen.basick.cachek.mmkv.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKVarPropertyString
import com.mozhimen.basick.cachek.mmkv.helpers.CacheKMMKVProvider


/**
 * @ClassName CacheKDSVarPropertyString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKMMKVVarPropertyString(
    cacheKMMKVProvider: CacheKMMKVProvider, key: String, default: String = ""
) : BaseCacheKVarPropertyString<CacheKMMKVProvider>(cacheKMMKVProvider,key,default)