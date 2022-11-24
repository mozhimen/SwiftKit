package com.mozhimen.componentk.audiok.commons

import com.mozhimen.componentk.audiok.cons.EPlayMode
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import com.mozhimen.componentk.audiok.mos.MAudioK


/**
 * @ClassName IAudioKListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 15:26
 * @Version 1.0
 */
internal interface IAudioKListener {
    fun getPlayList(): ArrayList<MAudioK>
    fun addAudiosToPlayList(audios: List<MAudioK>)
    fun addAudioToPlayList(audio: MAudioK)
    fun clearPLayList()
    fun getPlayStatus(): EPlayStatus
    fun getPlayMode(): EPlayMode
    fun setPlayMode(playMode: EPlayMode)
    fun setPlayIndexCurrent(playIndex: Int)
    fun getPlayIndexCurrent(): Int?

    //protect
    fun getPlayIndexNext(): Int
    fun getPlayIndexPrevious(): Int
    fun getAudioFromPlayList(index: Int): MAudioK?

    //control
    fun play()
    fun playNext()
    fun playPrevious()
    fun pause()
    fun release()
    fun setVolume(volume: Int)
    fun getVolume(): Int
    fun getVolumeMin(): Int
    fun getVolumeMax(): Int
}