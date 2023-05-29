package com.mozhimen.componentk.mediak.audio.helpers

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKDataBus
import com.mozhimen.componentk.mediak.audio.commons.IMediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.CAudioEvent
import com.mozhimen.componentk.mediak.audio.cons.EAudioPlayMode
import com.mozhimen.componentk.mediak.audio.custom.CustomAudioPlayer
import com.mozhimen.componentk.mediak.status.cons.EPlayStatus
import com.mozhimen.componentk.mediak.audio.mos.MAudioK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CopyOnWriteArrayList


/**
 * @ClassName AudioKProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 15:19
 * @Version 1.0
 */
internal class MediaKAudioProxy(private val _owner: LifecycleOwner) : IMediaKAudio {

    companion object {
        private const val TAG = "AudioKProxy>>>>>"
    }

    private val _customAudioPlayer by lazy { CustomAudioPlayer(_owner) }

    private val _playList: CopyOnWriteArrayList<MAudioK> = CopyOnWriteArrayList()
    private var _playMode = EAudioPlayMode.LIST_ONCE
    private var _playPositionCurrent: Int = 0
        set(value) {
            if (_playList.isEmpty()) {
                field = 0
                return
            }
            val tmpIndex = if (value !in _playList.indices) {
                0
            } else value
            val mAudioK = getAudioFromPlayList(tmpIndex) ?: run {
                field = 0
                return
            }
            _customAudioPlayer.load(mAudioK)
            field = tmpIndex
        }

    init {
        _owner.lifecycleScope.launch(Dispatchers.Main) {
            UtilKDataBus.with<MAudioK?>(CAudioEvent.EVENT_AUDIO_COMPLETE).observe(_owner) {
                Log.d(TAG, "init: onCompleted id ${it?.id} url ${it?.url}")
                genNextAudio(it)
            }
            UtilKDataBus.with<MAudioK?>(CAudioEvent.EVENT_AUDIO_ERROR).observe(_owner) {
                Log.d(TAG, "init: onError id ${it?.id} url ${it?.url}")
                genNextAudio(it)
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun getPlayList(): List<MAudioK> {
        return _playList.toList()
    }

    override fun addAudiosToPlayList(audios: List<MAudioK>) {
        _playList.addAll(audios)
        play()
    }

    override fun addAudioToPlayList(audio: MAudioK) {
        _playList.add(audio)
        play()
    }

    override fun addAudioToPlayListTop(audioK: MAudioK) {
        if (getPlayStatus() == EPlayStatus.STARTED || _playList.size > 0)
            _playList.add(1, audioK)
        else _playList.add(0, audioK)
        play()
    }

    override fun clearPLayList() {
        _playList.clear()
    }

    override fun getPlayMode(): EAudioPlayMode {
        return _playMode
    }

    override fun setPlayMode(playMode: EAudioPlayMode) {
        _playMode = playMode
    }

    override fun setPlayPositionCurrent(playIndex: Int) {
        _playPositionCurrent = playIndex
    }

    override fun getPlayPositionCurrent(): Int {
        return _playPositionCurrent
    }

    override fun getPlayPositionNext(): Int {
        return when (_playMode) {
            EAudioPlayMode.LIST_ONCE -> {
                return 0
            }

            EAudioPlayMode.LIST_LOOP -> {
                (_playPositionCurrent + 1) % _playList.size
            }

            EAudioPlayMode.LIST_RANDOM -> {
                (0 until _playList.size).random() % _playList.size
            }

            EAudioPlayMode.SINGLE_REPEAT -> {
                _playPositionCurrent
            }
        }
    }

    override fun getPlayPositionPrevious(): Int {
        return when (_playMode) {
            EAudioPlayMode.LIST_ONCE -> {
                0
            }

            EAudioPlayMode.LIST_LOOP -> {
                (_playPositionCurrent + _playList.size - 1) % _playList.size
            }

            EAudioPlayMode.LIST_RANDOM -> {
                (0 until _playList.size).random() % _playList.size
            }

            EAudioPlayMode.SINGLE_REPEAT -> {
                _playPositionCurrent
            }
        }
    }

    override fun getAudioFromPlayList(index: Int): MAudioK? {
        return if (_playList.isNotEmpty() && index >= 0 && index < _playList.size) {
            _playList[index]
        } else null
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun play() {
        if (getPlayStatus() == EPlayStatus.STOPPED) _customAudioPlayer.resume()
        else if (getPlayStatus() == EPlayStatus.COMPLETED || getPlayStatus() == EPlayStatus.IDLE) _playPositionCurrent = 0
    }

    override fun playNext() {
        _playPositionCurrent = getPlayPositionNext()
    }

    override fun playPrevious() {
        _playPositionCurrent = getPlayPositionPrevious()
    }

    override fun pause() {
        if (getPlayStatus() == EPlayStatus.STARTED) _customAudioPlayer.pause()
    }

    override fun release() {
        _customAudioPlayer.release()
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun setVolume(volume: Int) {
        _customAudioPlayer.setVolume(volume)
    }

    override fun setVolumePercent(volumePercent: Float) {
        _customAudioPlayer.setVolumePercent(volumePercent)
    }

    override fun getVolumeCurrent(): Int {
        return _customAudioPlayer.getVolumeCurrent()
    }

    override fun getVolumeMin(): Int {
        return _customAudioPlayer.getVolumeMin()
    }

    override fun getVolumeMax(): Int {
        return _customAudioPlayer.getVolumeMax()
    }

    override fun getVolumeInterval(): Int {
        return _customAudioPlayer.getVolumeInterval()
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun getPlayStatus(): EPlayStatus {
        return _customAudioPlayer.getPlayStatus()
    }

    private fun genNextAudio(audio: MAudioK?) {
        if (audio == null) return
        _playPositionCurrent = when (_playMode) {
            EAudioPlayMode.LIST_RANDOM, EAudioPlayMode.LIST_LOOP, EAudioPlayMode.SINGLE_REPEAT -> {
                getPlayPositionNext()
            }

            EAudioPlayMode.LIST_ONCE -> {
                val index = _playList.indexOf(audio)
                if (index in _playList.indices) {
                    _playList.removeAt(index)
                }
                UtilKDataBus.with<Pair<MAudioK, Boolean>?>(CAudioEvent.EVENT_AUDIO_POPUP).setValue(audio to (_playList.size != 0))
                getPlayPositionNext()
            }
        }
    }
}