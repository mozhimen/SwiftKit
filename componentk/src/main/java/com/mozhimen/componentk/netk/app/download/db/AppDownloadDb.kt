package com.mozhimen.componentk.netk.app.download.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @ClassName AppDownloadDB
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:01
 * @Version 1.0
 */
@Database(entities = [AppDownloadTask::class], version = 1, exportSchema = false)
abstract class AppDownloadDb : RoomDatabase() {
    abstract fun appDownloadParamDao(): AppDownloadParamDao
}