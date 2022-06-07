package com.mozhimen.basick.utilk

import android.media.MediaPlayer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * @ClassName UtilKAudio
 * @Description 你可以将MP3或wav音频文件放在R.raw目录下
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 13:04
 * @Version 1.0
 */
class UtilKAudio(owner: LifecycleOwner) : DefaultLifecycleObserver {

    companion object {
        fun getInstance(owner: LifecycleOwner) =
            instance ?: synchronized(this) {
                instance ?: UtilKAudio(owner).also { instance = it }
            }

        @Volatile
        private var instance: UtilKAudio? = null
    }

    @Volatile
    private var _audioWorker: AudioWorker
    private val _executors = Executors.newSingleThreadExecutor()

    init {
        owner.lifecycle.addObserver(this)
        _audioWorker = AudioWorker()
        _audioWorker.start()
    }

    /**
     * 播放
     * @param resId Int
     */
    fun play(resId: Int) {
        val audioPlayer = MediaPlayer.create(UtilKGlobal.instance.getApp(), resId)
        play(audioPlayer)
    }

    /**
     * 播放
     * @param audio MediaPlayer
     */
    fun play(audio: MediaPlayer) {
        if (!_audioWorker.isRunning()) {
            _audioWorker.start()
        }
        _audioWorker.put(audio)
    }

    private inner class AudioWorker : Runnable {
        private var _audioQueue = LinkedBlockingQueue<MediaPlayer>()

        @Volatile
        private var _isRunning = false


        override fun run() {
            var audioPlayer: MediaPlayer
            var isPlaying = false
            try {
                while (true) {
                    if (!isPlaying) {
                        isPlaying = true
                        audioPlayer = _audioQueue.take()
                        audioPlayer.setOnCompletionListener {
                            it.release()
                            isPlaying = false
                        }
                        audioPlayer.start()
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) {
                    _isRunning = false
                }
            }
        }

        fun start() {
            synchronized(this) {
                _executors.execute(this)
                _isRunning = true
            }
        }

        fun stop() {
            if (!_executors.isShutdown) {
                _executors.shutdown()
            }
            _audioQueue.clear()
            _isRunning = false
        }

        fun put(audio: MediaPlayer) {
            try {
                _audioQueue.put(audio)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        fun isRunning(): Boolean {
            synchronized(this) {
                return _isRunning
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _audioWorker.stop()
    }
}