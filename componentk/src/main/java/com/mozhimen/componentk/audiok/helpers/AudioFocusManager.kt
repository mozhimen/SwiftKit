package com.mozhimen.componentk.audiok.helpers

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
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
    private val _audioManager: AudioManager = (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)

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