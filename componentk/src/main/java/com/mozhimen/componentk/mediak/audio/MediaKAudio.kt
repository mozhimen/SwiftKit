package com.mozhimen.componentk.mediak.audio

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.componentk.mediak.audio.commons.IMediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.EAudioPlayMode
import com.mozhimen.componentk.mediak.status.cons.EPlayStatus
import com.mozhimen.componentk.mediak.audio.helpers.MediaKAudioProxy
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

    private val _lifecycleRegistry by lazy { LifecycleRegistry(this) }

    private val _audioKProxy by lazy { MediaKAudioProxy(this) }

    companion object {
        @JvmStatic
        val instance = AudioKProvider.holder
    }

    private object AudioKProvider {
        val holder = MediaKAudio()
    }

    init {
        this.lifecycleScope.launch(Dispatchers.Main) {
            _lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }
    }

    ///////////////////////////////////////////////////////////////////////

    override fun getPlayList(): List<MAudioK> {
        return _audioKProxy.getPlayList()
    }

    override fun addAudiosToPlayList(audios: List<MAudioK>) {
        _audioKProxy.addAudiosToPlayList(audios)
    }

    override fun addAudioToPlayList(audio: MAudioK) {
        _audioKProxy.addAudioToPlayList(audio)
    }

    override fun addAudioToPlayListTop(audioK: MAudioK) {
        _audioKProxy.addAudioToPlayListTop(audioK)
    }

    override fun clearPLayList() {
        _audioKProxy.clearPLayList()
    }

    override fun getPlayMode(): EAudioPlayMode {
        return _audioKProxy.getPlayMode()
    }

    override fun setPlayMode(playMode: EAudioPlayMode) {
        _audioKProxy.setPlayMode(playMode)
    }

    override fun setPlayPositionCurrent(playIndex: Int) {
        _audioKProxy.setPlayPositionCurrent(playIndex)
    }

    override fun getPlayPositionCurrent(): Int {
        return _audioKProxy.getPlayPositionCurrent()
    }

    override fun getPlayPositionNext(): Int {
        return _audioKProxy.getPlayPositionNext()
    }

    override fun getPlayPositionPrevious(): Int {
        return _audioKProxy.getPlayPositionPrevious()
    }

    override fun getAudioFromPlayList(index: Int): MAudioK? {
        return _audioKProxy.getAudioFromPlayList(index)
    }

    ///////////////////////////////////////////////////////////////////////

    override fun play() {
        _audioKProxy.play()
    }

    override fun playNext() {
        _audioKProxy.playNext()
    }

    override fun playPrevious() {
        _audioKProxy.playPrevious()
    }

    override fun pause() {
        _audioKProxy.pause()
    }

    protected fun finalize() {
        _lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        release()
    }

    override fun release() {
        _audioKProxy.release()
    }

    ///////////////////////////////////////////////////////////////////////

    override fun setVolume(volume: Int) {
        _audioKProxy.setVolume(volume)
    }

    override fun setVolumePercent(volumePercent: Float) {
        _audioKProxy.setVolumePercent(volumePercent)
    }

    override fun getVolumeCurrent(): Int {
        return _audioKProxy.getVolumeCurrent()
    }

    override fun getVolumeMin(): Int {
        return _audioKProxy.getVolumeMin()
    }

    override fun getVolumeMax(): Int {
        return _audioKProxy.getVolumeMax()
    }

    override fun getVolumeInterval(): Int {
        return _audioKProxy.getVolumeInterval()
    }

    ///////////////////////////////////////////////////////////////////////

    override fun getPlayStatus(): EPlayStatus {
        return _audioKProxy.getPlayStatus()
    }

    ////////////////////////////////////////////////////////////////////////

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry
    }
}