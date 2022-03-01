package com.mozhimen.abilitymk.cachemk.mos

import androidx.room.*

/**
 * @ClassName CacheDao
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:06
 * @Version 1.0
 */
@Dao
interface CacheDao {
    @Insert(entity = Cache::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: Cache): Long

    @Query("select * from cache where `key`=:key")
    fun getCache(key: String): Cache?

    @Delete(entity = Cache::class)
    fun deleteCache(cache: Cache)
}