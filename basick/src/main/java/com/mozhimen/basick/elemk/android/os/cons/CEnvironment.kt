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

    @RequiresApi(CVersCode.V_29_10_Q)
    val DIRECTORY_SCREENSHOTS = Environment.DIRECTORY_SCREENSHOTS

    @RequiresApi(CVersCode.V_29_10_Q)
    val DIRECTORY_AUDIOBOOKS = Environment.DIRECTORY_AUDIOBOOKS

    @RequiresApi(CVersCode.V_31_11_S)
    val DIRECTORY_RECORDINGS = Environment.DIRECTORY_RECORDINGS
}