package com.mozhimen.basick.cachek.room

import com.mozhimen.basick.cachek.room.commons.CacheKRMDatabase
import com.mozhimen.basick.cachek.room.commons.ICacheKRM
import com.mozhimen.basick.cachek.room.mos.MCacheKRM
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.datatype.byteArray2Obj
import com.mozhimen.basick.utilk.java.datatype.t2ByteArray
import com.mozhimen.basick.utilk.log.et

/**
 * @ClassName CacheK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:50
 * @Version 1.0
 */
object CacheKRM : BaseUtilK(), ICacheKRM {
    override fun <T> saveCache(key: String, body: T) {
        val cache = MCacheKRM(key, body.t2ByteArray() ?: run { "serialize fail!".et(TAG);return })
        CacheKRMDatabase.get().cacheKDao.saveCache(cache)
    }

    /**
     * 获取cache
     * @param key String
     * @return T?
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getCache(key: String): T? {
        val cache = CacheKRMDatabase.get().cacheKDao.getCache(key)
        return (if (cache?.data != null) cache.data.byteArray2Obj() else null) as? T?
    }

    fun deleteCache(key: String) {
        CacheKRMDatabase.get().cacheKDao.deleteByKey(key)
    }
}