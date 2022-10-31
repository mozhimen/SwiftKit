package com.mozhimen.basick.cachek.commons

import androidx.room.*
import com.mozhimen.basick.cachek.mos.MCacheK

/**
 * @ClassName CacheDao
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:06
 * @Version 1.0
 */
@Dao
interface ICacheKDao {
    @Insert(entity = MCacheK::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(MCacheK: MCacheK): Long

    @Query("select * from cachek where `key`=:key")
    fun getCache(key: String): MCacheK?

    @Delete(entity = MCacheK::class)
    fun deleteCache(cache: MCacheK)
}