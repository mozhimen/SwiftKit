package com.mozhimen.swiftmk.helper.os

import android.app.Activity
import android.media.MediaPlayer
import android.util.Log
import com.mozhimen.swiftmk.R

/**
 * @ClassName MediaPlayer
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/25 16:52
 * @Version 1.0
 */
object MediaPlayerMK {
    private lateinit var audioPlayer: MediaPlayer

    fun audioPlayer(activity: Activity, resId: Int = R.raw.success) {
        audioPlayer = MediaPlayer.create(activity, resId)
        audioPlayer.start()
    }
}