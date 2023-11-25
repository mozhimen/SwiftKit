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
    @JvmStatic
    fun getDir(context: Context, name: String, mode: Int): File =
        context.getDir(name, mode)

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
            context.cacheDir

        @JvmStatic
        fun getFilesDir(context: Context): File =
            context.filesDir

        @JvmStatic
        fun getDataDir(context: Context): File =
            if (UtilKBuildVersion.isAfterV_24_7_N()) context.dataDir
            else UtilKApplicationInfo.get(context)!!.dataDir.strFilePath2file()

        @JvmStatic
        fun getObbDir(context: Context): File =
            context.obbDir

        @JvmStatic
        fun getObbDirs(context: Context): Array<File>? =
            context.obbDirs

        @JvmStatic
        fun getCodeCacheDir(context: Context): File =
            context.codeCacheDir

        @JvmStatic
        fun getNoBackupFilesDir(context: Context): File =
            context.noBackupFilesDir

        @JvmStatic
        fun getFileStreamDir(context: Context, name: String): File =
            context.getFileStreamPath(name)

        /**
         * 内部使用，外部程序无法访问。主要是 SQLite 数据库的目录
         */
        @JvmStatic
        fun getDatabaseDir(context: Context, name: String): File =
            context.getDatabasePath(name)

        @JvmStatic
        fun getPackageCodePath(context: Context): String =
            context.packageCodePath

        @JvmStatic
        fun getPackageResourcePath(context: Context): String =
            context.packageResourcePath
    }

    /**
     * 读写需要申请权限
     * 外部程序可以访问。卸载应用可能会删除。
     */
    @AManifestKRequire(CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE)
    object External {

        @JvmStatic
        fun getCacheDir(context: Context): File? =
            context.externalCacheDir

        @JvmStatic
        fun getFilesRootDir(context: Context): File? =
            context.getExternalFilesDir(null)

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getFilesMusicDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_MUSIC)

        @JvmStatic
        fun getFilesPodcastsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_PODCASTS)

        @JvmStatic
        fun getFilesRingtonesDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_RINGTONES)

        @JvmStatic
        fun getFilesAlarmsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_ALARMS)

        @JvmStatic
        fun getFilesNotificationsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_NOTIFICATIONS)

        @JvmStatic
        fun getFilesPicturesDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_PICTURES)

        @JvmStatic
        fun getFilesMoviesDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_MOVIES)

        @JvmStatic
        fun getFilesDownloadsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_DOWNLOADS)

        @JvmStatic
        fun getFilesDCIMDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_DCIM)

        @JvmStatic
        fun getFilesDocumentsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.DIRECTORY_DOCUMENTS)

        @JvmStatic
        @RequiresApi(CVersCode.V_29_10_Q)
        fun getFilesScreenshotsDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.getDirectoryScreenShots())

        @JvmStatic
        @RequiresApi(CVersCode.V_29_10_Q)
        fun getFilesAudiobooksDir(context: Context): File? =
            context.getExternalFilesDir(CEnvironment.getDirectoryAudioBooks())

//        @JvmStatic
//        @RequiresApi(CVersCode.V_31_11_S)
//        fun getFilesRecordingsDir(context: Context): File? =
//            context.getExternalFilesDir(CEnvironment.DIRECTORY_RECORDINGS)
    }
}