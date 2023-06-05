package com.mozhimen.basick.cachek.room

import com.mozhimen.basick.cachek.commons.ICacheKProvider
import com.mozhimen.basick.cachek.room.commons.CacheKRMDatabase
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
object CacheKRM : BaseUtilK(), ICacheKProvider {
    override fun <T> putObj(key: String, obj: T) {
        val cache = MCacheKRM(key, obj.t2ByteArray() ?: run { "serialize fail!".et(TAG);return })
        CacheKRMDatabase.get().cacheKDao.saveCache(cache)
    }

    override fun putString(key: String, value: String) {
        putObj(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        putObj(key, value)
    }

    override fun putInt(key: String, value: Int) {
        putObj(key, value)
    }

    override fun putLong(key: String, value: Long) {
        putObj(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        putObj(key, value)
    }

    override fun putDouble(key: String, value: Double) {
        putObj(key, value)
    }

    /////////////////////////////////////////////////////////////////////

    @Suppress("UNCHECKED_CAST")
    override fun <T> getObj(key: String, default: T): T {
        val cache = CacheKRMDatabase.get().cacheKDao.getCache(key)
        return ((if (cache?.data != null) cache.data.byteArray2Obj() else null) as? T?) ?: default
    }

    override fun getInt(key: String): Int =
        getInt(key, 0)

    override fun getLong(key: String): Long =
        getLong(key, 0L)

    override fun getString(key: String): String =
        getString(key, "")

    override fun getBoolean(key: String): Boolean =
        getBoolean(key, false)

    override fun getFloat(key: String): Float =
        getFloat(key, 0f)

    override fun getDouble(key: String): Double =
        getDouble(key, 0.0)

    /////////////////////////////////////////////////////////////////////

    override fun getInt(key: String, defaultValue: Int): Int =
        getObj(key, defaultValue)

    override fun getLong(key: String, defaultValue: Long): Long =
        getObj(key, defaultValue)

    override fun getString(key: String, defaultValue: String): String =
        getObj(key, defaultValue)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        getObj(key, defaultValue)

    override fun getFloat(key: String, defaultValue: Float): Float =
        getObj(key, defaultValue)

    override fun getDouble(key: String, defaultValue: Double): Double =
        getObj(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun remove(key: String) {
        CacheKRMDatabase.get().cacheKDao.deleteCacheByKey(key)
    }

    fun contains(key: String): Boolean =
        CacheKRMDatabase.get().cacheKDao.getCache(key) != null

    override fun clear() {
        CacheKRMDatabase.get().cacheKDao.deleteAllCaches()
    }
}