package com.mozhimen.basick.utilk.android.os

import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CEnvironment
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.manifestk.cons.CPermission
import java.io.File


/**
 * @ClassName UtilKEnvironment
 * @Description 读写需要申请权限
 * 外部程序可以访问。卸载应用不会删除。
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 17:04
 * @Version 1.0
 */

object UtilKEnvironment {

    @JvmStatic
    fun getDataDir(): File =
        Environment.getDataDirectory()

    @JvmStatic
    fun getRootDir(): File =
        Environment.getRootDirectory()

    @JvmStatic
    fun getDownloadCacheDir(): File =
        Environment.getDownloadCacheDirectory()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getStorageDir(): File =
        Environment.getStorageDirectory()

    @JvmStatic
    fun getExternalStorageDir(): File =
        Environment.getExternalStorageDirectory()

    @JvmStatic
    fun getExternalStoragePublicDir(): File =
        Environment.getExternalStoragePublicDirectory(null)

    @JvmStatic
    fun getExternalStoragePublicDir_ofAlarms(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_ALARMS)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getExternalStoragePublicDir_ofScreenshots(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryScreenShots())

    @JvmStatic
    @RequiresApi(CVersCode.V_31_12_S)
    fun getExternalStoragePublicDir_ofRecordings(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryRecordings())

    @JvmStatic
    fun getExternalStoragePublicDir_ofPodcasts(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_PODCASTS)

    @JvmStatic
    fun getExternalStoragePublicDir_ofPictures(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_PICTURES)

    @JvmStatic
    fun getExternalStoragePublicDir_ofNotifications(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_NOTIFICATIONS)

    @JvmStatic
    fun getExternalStoragePublicDir_ofMusic(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_MUSIC)

    @JvmStatic
    fun getExternalStoragePublicDir_ofMovies(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_MOVIES)

    @JvmStatic
    fun getExternalStoragePublicDir_ofDownloads(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DOWNLOADS)

    @JvmStatic
    fun getExternalStoragePublicDir_ofDocuments(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DOCUMENTS)

    @JvmStatic
    fun getExternalStoragePublicDir_ofDCIM(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DCIM)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getExternalStoragePublicDir_ofAudiobooks(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryAudioBooks())

    @JvmStatic
    fun getExternalStoragePublicDir_ofRingtones(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_RINGTONES)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isExternalStorageStateMounted(): Boolean =
        Environment.getExternalStorageState() == CEnvironment.MEDIA_MOUNTED

    @JvmStatic
    @OPermission_MANAGE_EXTERNAL_STORAGE
    @OPermission_READ_EXTERNAL_STORAGE
    @RequiresPermission(allOf = [CPermission.READ_EXTERNAL_STORAGE, CPermission.MANAGE_EXTERNAL_STORAGE])
    @ADescription(CSettings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
    @RequiresApi(CVersCode.V_30_11_R)
    fun isExternalStorageManager(): Boolean =
        Environment.isExternalStorageManager()
}