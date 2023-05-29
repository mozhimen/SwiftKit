package com.mozhimen.componentk.mediak.audio.focus.commons

import android.media.AudioManager

/**
 * @ClassName IAudioKFocus
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/29 15:05
 * @Version 1.0
 */
interface IAudioKFocusManager {
    /**
     * 获取
     * @return AudioManager
     */
    fun getAudioManager(): AudioManager

    /**
     * 获取当前音量
     * @return Int
     */
    fun getVolumeCurrent(): Int

    /**
     * 获取当前音量最大值
     * @return Int
     */
    fun getVolumeMax(): Int

    /**
     * 获取当前音量最小值
     * @return Int
     */
    fun getVolumeMin(): Int

    /**
     * 获取音域
     * @return Int
     */
    fun getVolumeInterval(): Int

    /**
     * 设置音量
     * @param volume Int
     */
    fun setVolume(volume: Int)

    /**
     * 设置音量比例
     * @param volumePercent Float
     */
    fun setVolumePercent(volumePercent: Float)

    /**
     * 获取声音焦点
     * @return Boolean
     */
    fun requestAudioFocus(): Boolean

    /**
     * 取消音量焦点
     */
    fun abandonAudioFocus()
}