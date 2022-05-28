package com.mozhimen.basick.utilk

import android.media.MediaPlayer

/**
 * @ClassName UtilKAudio
 * @Description 你可以将MP3或wav音频文件放在R.raw目录下
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 13:04
 * @Version 1.0
 */
object UtilKAudio {

    private var _audioPlayer: MediaPlayer? = null

    private val _completionListener = MediaPlayer.OnCompletionListener {
        releaseMediaPlayer()
    }

    /**
     * 播放
     * @param resId Int
     */
    fun play(resId: Int) {
        if (_audioPlayer == null) {
            _audioPlayer = MediaPlayer.create(UtilKGlobal.instance.getApp(), resId)
        }
        _audioPlayer!!.setOnCompletionListener(_completionListener)
        _audioPlayer!!.start()
    }

    private fun releaseMediaPlayer() {
        _audioPlayer?.let {
            _audioPlayer!!.release()
            _audioPlayer = null
        }
    }
}