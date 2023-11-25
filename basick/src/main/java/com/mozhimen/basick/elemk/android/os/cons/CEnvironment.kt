package com.mozhimen.basick.elemk.android.os.cons

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi

/**
 * @ClassName CEnvironment
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/8 18:33
 * @Version 1.0
 */
object CEnvironment {
    val DIRECTORY_MUSIC = Environment.DIRECTORY_MUSIC
    val DIRECTORY_PODCASTS = Environment.DIRECTORY_PODCASTS
    val DIRECTORY_RINGTONES = Environment.DIRECTORY_RINGTONES
    val DIRECTORY_ALARMS = Environment.DIRECTORY_ALARMS
    val DIRECTORY_NOTIFICATIONS = Environment.DIRECTORY_NOTIFICATIONS
    val DIRECTORY_PICTURES = Environment.DIRECTORY_PICTURES
    val DIRECTORY_MOVIES = Environment.DIRECTORY_MOVIES
    val DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS
    val DIRECTORY_DCIM = Environment.DIRECTORY_DCIM
    val DIRECTORY_DOCUMENTS = Environment.DIRECTORY_DOCUMENTS
    val MEDIA_MOUNTED = Environment.MEDIA_MOUNTED

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getDirectoryScreenShots(): String =
        Environment.DIRECTORY_SCREENSHOTS

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getDirectoryAudioBooks(): String =
        Environment.DIRECTORY_AUDIOBOOKS

    @JvmStatic
    @RequiresApi(CVersCode.V_31_11_S)
    fun getDirectoryRecordings(): String =
        Environment.DIRECTORY_RECORDINGS
}