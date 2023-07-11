package com.mozhimen.componentk.mediak.audio

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.componentk.mediak.audio.commons.IMediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.EAudioPlayMode
import com.mozhimen.componentk.mediak.status.cons.EPlayStatus
import com.mozhimen.componentk.mediak.audio.helpers.MediaKAudioDelegate
import com.mozhimen.componentk.mediak.audio.mos.MAudioK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName AudioK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 18:57
 * @Version 1.0
 */
@AManifestKRequire(CPermission.WAKE_LOCK)
class MediaKAudio : IMediaKAudio, LifecycleOwner {

    private val _lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    private val _mediaKAudioDelegate by lazy { MediaKAudioDelegate(this) }

    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    init {
        runOnMainThread {
            _lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }
    }

    ///////////////////////////////////////////////////////////////////////

    override fun getPlayList(): List<MAudioK> {
        return _mediaKAudioDelegate.getPlayList()
    }

    override fun addAudiosToPlayList(audios: List<MAudioK>) {
        _mediaKAudioDelegate.addAudiosToPlayList(audios)
    }

    override fun addAudioToPlayList(audio: MAudioK) {
        _mediaKAudioDelegate.addAudioToPlayList(audio)
    }

    override fun addAudioToPlayListTop(audioK: MAudioK) {
        _mediaKAudioDelegate.addAudioToPlayListTop(audioK)
    }

    override fun clearPLayList() {
        _mediaKAudioDelegate.clearPLayList()
    }

    override fun getPlayMode(): EAudioPlayMode {
        return _mediaKAudioDelegate.getPlayMode()
    }

    override fun setPlayMode(playMode: EAudioPlayMode) {
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

    override fun getAudioFromPlayList(index: Int): MAudioK? {
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
        _lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
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

    override fun getPlayStatus(): EPlayStatus {
        return _mediaKAudioDelegate.getPlayStatus()
    }

    ////////////////////////////////////////////////////////////////////////

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry
    }

    private object INSTANCE {
        val holder = MediaKAudio()
    }
}