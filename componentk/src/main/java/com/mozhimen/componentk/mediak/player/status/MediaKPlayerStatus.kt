package com.mozhimen.componentk.mediak.player.status

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.mozhimen.componentk.mediak.player.status.commons.IMediaKStatusPLayer
import com.mozhimen.componentk.mediak.player.status.cons.EMediaKPlayerStatus
import java.io.FileDescriptor
import java.io.IOException

/**
 * @ClassName CustomMediaPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:08
 * @Version 1.0
 */
class MediaKPlayerStatus : MediaPlayer(), MediaPlayer.OnCompletionListener, IMediaKStatusPLayer {
    private var _playStatus: EMediaKPlayerStatus = EMediaKPlayerStatus.IDLE
    private var _onCompletionListener: OnCompletionListener? = null

    init {
        _playStatus = EMediaKPlayerStatus.IDLE
        super.setOnCompletionListener(this)
    }

    override fun setPlayStatus(status: EMediaKPlayerStatus) {
        _playStatus = status
    }

    override fun getPlayStatus(): EMediaKPlayerStatus =
        _playStatus

    override fun isPlayComplete(): Boolean =
        _playStatus == EMediaKPlayerStatus.COMPLETED

    override fun reset() {
        super.reset()
        _playStatus = EMediaKPlayerStatus.IDLE
    }

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    override fun setDataSource(path: String?) {
        super.setDataSource(path)
        _playStatus = EMediaKPlayerStatus.INITIALIZED
    }

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    override fun setDataSource(afd: AssetFileDescriptor) {
        super.setDataSource(afd)
        _playStatus = EMediaKPlayerStatus.INITIALIZED
    }

    override fun setDataSource(fd: FileDescriptor?, offset: Long, length: Long) {
        super.setDataSource(fd, offset, length)
        _playStatus = EMediaKPlayerStatus.INITIALIZED
    }

    override fun start() {
        super.start()
        _playStatus = EMediaKPlayerStatus.STARTED
    }

    override fun setOnCompletionListener(listener: OnCompletionListener) {
        _onCompletionListener = listener
    }

    override fun onCompletion(mp: MediaPlayer?) {
        _playStatus = EMediaKPlayerStatus.COMPLETED
        _onCompletionListener?.onCompletion(mp)
    }

    @Throws(IllegalStateException::class)
    override fun stop() {
        super.stop()
        _playStatus = EMediaKPlayerStatus.STOPPED
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        super.pause()
        _playStatus = EMediaKPlayerStatus.PAUSED
    }
}