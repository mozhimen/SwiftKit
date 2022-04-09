package com.mozhimen.basicsk.utilk

import android.media.MediaPlayer

/**
 * @ClassName UtilKAudio
 * @Description 你可以将MP3或wav音频文件放在R.raw目录下
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 13:04
 * @Version 1.0
 */
object UtilKAudio {

    private var audioPlayer: MediaPlayer? = null

    private val completionListener = MediaPlayer.OnCompletionListener {
        releaseMediaPlayer()
    }

    fun play(resId: Int) {
        if (audioPlayer == null) {
            audioPlayer = MediaPlayer.create(UtilKGlobal.instance.getApp(), resId)
        }
        audioPlayer!!.setOnCompletionListener(completionListener)
        audioPlayer!!.start()
    }

    private fun releaseMediaPlayer() {
        audioPlayer?.let {
            audioPlayer!!.release()
            audioPlayer = null
        }
    }
}