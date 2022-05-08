package com.mozhimen.basicsk.cachek

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.basicsk.cachek.commons.ICacheKDao
import com.mozhimen.basicsk.cachek.mos.CacheK
import com.mozhimen.basicsk.utilk.UtilKGlobal

/**
 * @ClassName CacheDatabase
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:59
 * @Version 1.0
 */
@Database(entities = [CacheK::class], version = 1, exportSchema = false)
abstract class CacheKDatabase : RoomDatabase() {
    abstract val cacheKDao: ICacheKDao

    companion object {
        private var _db: CacheKDatabase
        fun get(): CacheKDatabase {
            return _db
        }

        init {
            val context = UtilKGlobal.instance.getApp()!!.applicationContext
            _db =
                Room.databaseBuilder(context, CacheKDatabase::class.java, "basicsk_cachek").build()
        }
    }
}