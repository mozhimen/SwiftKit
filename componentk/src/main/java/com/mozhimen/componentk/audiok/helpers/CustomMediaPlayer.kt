package com.mozhimen.componentk.audiok.helpers

import android.media.MediaPlayer
import com.mozhimen.componentk.audiok.mos.PlayStatus
import java.io.IOException

/**
 * @ClassName CustomMediaPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:08
 * @Version 1.0
 */
class CustomMediaPlayer : MediaPlayer(), MediaPlayer.OnCompletionListener {
    private var _playStatus: PlayStatus = PlayStatus.IDLE
    private var _onCompletionListener: OnCompletionListener? = null

    init {
        _playStatus = PlayStatus.IDLE
        super.setOnCompletionListener(this)
    }

    fun setState(mState: PlayStatus) {
        _playStatus = mState
    }

    fun getState(): PlayStatus =
        _playStatus

    fun isComplete(): Boolean =
        _playStatus == PlayStatus.COMPLETED

    override fun reset() {
        super.reset()
        _playStatus = PlayStatus.IDLE
    }

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    override fun setDataSource(path: String?) {
        super.setDataSource(path)
        _playStatus = PlayStatus.INITIALIZED
    }

    override fun start() {
        super.start()
        _playStatus = PlayStatus.STARTED
    }

    override fun setOnCompletionListener(listener: OnCompletionListener) {
        _onCompletionListener = listener
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _playStatus = PlayStatus.COMPLETED
        _onCompletionListener?.onCompletion(mp)
    }

    @Throws(IllegalStateException::class)
    override fun stop() {
        super.stop()
        _playStatus = PlayStatus.STOPPED
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        super.pause()
        _playStatus = PlayStatus.PAUSED
    }
}