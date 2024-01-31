package com.mozhimen.basick.utilk.android.content

import android.content.Context
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CEnvironment
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.File

/**
 * @ClassName UtilKContextDir
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/17 13:09
 * @Version 1.0
 */
object UtilKContextDir {

    /**
     * 无需申请权限
     * 内部使用，外部程序无法访问。卸载应用时删除。
     */
    object Internal {
        /**
         * 系统空间不足时可能会删除
         */
        @JvmStatic
        fun getCacheDir(context: Context): File =
            UtilKContext.getCacheDir(context)

        @JvmStatic
        fun getFilesDir(context: Context): File =
            UtilKContext.getFilesDir(context)

        @JvmStatic
        fun getDataDir(context: Context): File =
            if (UtilKBuildVersion.isAfterV_24_7_N()) UtilKContext.getDataDir(context)
            else UtilKApplicationInfo.getOfPackageInfo(context)!!.dataDir.strFilePath2file()

        @JvmStatic
        fun getObbDir(context: Context): File =
            UtilKContext.getObbDir(context)

        @JvmStatic
        fun getObbDirs(context: Context): Array<File>? =
            UtilKContext.getObbDirs(context)

        @RequiresApi(CVersCode.V_21_5_L)
        @JvmStatic
        fun getCodeCacheDir(context: Context): File =
            UtilKContext.getCodeCacheDir(context)

        @RequiresApi(CVersCode.V_21_5_L)
        @JvmStatic
        fun getNoBackupFilesDir(context: Context): File =
            UtilKContext.getNoBackupFilesDir(context)

        @JvmStatic
        fun getFileStreamDir(context: Context, name: String): File =
            UtilKContext.getFileStreamDir(context, name)

        /**
         * 内部使用，外部程序无法访问。主要是 SQLite 数据库的目录
         */
        @JvmStatic
        fun getDatabaseDir(context: Context, name: String): File =
            UtilKContext.getDatabasePath(context, name)

        @JvmStatic
        fun getPackageCodeDir(context: Context): String =
            UtilKContext.getPackageCodePath(context)

        @JvmStatic
        fun getPackageResourceDir(context: Context): String =
            UtilKContext.getPackageResourcePath(context)
    }

    /**
     * 读写需要申请权限
     * 外部程序可以访问。卸载应用可能会删除。
     */
    @AManifestKRequire(CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE)
    object External {

        @JvmStatic
        fun getCacheDir(context: Context): File? =
            UtilKContext.getExternalCacheDir(context)

        @JvmStatic
        fun getFilesRootDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, null)

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getFilesMusicDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_MUSIC)

        @JvmStatic
        fun getFilesPodcastsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_PODCASTS)

        @JvmStatic
        fun getFilesRingtonesDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_RINGTONES)

        @JvmStatic
        fun getFilesAlarmsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_ALARMS)

        @JvmStatic
        fun getFilesNotificationsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_NOTIFICATIONS)

        @JvmStatic
        fun getFilesPicturesDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_PICTURES)

        @JvmStatic
        fun getFilesMoviesDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_MOVIES)

        @JvmStatic
        fun getFilesDownloadsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_DOWNLOADS)

        @JvmStatic
        fun getFilesDCIMDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_DCIM)

        @JvmStatic
        fun getFilesDocumentsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.DIRECTORY_DOCUMENTS)

        @JvmStatic
        @RequiresApi(CVersCode.V_29_10_Q)
        fun getFilesScreenshotsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.getDirectoryScreenShots())

        @JvmStatic
        @RequiresApi(CVersCode.V_29_10_Q)
        fun getFilesAudiobooksDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.getDirectoryAudioBooks())

        @JvmStatic
        @RequiresApi(CVersCode.V_31_12_S)
        fun getFilesRecordingsDir(context: Context): File? =
            UtilKContext.getExternalFilesDir(context, CEnvironment.getDirectoryRecordings())
    }
}