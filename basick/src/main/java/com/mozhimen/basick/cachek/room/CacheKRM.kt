package com.mozhimen.basick.cachek.room

import com.mozhimen.basick.cachek.commons.ICacheKProvider
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
object CacheKRM : BaseUtilK(), ICacheKRM, ICacheKProvider {
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

    @Suppress("UNCHECKED_CAST")
    override fun <T> getObj(key: String): T? {
        val cache = CacheKRMDatabase.get().cacheKDao.getCache(key)
        return (if (cache?.data != null) cache.data.byteArray2Obj() else null) as? T?
    }

    override fun getInt(key: String): Int =
        getInt(key, 0)

    override fun getInt(key: String, defaultValue: Int): Int =
        getObj(key) ?: defaultValue

    override fun getLong(key: String): Long =
        getLong(key, 0L)

    override fun getLong(key: String, defaultValue: Long): Long =
        getObj(key) ?: defaultValue

    override fun getString(key: String): String =
        getString(key, "")

    override fun getString(key: String, defaultValue: String): String =
        getObj(key) ?: defaultValue

    override fun getBoolean(key: String): Boolean =
        getBoolean(key, false)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFloat(key: String): Float {
        TODO("Not yet implemented")
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        TODO("Not yet implemented")
    }

    override fun getDouble(key: String): Double {
        TODO("Not yet implemented")
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        TODO("Not yet implemented")
    }

    override fun remove(key: String) {
        CacheKRMDatabase.get().cacheKDao.deleteCacheByKey(key)
    }


}