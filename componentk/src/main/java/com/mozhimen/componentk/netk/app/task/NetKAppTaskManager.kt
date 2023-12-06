package com.mozhimen.componentk.netk.app.task

import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.datastore.temps.CacheKDSVarPropertyBoolean
import com.mozhimen.basick.utilk.bases.IUtilK
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
internal object NetKAppTaskManager : IUtilK {
    private val _cacheKDSProvider by lazy { CacheKDS.instance.with(NAME) }
    var isDeleteApkFile by CacheKDSVarPropertyBoolean(_cacheKDSProvider, "is_delete_apk_file", false)

//    /**
//     * 获取本地保存的文件
//     */
//    private fun getApkSavePathName(appTask: AppTask): File? {
//        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return null
//        return File(externalFilesDir, appTask.apkName)
//    }
}