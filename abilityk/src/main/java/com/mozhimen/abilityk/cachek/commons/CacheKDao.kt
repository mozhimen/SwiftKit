package com.mozhimen.abilityk.cachek.commons

import androidx.room.*
import com.mozhimen.abilityk.cachek.mos.CacheK

/**
 * @ClassName CacheDao
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:06
 * @Version 1.0
 */
@Dao
interface CacheKDao {
    @Insert(entity = CacheK::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cacheK: CacheK): Long

    @Query("select * from cachek where `key`=:key")
    fun getCache(key: String): CacheK?

    @Delete(entity = CacheK::class)
    fun deleteCache(cacheK: CacheK)
}