package com.mozhimen.componentk.mediak.audio.focus

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import com.mozhimen.basick.utilk.android.media.UtilKAudioManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
import com.mozhimen.basick.utilk.kotlin.normalize
import com.mozhimen.componentk.mediak.audio.focus.commons.IAudioKFocusManager
import com.mozhimen.componentk.mediak.audio.focus.commons.IMediaKAudioFocusListener
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * @ClassName AudioFocusManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:00
 * @Version 1.0
 */
class MediaKAudioFocusManager(
    context: Context,
    private val _listener: IMediaKAudioFocusListener? = null
) : OnAudioFocusChangeListener, IAudioKFocusManager {
    private val _audioManager: AudioManager by lazy { UtilKAudioManager.get(context) }

    override fun getAudioManager(): AudioManager =
        _audioManager

    override fun getVolumeCurrent(): Int =
        getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC)

    override fun getVolumeMax(): Int =
        getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    override fun getVolumeMin(): Int =
        if (UtilKBuildVers.isAfterV_28_9_P()) getAudioManager().getStreamMinVolume(AudioManager.STREAM_MUSIC) else 0

    override fun getVolumeInterval(): Int =
        abs(getVolumeMax() - getVolumeMin())

    override fun setVolume(volume: Int) {
        getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, volume.normalize(getVolumeMin(), getVolumeMax()), AudioManager.FLAG_PLAY_SOUND)
    }

    override fun setVolumePercent(volumePercent: Float) {
        setVolume((volumePercent * getVolumeInterval()).roundToInt().normalize(getVolumeMin(), getVolumeMax()))
    }

    override fun requestAudioFocus(): Boolean =
        getAudioManager().requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED

    override fun abandonAudioFocus() {
        getAudioManager().abandonAudioFocus(this)
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