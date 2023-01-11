package com.mozhimen.componentk.audiok

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.componentk.audiok.commons.IAudioKListener
import com.mozhimen.componentk.audiok.cons.EPlayMode
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import com.mozhimen.componentk.audiok.helpers.AudioKProxy
import com.mozhimen.componentk.audiok.mos.MAudioK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName AudioK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 18:57
 * @Version 1.0
 */
@APermissionKRequire(CPermission.WAKE_LOCK)
class AudioK : IAudioKListener, LifecycleOwner {

    private val _lifecycleRegistry by lazy { LifecycleRegistry(this) }

    private val _audioKProxy by lazy { AudioKProxy(this) }

    companion object {
        @JvmStatic
        val instance = AudioKProvider.holder
    }

    private object AudioKProvider {
        val holder = AudioK()
    }

    init {
        this.lifecycleScope.launch(Dispatchers.Main) {
            _lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }
    }

    override fun getPlayList(): ArrayList<MAudioK> {
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

    override fun getPlayStatus(): EPlayStatus {
        return _audioKProxy.getPlayStatus()
    }

    override fun getPlayMode(): EPlayMode {
        return _audioKProxy.getPlayMode()
    }

    override fun setPlayMode(playMode: EPlayMode) {
        _audioKProxy.setPlayMode(playMode)
    }

    override fun setPlayIndexCurrent(playIndex: Int) {
        _audioKProxy.setPlayIndexCurrent(playIndex)
    }

    override fun getPlayIndexCurrent(): Int {
        return _audioKProxy.getPlayIndexCurrent()
    }

    override fun getPlayIndexNext(): Int {
        return _audioKProxy.getPlayIndexNext()
    }

    override fun getPlayIndexPrevious(): Int {
        return _audioKProxy.getPlayIndexPrevious()
    }

    override fun getAudioFromPlayList(index: Int): MAudioK? {
        return _audioKProxy.getAudioFromPlayList(index)
    }

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

    override fun setVolume(volume: Int) {
        _audioKProxy.setVolume(volume)
    }

    override fun getVolume(): Int {
        return _audioKProxy.getVolume()
    }

    override fun getVolumeMin(): Int {
        return _audioKProxy.getVolumeMin()
    }

    override fun getVolumeMax(): Int {
        return _audioKProxy.getVolumeMax()
    }

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry
    }
}