package com.mozhimen.componentk.netk.app.download.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @ClassName AppFileParam
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 18:26
 * @Version 1.0
 */
@Entity(tableName = "download_file_param")
data class AppDownloadParam(
    @PrimaryKey
    @ColumnInfo(name = "download_id")
    val downloadId: String,//主键
    @ColumnInfo(name = "download_url")
    val downloadUrl: String,//内部下载地址
    @ColumnInfo(name = "outside_download_url")
    val outsideDownloadUrl: String,//外部下载地址
    @ColumnInfo(name = "file_size")
    var fileSize: Long,//软件大小
    @ColumnInfo(name = "file_md5")
    val fileMd5: String,//文件的MD5值
    @ColumnInfo(name = "package_name")
    val packageName: String,//包名
    @ColumnInfo(name = "save_name")
    val saveName: String,//本地保存的名称 为appid.apk或appid.npk
    @ColumnInfo(name = "current_download_url")
    var currentDownloadUrl: String,//当前使用的下载地址
    @ColumnInfo(name = "download_state")
    var downloadState: Int,//下载状态
    @ColumnInfo(name = "download_progress")
    var downloadProgress: Int,//下载进度
    @ColumnInfo(name = "installed")
    var installed: Int,//是否安装
    @ColumnInfo(name = "need_checking")
    var needChecking: Int,//是否需要检测
    @ColumnInfo(name = "update_time")
    var updateTime: Long = System.currentTimeMillis(),//更新时间
)