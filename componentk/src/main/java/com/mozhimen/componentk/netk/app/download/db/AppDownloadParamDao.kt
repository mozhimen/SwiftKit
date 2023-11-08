package com.mozhimen.componentk.netk.app.download.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * @ClassName AppDownloadParamDao
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:03
 * @Version 1.0
 */
@Dao
interface AppDownloadParamDao {
    @Query("select * from app_download_task")
    fun getAll(): List<AppDownloadTask>

//    @Query("select * from app_download_task where apk_is_installed = 0")
//    fun getAllDownloading(): List<AppDownloadTask>

    @Query("select * from app_download_task where task_id = :taskId")
    fun getByDownloadId(taskId: String): List<AppDownloadTask>

    @Query("select * from app_download_task where apk_package_name = :packageName order by task_update_time desc")
    fun getByPackageName(packageName: String): List<AppDownloadTask>

    //////////////////////////////////////////////////////////

    @Insert
    fun addAll(vararg appDownloadTask: AppDownloadTask)

    @Update
    fun update(appDownloadTask: AppDownloadTask)

    @Delete
    fun delete(appDownloadTask: AppDownloadTask)
}