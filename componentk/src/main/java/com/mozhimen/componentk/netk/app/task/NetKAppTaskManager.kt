package com.mozhimen.componentk.netk.app.task

import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.deleteFolder
import com.mozhimen.componentk.netk.app.task.db.AppTask
import java.io.File

/**
 * @ClassName NetKAppTaskManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/11/14 23:46
 * @Version 1.0
 */
object NetKAppTaskManager {
//    /**
//     * 获取本地保存的文件
//     */
//    private fun getApkSavePathName(appTask: AppTask): File? {
//        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return null
//        return File(externalFilesDir, appTask.apkName)
//    }

    /**
     * 删除Apk文件
     */
    @JvmStatic
    fun deleteFileApk(appTask: AppTask): Boolean {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return true
        File(externalFilesDir, appTask.apkFileName).deleteFile()
        if (appTask.apkFileName.endsWith(".npk")) {//如果是npk,删除解压的文件夹
            File(externalFilesDir, appTask.apkFileName.split(".npk")[0]).deleteFolder()
        }
        return true
    }
}