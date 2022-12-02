package com.mozhimen.componentk.audiok.helpers

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import java.io.FileDescriptor
import java.io.IOException

/**
 * @ClassName CustomMediaPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:08
 * @Version 1.0
 */
class StatusMediaPlayer : MediaPlayer(), MediaPlayer.OnCompletionListener {
    private var _playStatus: EPlayStatus = EPlayStatus.IDLE
    private var _onCompletionListener: OnCompletionListener? = null

    init {
        _playStatus = EPlayStatus.IDLE
        super.setOnCompletionListener(this)
    }

    fun setState(mState: EPlayStatus) {
        _playStatus = mState
    }

    fun getState(): EPlayStatus =
        _playStatus

    fun isComplete(): Boolean =
        _playStatus == EPlayStatus.COMPLETED

    override fun reset() {
        super.reset()
        _playStatus = EPlayStatus.IDLE
    }

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    override fun setDataSource(path: String?) {
        super.setDataSource(path)
        _playStatus = EPlayStatus.INITIALIZED
    }

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    override fun setDataSource(afd: AssetFileDescriptor) {
        super.setDataSource(afd)
        _playStatus = EPlayStatus.INITIALIZED
    }

    override fun setDataSource(fd: FileDescriptor?, offset: Long, length: Long) {
        super.setDataSource(fd, offset, length)
        _playStatus = EPlayStatus.INITIALIZED
    }

    override fun start() {
        super.start()
        _playStatus = EPlayStatus.STARTED
    }

    override fun setOnCompletionListener(listener: OnCompletionListener) {
        _onCompletionListener = listener
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _playStatus = EPlayStatus.COMPLETED
        _onCompletionListener?.onCompletion(mp)
    }

    @Throws(IllegalStateException::class)
    override fun stop() {
        super.stop()
        _playStatus = EPlayStatus.STOPPED
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        super.pause()
        _playStatus = EPlayStatus.PAUSED
    }
}