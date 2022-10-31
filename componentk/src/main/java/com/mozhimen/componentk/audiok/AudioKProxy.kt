package com.mozhimen.componentk.audiok

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.componentk.audiok.commons.IAudioKListener
import com.mozhimen.componentk.audiok.cons.CEvent
import com.mozhimen.componentk.audiok.cons.EPlayMode
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import com.mozhimen.componentk.audiok.helpers.CustomAudioPlayer
import com.mozhimen.componentk.audiok.mos.MAudioK


/**
 * @ClassName AudioKProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 15:19
 * @Version 1.0
 */
class AudioKProxy(owner: LifecycleOwner) : IAudioKListener {

    private val _customAudioPlayer by lazy { CustomAudioPlayer(owner) }

    private val _playList: ArrayList<MAudioK> = ArrayList()
    private var _playMode = EPlayMode.LIST_ONCE
    private var _playIndexCurrent: Int = 0
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
            _customAudioPlayer.load(mAudioK.url)
            field = tmpIndex
        }

    init {
        UtilKDataBus.with<String?>(CEvent.audio_complete).observe(owner) {
            if (it != null) {
                _playIndexCurrent = when (_playMode) {
                    EPlayMode.LIST_RANDOM, EPlayMode.LIST_LOOP, EPlayMode.SINGLE_REPEAT -> {
                        getPlayIndexNext()
                    }
                    EPlayMode.LIST_ONCE -> {
                        _playList.removeIf { mo -> mo.url == it }
                        getPlayIndexNext()
                    }
                }
            }
        }
    }

    override fun getPlayList(): ArrayList<MAudioK> {
        return _playList
    }

    override fun addAudiosToPlayList(audios: List<MAudioK>) {
        _playList.addAll(audios)
    }

    override fun addAudioToPlayList(audio: MAudioK) {
        _playList.add(audio)
    }

    override fun clearPLayList() {
        _playList.clear()
    }

    override fun getPlayStatus(): EPlayStatus {
        return _customAudioPlayer.getPlayStatus()
    }

    override fun getPlayMode(): EPlayMode {
        return _playMode
    }

    override fun setPlayMode(playMode: EPlayMode) {
        _playMode = playMode
    }

    override fun setPlayIndexCurrent(playIndex: Int) {
        _playIndexCurrent = playIndex
    }

    override fun getPlayIndexCurrent(): Int {
        return _playIndexCurrent
    }

    override fun getPlayIndexNext(): Int {
        return when (_playMode) {
            EPlayMode.LIST_ONCE -> {
                return 0
            }
            EPlayMode.LIST_LOOP -> {
                (_playIndexCurrent + 1) % _playList.size
            }
            EPlayMode.LIST_RANDOM -> {
                (0 until _playList.size).random() % _playList.size
            }
            EPlayMode.SINGLE_REPEAT -> {
                _playIndexCurrent
            }
        }
    }

    override fun getPlayIndexPrevious(): Int {
        return when (_playMode) {
            EPlayMode.LIST_ONCE -> {
                0
            }
            EPlayMode.LIST_LOOP -> {
                (_playIndexCurrent + _playList.size - 1) % _playList.size
            }
            EPlayMode.LIST_RANDOM -> {
                (0 until _playList.size).random() % _playList.size
            }
            EPlayMode.SINGLE_REPEAT -> {
                _playIndexCurrent
            }
        }
    }

    override fun getAudioFromPlayList(index: Int): MAudioK? {
        return if (_playList.isNotEmpty() && index >= 0 && index < _playList.size) {
            _playList[index]
        } else null
    }

    override fun play() {
        if (getPlayStatus() == EPlayStatus.STOPPED) _customAudioPlayer.resume()
        else if (getPlayStatus() == EPlayStatus.COMPLETED) getAudioFromPlayList(_playIndexCurrent)?.let { _customAudioPlayer.load(it.url) }
    }

    override fun playNext() {
        _playIndexCurrent = getPlayIndexNext()
    }

    override fun playPrevious() {
        _playIndexCurrent = getPlayIndexPrevious()
    }

    override fun pause() {
        if (getPlayStatus() == EPlayStatus.STARTED) _customAudioPlayer.pause()
    }

    override fun release() {
        _customAudioPlayer.release()
    }
}