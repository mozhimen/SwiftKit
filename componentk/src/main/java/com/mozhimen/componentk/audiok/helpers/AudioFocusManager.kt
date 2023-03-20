package com.mozhimen.componentk.audiok.helpers

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.device.UtilKAudioManager
import com.mozhimen.basick.utilk.exts.normalize
import com.mozhimen.componentk.audiok.commons.IAudioKFocusListener

/**
 * @ClassName AudioFocusManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:00
 * @Version 1.0
 */
class AudioFocusManager(
    context: Context,
    private val _listener: IAudioKFocusListener? = null
) : OnAudioFocusChangeListener {
    private val _audioManager: AudioManager by lazy { UtilKAudioManager.get(context) }

    fun getAudioManager(): AudioManager =
        _audioManager

    fun getVolume() =
        _audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    fun getVolumeMax() =
        _audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    @RequiresApi(CVersionCode.V_28_9_P)
    fun getVolumeMin() =
        _audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)

    @RequiresApi(CVersionCode.V_28_9_P)
    fun setVolume(volume: Int) {
        _audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume.normalize(getVolumeMin()..getVolumeMax()), AudioManager.FLAG_PLAY_SOUND)
    }

    fun requestAudioFocus(): Boolean =
        _audioManager.requestAudioFocus(
            this, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED

    fun abandonAudioFocus() {
        _audioManager.abandonAudioFocus(this)
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> _listener?.onFocusGrant()
            AudioManager.AUDIOFOCUS_LOSS -> _listener?.onFocusLoss()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> _listener?.onFocusLossTransient()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> _listener?.onFocusLossDuck()
        }
    }
}