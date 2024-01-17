package com.mozhimen.componentk.mediak.audio

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.componentk.mediak.audio.commons.IMediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.EMediaKAudioPlayMode
import com.mozhimen.componentk.mediak.player.status.cons.EMediaKPlayerStatus
import com.mozhimen.componentk.mediak.audio.helpers.MediaKAudioDelegate
import com.mozhimen.componentk.mediak.audio.mos.MAudioKInfo

/**
 * @ClassName AudioK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 18:57
 * @Version 1.0
 */
@AManifestKRequire(CPermission.WAKE_LOCK)
class MediaKAudio : IMediaKAudio, LifecycleOwner {

    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////////////////

    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    private val _mediaKAudioDelegate by lazy { MediaKAudioDelegate(this) }

    init {
        runOnMainThread {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    ///////////////////////////////////////////////////////////////////////

    override fun getPlayList(): List<MAudioKInfo> {
        return _mediaKAudioDelegate.getPlayList()
    }

    override fun addAudiosToPlayList(audios: List<MAudioKInfo>) {
        _mediaKAudioDelegate.addAudiosToPlayList(audios)
    }

    override fun addAudioToPlayList(audio: MAudioKInfo) {
        _mediaKAudioDelegate.addAudioToPlayList(audio)
    }

    override fun addAudioToPlayListTop(audioK: MAudioKInfo) {
        _mediaKAudioDelegate.addAudioToPlayListTop(audioK)
    }

    override fun clearPLayList() {
        _mediaKAudioDelegate.clearPLayList()
    }

    override fun getPlayMode(): EMediaKAudioPlayMode {
        return _mediaKAudioDelegate.getPlayMode()
    }

    override fun setPlayMode(playMode: EMediaKAudioPlayMode) {
        _mediaKAudioDelegate.setPlayMode(playMode)
    }

    override fun setPlayPositionCurrent(playIndex: Int) {
        _mediaKAudioDelegate.setPlayPositionCurrent(playIndex)
    }

    override fun getPlayPositionCurrent(): Int {
        return _mediaKAudioDelegate.getPlayPositionCurrent()
    }

    override fun getPlayPositionNext(): Int {
        return _mediaKAudioDelegate.getPlayPositionNext()
    }

    override fun getPlayPositionPrevious(): Int {
        return _mediaKAudioDelegate.getPlayPositionPrevious()
    }

    override fun getAudioFromPlayList(index: Int): MAudioKInfo? {
        return _mediaKAudioDelegate.getAudioFromPlayList(index)
    }

    ///////////////////////////////////////////////////////////////////////

    override fun play() {
        _mediaKAudioDelegate.play()
    }

    override fun playNext() {
        _mediaKAudioDelegate.playNext()
    }

    override fun playPrevious() {
        _mediaKAudioDelegate.playPrevious()
    }

    override fun pause() {
        _mediaKAudioDelegate.pause()
    }

    protected fun finalize() {
        release()
    }

    override fun release() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        _mediaKAudioDelegate.release()
    }

    ///////////////////////////////////////////////////////////////////////

    override fun setVolume(volume: Int) {
        _mediaKAudioDelegate.setVolume(volume)
    }

    override fun setVolumePercent(volumePercent: Float) {
        _mediaKAudioDelegate.setVolumePercent(volumePercent)
    }

    override fun getVolumeCurrent(): Int {
        return _mediaKAudioDelegate.getVolumeCurrent()
    }

    override fun getVolumeMin(): Int {
        return _mediaKAudioDelegate.getVolumeMin()
    }

    override fun getVolumeMax(): Int {
        return _mediaKAudioDelegate.getVolumeMax()
    }

    override fun getVolumeInterval(): Int {
        return _mediaKAudioDelegate.getVolumeInterval()
    }

    ///////////////////////////////////////////////////////////////////////

    override fun getPlayStatus(): EMediaKPlayerStatus {
        return _mediaKAudioDelegate.getPlayStatus()
    }

    ////////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    ////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = MediaKAudio()
    }
}