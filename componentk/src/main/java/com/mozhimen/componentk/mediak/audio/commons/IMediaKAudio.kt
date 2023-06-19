package com.mozhimen.componentk.mediak.audio.commons

import com.mozhimen.componentk.mediak.audio.cons.EAudioPlayMode
import com.mozhimen.componentk.mediak.status.cons.EPlayStatus
import com.mozhimen.componentk.mediak.audio.mos.MAudioK


/**
 * @ClassName IAudioKListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 15:26
 * @Version 1.0
 */
internal interface IMediaKAudio {
    /**
     * 获取播放列表
     * @return List<MAudioK>
     */
    fun getPlayList(): List<MAudioK>
    fun addAudiosToPlayList(audios: List<MAudioK>)
    fun addAudioToPlayList(audio: MAudioK)
    fun addAudioToPlayListTop(audioK: MAudioK)
    fun clearPLayList()
    fun getPlayMode(): EAudioPlayMode
    fun setPlayMode(playMode: EAudioPlayMode)
    fun setPlayPositionCurrent(playIndex: Int)
    fun getPlayPositionCurrent(): Int?
    fun getPlayPositionNext(): Int
    fun getPlayPositionPrevious(): Int
    fun getAudioFromPlayList(index: Int): MAudioK?

    //control
    fun play()
    fun playNext()
    fun playPrevious()
    fun pause()
    fun release()

    /////////////////////////////////////////////////////

    fun getVolumeCurrent(): Int
    fun getVolumeMax(): Int
    fun getVolumeMin(): Int
    fun getVolumeInterval(): Int
    fun setVolume(volume: Int)
    fun setVolumePercent(volumePercent: Float)

    /////////////////////////////////////////////////////

    fun getPlayStatus(): EPlayStatus
}