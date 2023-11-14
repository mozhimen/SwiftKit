package com.mozhimen.componentk.netk.app.task.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState

/**
 * @ClassName AppFileParam
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 18:26
 * @Version 1.0
 */
@Entity(tableName = "netk_app_task")
data class AppTask(
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
    @ColumnInfo(name = "apk_name")
    val apkName: String,//本地保存的名称 为appid.apk或appid.npk
    @ColumnInfo(name = "apk_path_name")
    var apkPathName: String,//本地暂存路径
    @ColumnInfo(name = "apk_is_installed")
    var apkIsInstalled: Boolean,//是否安装0未,1安装
    @ColumnInfo(name = "apk_verify_need")
    var apkVerifyNeed: Boolean,//是否需要检测0,不需要,1需要
    @ColumnInfo(name = "task_update_time")
    var taskUpdateTime: Long = System.currentTimeMillis(),//更新时间
) {
    fun isTaskProcess(): Boolean =
        !apkIsInstalled && CNetKAppTaskState.isTaskProcess(taskState)

    fun isTaskWait(): Boolean =
        !apkIsInstalled && CNetKAppTaskState.isTaskWait(taskState)

    fun isTasking(): Boolean =
        !apkIsInstalled && CNetKAppTaskState.isTasking(taskState)

    fun isTaskPause(): Boolean =
        !apkIsInstalled && CNetKAppTaskState.isTaskPause(taskState)

    fun isTaskCancel(): Boolean =
        !apkIsInstalled && CNetKAppTaskState.isTaskCancel(taskState)

    ////////////////////////////////////////////////////////////

    fun isTaskDownload(): Boolean =
        !apkIsInstalled && CNetKAppState.isTaskDownload(taskState)

    fun isTaskVerify(): Boolean =
        !apkIsInstalled && CNetKAppState.isTaskVerify(taskState)

    fun isTaskUnzip(): Boolean =
        !apkIsInstalled && CNetKAppState.isTaskUnzip(taskState)

    fun isTaskInstall(): Boolean =
        !apkIsInstalled && CNetKAppState.isTaskInstall(taskState)

    ////////////////////////////////////////////////////////////

    fun isDownloading(): Boolean =
        !apkIsInstalled && CNetKAppState.isDownloading(taskState)
}