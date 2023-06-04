package com.mozhimen.basick.cachek.datastore.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateString
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider


/**
 * @ClassName CacheKSPDelegateString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKDSDelegateString(
    cacheKDSProvider: CacheKDSProvider, key: String, default: String = ""
) : BaseCacheKDelegateString<CacheKDSProvider>(cacheKDSProvider,key,default)