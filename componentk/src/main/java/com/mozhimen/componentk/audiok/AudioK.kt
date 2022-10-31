package com.mozhimen.componentk.audiok

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mozhimen.componentk.audiok.commons.IAudioKListener
import com.mozhimen.componentk.audiok.cons.EPlayMode
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import com.mozhimen.componentk.audiok.mos.MAudioK

/**
 * @ClassName AudioK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 18:57
 * @Version 1.0
 */
class AudioK : IAudioKListener, LifecycleOwner {

    private val _lifecycleRegistry by lazy { LifecycleRegistry(this) }

    private val _audioKProxy by lazy { AudioKProxy(this) }

//    fun addAudios(audios: List<MAudioK>) {
//        _playList.addAll(_playList)
//    }
//
//    fun addAudios(audios: List<MAudioK>, playIndex: Int) {
//        addAudios(audios)
//        _currentPlayIndex = playIndex
//    }
//
//    fun addAudio(bean: MAudioK) {
//        val index = indexOf(bean)
//        if (index < 0) {
//            _playList.add(playIndex, bean)
//            setPlayIndex(playIndex)
//        } else {
//            val currentAudio = getCurrentAudio()
//            if (currentAudio != null && currentAudio.id != bean.id) {
//                //添加过且不是当前播放，播，否则什么也不干
//                setPlayIndex(index)
//            }
//        }
//    }
//
//    fun addAudio(bean: MAudioK, playIndex: Int) {
//        val index = indexOf(bean)
//        if (index < 0) {
//            _playList.add(playIndex, bean)
//            setPlayIndex(playIndex)
//        } else {
//            val currentAudio = getCurrentAudio()
//            if (currentAudio != null && currentAudio.id != bean.id) {
//                //添加过且不是当前播放，播，否则什么也不干
//                setPlayIndex(index)
//            }
//        }
//    }
//
//    fun setPlayIndex(playIndex: Int) {
//        _currentPlayIndex = playIndex
//        play()
//    }
//
//    fun getPlayMode(): EPlayMode =
//        _playMode
//
//    fun setPlayMode(playMode: EPlayMode) {
//        _playMode = playMode
//    }
//
//    fun getCurrentPlayIndex(): Int =
//        _currentPlayIndex
//
//    fun getCurrentAudio(): MAudioK? =
//        getAudio(_currentPlayIndex)
//
//    fun playOrPause() {
//        if (getPlayStatus() == EPlayStatus.STARTED) {
//            pause()
//        } else if (getPlayStatus() == EPlayStatus.STOPPED) {
//            resume()
//        }
//    }
//
//    fun play() {
//        getCurrentAudio()?.let {
//            loadAudio(it)
//        }
//    }
//
//    fun next() {
//        getNextAudio()?.let {
//            loadAudio(it)
//        }
//    }
//
//    fun getNowPlayTime(): Int =
//        _customAudioPlayer.getCurrentPosition()
//
//    fun getDuration(): Int =
//        _customAudioPlayer.getDuration()
//
//    fun resume() {
//        _customAudioPlayer.resume()
//    }
//
//    fun pause() {
//        _customAudioPlayer.pause()
//    }
//
//    fun release() {
//        _customAudioPlayer.release()
//    }
//
//    fun previous() {
//        getPreviousAudio()?.let {
//            loadAudio(it)
//        }
//    }
//
//    private fun indexOf(bean: MAudioK): Int =
//        _playList.indexOf(bean)
//
//    private fun loadAudio(bean: MAudioK) {
//        _customAudioPlayer.load(bean.url)
//    }
//
//    private fun getNextAudio(): MAudioK? =
//        when (_playMode) {
//            EPlayMode.LOOP -> {
//                _currentPlayIndex = (_currentPlayIndex + 1) % _playList.size
//                getAudio(_currentPlayIndex)
//            }
//            EPlayMode.RANDOM -> {
//                _currentPlayIndex = (0 until _playList.size).random() % _playList.size
//                getAudio(_currentPlayIndex)
//            }
//            EPlayMode.REPEAT -> {
//                getAudio(_currentPlayIndex)
//            }
//        }
//
//    private fun getPreviousAudio(): MAudioK? =
//        when (_playMode) {
//            EPlayMode.LOOP -> {
//                _currentPlayIndex = (_currentPlayIndex + _playList.size - 1) % _playList.size
//                getAudio(_currentPlayIndex)
//            }
//            EPlayMode.RANDOM -> {
//                _currentPlayIndex = (0 until _playList.size).random() % _playList.size
//                getAudio(_currentPlayIndex)
//            }
//            EPlayMode.REPEAT -> {
//                getAudio(_currentPlayIndex)
//            }
//        }
//
//    private fun getAudio(index: Int): MAudioK? =
//        if (_playList.isNotEmpty() && index >= 0 && index < _playList.size) {
//            _playList[index]
//        } else null

    companion object {
        @JvmStatic
        val instance = AudioKProvider.holder
    }

    private object AudioKProvider {
        val holder = AudioK()
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
        release()
    }

    override fun release() {
        _lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        _audioKProxy.release()
    }

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry
    }
}