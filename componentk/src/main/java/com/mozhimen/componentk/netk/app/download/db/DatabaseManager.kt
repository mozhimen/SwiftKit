package com.mozhimen.componentk.netk.app.download.db

import android.content.Context
import androidx.room.Room
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication

/**
 * @ClassName DatabaseManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:01
 * @Version 1.0
 */
@OptInApiInit_InApplication
object DatabaseManager {
    private lateinit var _appDownloadDb: AppDownloadDb

    lateinit var appDownloadParamDao: AppDownloadParamDao
        private set

//    /**
//     * 用户数据库从version 1 升级到 version 2
//     */
//    private val APP_DATABASE_MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE app_file_params ADD COLUMN app_size INTEGER NOT NULL DEFAULT 0")
//            database.execSQL("ALTER TABLE app_file_params ADD COLUMN need_checking INTEGER NOT NULL DEFAULT 0")
//        }
//    }

    fun init(context: Context) {
        _appDownloadDb = Room.databaseBuilder(context, AppDownloadDb::class.java, "netk_app_download_db")
//            .fallbackToDestructiveMigration()//使用该方法会在数据库升级异常时重建数据库，但是所有数据会丢失
//            .addMigrations(APP_DATABASE_MIGRATION_1_2)
            .build()
        appDownloadParamDao = _appDownloadDb.appDownloadParamDao()
        DaoManager.init()
    }
}