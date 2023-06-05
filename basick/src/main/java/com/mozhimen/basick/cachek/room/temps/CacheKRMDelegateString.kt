package com.mozhimen.basick.cachek.room.temps

import com.mozhimen.basick.cachek.bases.BaseCacheKDelegateString
import com.mozhimen.basick.cachek.datastore.helpers.CacheKDSProvider
import com.mozhimen.basick.cachek.room.CacheKRM


/**
 * @ClassName CacheKSPDelegateString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKRMDelegateString(
    cacheKRMProvider: CacheKRM, key: String, default: String = ""
) : BaseCacheKDelegateString<CacheKRM>(cacheKRMProvider,key,default)