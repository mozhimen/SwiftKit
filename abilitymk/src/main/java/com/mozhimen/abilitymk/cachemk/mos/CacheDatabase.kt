package com.mozhimen.abilitymk.cachemk.mos

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal

/**
 * @ClassName CacheDatabase
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:59
 * @Version 1.0
 */
@Database(entities = [Cache::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract val cacheDao: CacheDao

    companion object {
        private var database: CacheDatabase
        fun get(): CacheDatabase {
            return database
        }

        init {
            val context = UtilMKGlobal.instance.getApp()!!.applicationContext
            database =
                Room.databaseBuilder(context, CacheDatabase::class.java, "abilitymk_cachemk").build()
        }
    }
}