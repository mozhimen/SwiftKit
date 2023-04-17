package com.mozhimen.basick.utilk.os

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
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
@AManifestKRequire(CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE)
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

    @RequiresApi(Build.VERSION_CODES.R)
    @JvmStatic
    fun getStorageDir(): File =
        Environment.getStorageDirectory()

    @JvmStatic
    fun getExternalStorageDir(): File =
        Environment.getExternalStorageDirectory()

    @JvmStatic
    fun getExternalStoragePublicDir(): File=
        Environment.getExternalStoragePublicDirectory(null)

    @JvmStatic
    fun getExternalStoragePublicAlarmsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)

    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun getExternalStoragePublicScreenshotsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS)

    @RequiresApi(Build.VERSION_CODES.S)
    @JvmStatic
    fun getExternalStoragePublicRecordingsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS)

    @JvmStatic
    fun getExternalStoragePublicPodcastsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)

    @JvmStatic
    fun getExternalStoragePublicPicturesDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    @JvmStatic
    fun getExternalStoragePublicNotificationsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)

    @JvmStatic
    fun getExternalStoragePublicMusicDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    @JvmStatic
    fun getExternalStoragePublicMoviesDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

    @JvmStatic
    fun getExternalStoragePublicDownloadsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    @JvmStatic
    fun getExternalStoragePublicDocumentsDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    @JvmStatic
    fun getExternalStoragePublicDCIMDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun getExternalStoragePublicAudiobooksDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_AUDIOBOOKS)

    @JvmStatic
    fun getExternalStoragePublicRingtonesDir(): File=
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)

    @JvmStatic
    fun isExternalStorageMounted(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

//    @JvmStatic
//    fun getExternalStorageAbsolutePath(): String =
//        getExternalStorage().absolutePath
//
//    @JvmStatic
//    fun getExternalStoragePath(): String =
//        getExternalStorage().path
//
//    @JvmStatic
//    fun getDataAbsolutePath(): String =
//        getData().absolutePath
//
//    @JvmStatic
//    fun getDataPath(): String =
//        getData().path
}