package com.mozhimen.componentk.netk.app.download.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mozhimen.componentk.netk.app.download.cons.CAppDownloadState

/**
 * @ClassName AppFileParam
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 18:26
 * @Version 1.0
 */
@Entity(tableName = "app_download_task")
data class AppDownloadTask(
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    val taskId: String,//主键
    @ColumnInfo(name = "task_state")
    var taskState: Int,//下载状态
    @ColumnInfo(name = "download_url")
    val downloadUrl: String,//内部下载地址
    @ColumnInfo(name = "download_url_outside")
    val downloadUrlOutSide: String,//外部下载地址
    @ColumnInfo(name = "download_url_current")
    var downloadUrlCurrent: String,//当前使用的下载地址
    @ColumnInfo(name = "download_progress")
    var downloadProgress: Int,//下载进度
    @ColumnInfo(name = "apk_file_size")
    var apkFileSize: Long,//软件大小
    @ColumnInfo(name = "apk_file_md5")
    val apkFileMd5: String,//文件的MD5值
    @ColumnInfo(name = "apk_package_name")
    val apkPackageName: String,//包名
    @ColumnInfo(name = "apk_save_name")
    val apkSaveName: String,//本地保存的名称 为appid.apk或appid.npk
    @ColumnInfo(name = "apk_is_installed")
    var apkIsInstalled: Boolean,//是否安装0未,1安装
    @ColumnInfo(name = "apk_verify_need")
    var apkVerifyNeed: Boolean,//是否需要检测0,不需要,1需要
    @ColumnInfo(name = "task_update_time")
    var taskUpdateTime: Long = System.currentTimeMillis(),//更新时间
) {
    fun isDownloading(): Boolean =
        !apkIsInstalled && CAppDownloadState.isDownloading(taskState)

    fun isVerifying(): Boolean =
        !apkIsInstalled && CAppDownloadState.isVerifying(taskState)

    fun isUnziping(): Boolean =
        !apkIsInstalled && CAppDownloadState.isUnziping(taskState)

    fun isInstalling(): Boolean =
        !apkIsInstalled && CAppDownloadState.isInstalling(taskState)
}