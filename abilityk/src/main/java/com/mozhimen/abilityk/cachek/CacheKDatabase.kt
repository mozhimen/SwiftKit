package com.mozhimen.abilityk.cachek

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.abilityk.cachek.commons.CacheKDao
import com.mozhimen.abilityk.cachek.mos.CacheK
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
    abstract val cacheKDao: CacheKDao

    companion object {
        private var KDatabase: CacheKDatabase
        fun get(): CacheKDatabase {
            return KDatabase
        }

        init {
            val context = UtilKGlobal.instance.getApp()!!.applicationContext
            KDatabase =
                Room.databaseBuilder(context, CacheKDatabase::class.java, "abilityk_cachek").build()
        }
    }
}