package com.mozhimen.componentk.mediak.audio.helpers

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.componentk.mediak.audio.commons.IMediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.CMediaKAudioCons
import com.mozhimen.componentk.mediak.audio.cons.EMediaKAudioPlayMode
import com.mozhimen.componentk.mediak.audio.player.custom.MediaKAudioPlayerCustom
import com.mozhimen.componentk.mediak.player.status.cons.EMediaKPlayerStatus
import com.mozhimen.componentk.mediak.audio.mos.MAudioKInfo
import java.util.concurrent.CopyOnWriteArrayList


/**
 * @ClassName MediaKAudioDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 15:19
 * @Version 1.0
 */
internal class MediaKAudioDelegate(private val _owner: LifecycleOwner) : IMediaKAudio, BaseUtilK() {

    private val _mediaKAudioPlayerCustom by lazy { MediaKAudioPlayerCustom(_owner) }

    private val _playList: CopyOnWriteArrayList<MAudioKInfo> = CopyOnWriteArrayList()
    private var _playMode = EMediaKAudioPlayMode.LIST_ONCE
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
            _mediaKAudioPlayerCustom.load(mAudioK)
            field = tmpIndex
        }

    init {
        _owner.runOnMainThread {
            PostKEventLiveData.instance.with<MAudioKInfo?>(CMediaKAudioCons.Event.AUDIO_COMPLETE).observe(_owner) {
                Log.d(TAG, "init: onCompleted id ${it?.id} url ${it?.url}")
                genNextAudio(it)
            }
            PostKEventLiveData.instance.with<MAudioKInfo?>(CMediaKAudioCons.Event.AUDIO_ERROR).observe(_owner) {
                Log.d(TAG, "init: onError id ${it?.id} url ${it?.url}")
                genNextAudio(it)
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun getPlayList(): List<MAudioKInfo> {
        return _playList.toList()
    }

    override fun addAudiosToPlayList(audios: List<MAudioKInfo>) {
        _playList.addAll(audios)
        play()
    }

    override fun addAudioToPlayList(audio: MAudioKInfo) {
        _playList.add(audio)
        play()
    }

    override fun addAudioToPlayListTop(audioK: MAudioKInfo) {
        if (getPlayStatus() == EMediaKPlayerStatus.STARTED || _playList.size > 0)
            _playList.add(1, audioK)
        else _playList.add(0, audioK)
        play()
    }

    override fun clearPLayList() {
        _playList.clear()
    }

    override fun getPlayMode(): EMediaKAudioPlayMode {
        return _playMode
    }

    override fun setPlayMode(playMode: EMediaKAudioPlayMode) {
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
            EMediaKAudioPlayMode.LIST_ONCE -> 0
            EMediaKAudioPlayMode.LIST_LOOP -> (_playPositionCurrent + 1) % _playList.size
            EMediaKAudioPlayMode.LIST_RANDOM -> (0 until _playList.size).random() % _playList.size
            EMediaKAudioPlayMode.SINGLE_REPEAT -> _playPositionCurrent
        }
    }

    override fun getPlayPositionPrevious(): Int {
        return when (_playMode) {
            EMediaKAudioPlayMode.LIST_ONCE -> 0
            EMediaKAudioPlayMode.LIST_LOOP ->  (_playPositionCurrent + _playList.size - 1) % _playList.size
            EMediaKAudioPlayMode.LIST_RANDOM -> (0 until _playList.size).random() % _playList.size
            EMediaKAudioPlayMode.SINGLE_REPEAT -> _playPositionCurrent
        }
    }

    override fun getAudioFromPlayList(index: Int): MAudioKInfo? {
        return if (_playList.isNotEmpty() && index >= 0 && index < _playList.size) {
            _playList[index]
        } else null
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun play() {
        if (getPlayStatus() == EMediaKPlayerStatus.STOPPED) _mediaKAudioPlayerCustom.resume()
        else if (getPlayStatus() == EMediaKPlayerStatus.COMPLETED || getPlayStatus() == EMediaKPlayerStatus.IDLE) _playPositionCurrent = 0
    }

    override fun playNext() {
        _playPositionCurrent = getPlayPositionNext()
    }

    override fun playPrevious() {
        _playPositionCurrent = getPlayPositionPrevious()
    }

    override fun pause() {
        if (getPlayStatus() == EMediaKPlayerStatus.STARTED) _mediaKAudioPlayerCustom.pause()
    }

    override fun release() {
        _mediaKAudioPlayerCustom.release()
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun setVolume(volume: Int) {
        _mediaKAudioPlayerCustom.setVolume(volume)
    }

    override fun setVolumePercent(volumePercent: Float) {
        _mediaKAudioPlayerCustom.setVolumePercent(volumePercent)
    }

    override fun getVolumeCurrent(): Int {
        return _mediaKAudioPlayerCustom.getVolumeCurrent()
    }

    override fun getVolumeMin(): Int {
        return _mediaKAudioPlayerCustom.getVolumeMin()
    }

    override fun getVolumeMax(): Int {
        return _mediaKAudioPlayerCustom.getVolumeMax()
    }

    override fun getVolumeInterval(): Int {
        return _mediaKAudioPlayerCustom.getVolumeInterval()
    }

    ////////////////////////////////////////////////////////////////////////////

    override fun getPlayStatus(): EMediaKPlayerStatus {
        return _mediaKAudioPlayerCustom.getPlayStatus()
    }

    private fun genNextAudio(audio: MAudioKInfo?) {
        if (audio == null) return
        _playPositionCurrent = when (_playMode) {
            EMediaKAudioPlayMode.LIST_RANDOM, EMediaKAudioPlayMode.LIST_LOOP, EMediaKAudioPlayMode.SINGLE_REPEAT -> {
                getPlayPositionNext()
            }

            EMediaKAudioPlayMode.LIST_ONCE -> {
                val index = _playList.indexOf(audio)
                if (index in _playList.indices) {
                    _playList.removeAt(index)
                }
                PostKEventLiveData.instance.with<Pair<MAudioKInfo, Boolean>?>(CMediaKAudioCons.Event.AUDIO_POPUP).setValue(audio to (_playList.size != 0))
                getPlayPositionNext()
            }
        }
    }
}