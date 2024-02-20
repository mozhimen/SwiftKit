package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CEnvironment
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_WRITE_EXTERNAL_STORAGE
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
    fun getExternalStoragePublicDirAlarms(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_ALARMS)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getExternalStoragePublicDirScreenshots(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryScreenShots())

    @JvmStatic
    @RequiresApi(CVersCode.V_31_12_S)
    fun getExternalStoragePublicDirRecordings(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryRecordings())

    @JvmStatic
    fun getExternalStoragePublicDirPodcasts(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_PODCASTS)

    @JvmStatic
    fun getExternalStoragePublicDirPictures(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_PICTURES)

    @JvmStatic
    fun getExternalStoragePublicDirNotifications(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_NOTIFICATIONS)

    @JvmStatic
    fun getExternalStoragePublicDirMusic(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_MUSIC)

    @JvmStatic
    fun getExternalStoragePublicDirMovies(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_MOVIES)

    @JvmStatic
    fun getExternalStoragePublicDirDownloads(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DOWNLOADS)

    @JvmStatic
    fun getExternalStoragePublicDirDocuments(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DOCUMENTS)

    @JvmStatic
    fun getExternalStoragePublicDirDCIM(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_DCIM)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getExternalStoragePublicDirAudiobooks(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.getDirectoryAudioBooks())

    @JvmStatic
    fun getExternalStoragePublicDirRingtones(): File =
        Environment.getExternalStoragePublicDirectory(CEnvironment.DIRECTORY_RINGTONES)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isExternalStorageMounted(): Boolean =
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