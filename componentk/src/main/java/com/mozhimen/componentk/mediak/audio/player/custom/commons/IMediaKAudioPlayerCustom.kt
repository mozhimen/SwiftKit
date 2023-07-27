package com.mozhimen.componentk.mediak.audio.player.custom.commons

import com.mozhimen.componentk.mediak.audio.manager.focus.commons.IMediaKAudioManagerFocus
import com.mozhimen.componentk.mediak.audio.mos.MAudioKInfo
import com.mozhimen.componentk.mediak.player.status.commons.IMediaKStatusPLayer
import com.mozhimen.componentk.mediak.player.status.cons.EMediaKPlayerStatus

/**
 * @ClassName ICustomAudioPlayer
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/29 16:07
 * @Version 1.0
 */
interface IMediaKAudioPlayerCustom : IMediaKStatusPLayer, IMediaKAudioManagerFocus {
    /**
     * 加载音频
     * @param audio MAudioK
     */
    fun load(audio: MAudioKInfo)

    /**
     * 对外提供的播放方法
     */
    fun resume()

    /**
     * pause方法
     */
    fun pause()

    /**
     * 销毁唯一_statusMediaPlayer实例,只有在退出app时使用
     */
    fun release()

    /**
     * 获取当前进度
     * @return Int
     */
    fun getCurrentPlayPosition(): Int

    /**
     * 获取当前音乐总时长,更新进度用
     * @return Int
     */
    fun getDuration(): Int

    //隔离某些方法
    /////////////////////////////////////////////////////////////////

    override fun setPlayStatus(status: EMediaKPlayerStatus) {}

    /////////////////////////////////////////////////////////////////
}