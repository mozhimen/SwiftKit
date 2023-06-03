package com.mozhimen.basick.cachek.room.commons

import androidx.room.*
import com.mozhimen.basick.cachek.room.mos.MCacheKRM

/**
 * @ClassName CacheDao
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:06
 * @Version 1.0
 */
@Dao
interface ICacheKRMDao {
    @Insert(entity = MCacheKRM::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: MCacheKRM): Long

    @Query("select * from cachekrm where `key`=:key")
    fun getCache(key: String): MCacheKRM?

    @Query("delete from cachekrm where `key`=:key")
    fun deleteByKey(key: String)

//    @Delete(entity = MCacheKRM::class)
//    fun deleteCache(cache: MCacheKRM)
}