package com.mozhimen.basick.cachek.cacheksp

import com.mozhimen.basick.cachek.cacheksp.helpers.CacheKSPProvider
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilKSharedPreferences
 * @Description UtilKSP.INSTANCE.with("123").getAll()
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:09
 * @Version 1.0
 */
class CacheKSP {

    companion object {
        @JvmStatic
        val instance = UtilKSPProvider.holder
    }

    private object UtilKSPProvider {
        val holder = CacheKSP()
    }

    private val _spMap = ConcurrentHashMap<String, CacheKSPProvider>()

    /**
     * 携带sp名称
     * @param spName String
     * @return CacheKSPProvider
     */
    @Synchronized
    fun with(spName: String): CacheKSPProvider {
        return if (_spMap[spName] != null && _spMap.containsKey(spName)) {
            _spMap[spName]!!
        } else {
            val provider = CacheKSPProvider(spName)
            _spMap[spName] = provider
            provider
        }
    }
}