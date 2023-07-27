package com.mozhimen.componentk.mediak.audio.manager.focus.commons

/**
 * @ClassName IAudioFocusListener
 * @Description  音频焦点改变,接口回调，
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:01
 * @Version 1.0
 */
interface IMediaKAudioManagerFocusListener {
    /**
     * 获得焦点回调处理
     */
    fun onFocusGrant()

    /**
     * 永久失去焦点回调处理
     */
    fun onFocusLoss()

    /**
     * 短暂失去焦点回调处理
     */
    fun onFocusLossTransient()

    /**
     * 瞬间失去焦点回调
     */
    fun onFocusLossDuck()
}