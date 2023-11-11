package com.mozhimen.componentk.netk.app.task.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @ClassName AppDownloadDB
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:01
 * @Version 1.0
 */
@Database(entities = [AppTask::class], version = 1, exportSchema = false)
abstract class AppTaskDb : RoomDatabase() {
    abstract fun appTaskDao(): AppTaskDao
}