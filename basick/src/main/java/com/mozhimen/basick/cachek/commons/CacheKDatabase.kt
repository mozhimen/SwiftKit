package com.mozhimen.basick.cachek.commons

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.basick.cachek.mos.MCacheK
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName CacheDatabase
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:59
 * @Version 1.0
 */
@Database(entities = [MCacheK::class], version = 1, exportSchema = false)
abstract class CacheKDatabase : RoomDatabase() {
    abstract val cacheKDao: ICacheKDao

    companion object {
        private var _db: CacheKDatabase = Room.databaseBuilder(UtilKApplication.instance.get(), CacheKDatabase::class.java, "basicsk_cachek").build()
        fun get(): CacheKDatabase {
            return _db
        }
    }
}