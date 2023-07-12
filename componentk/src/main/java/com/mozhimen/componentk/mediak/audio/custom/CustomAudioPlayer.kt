package com.mozhimen.componentk.mediak.audio.custom

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WifiLock
import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle
import com.mozhimen.basick.postk.livedata.PostKLiveDataEventBus
import com.mozhimen.basick.taskk.temps.TaskKPollInfinite
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.net.UtilKWifiManager
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.componentk.mediak.audio.focus.commons.IMediaKAudioFocusListener
import com.mozhimen.componentk.mediak.audio.cons.CAudioEvent
import com.mozhimen.componentk.mediak.audio.custom.commons.ICustomAudioPlayer
import com.mozhimen.componentk.mediak.audio.focus.MediaKAudioFocusManager
import com.mozhimen.componentk.mediak.status.cons.EPlayStatus
import com.mozhimen.componentk.mediak.audio.mos.MAudioK
import com.mozhimen.componentk.mediak.audio.mos.MAudioKProgress
import com.mozhimen.componentk.mediak.status.MediaKStatusPlayer

/**
 * @ClassName AudioPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:00
 * @Version 1.0
 */
class CustomAudioPlayer(private val _owner: LifecycleOwner) :
    ICustomAudioPlayer,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    IMediaKAudioFocusListener, BaseUtilK() {

    private var _mediaKStatusPlayer: MediaKStatusPlayer? = null
        get() {
            if (field != null) return field
            val mediaKStatusPlayer = MediaKStatusPlayer().apply {
                setWakeMode(_context, PowerManager.PARTIAL_WAKE_LOCK)// 使用唤醒锁
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setOnCompletionListener(this@CustomAudioPlayer)
                setOnPreparedListener(this@CustomAudioPlayer)
                setOnBufferingUpdateListener(this@CustomAudioPlayer)
                setOnErrorListener(this@CustomAudioPlayer)
            }
            return mediaKStatusPlayer.also { field = it }
        }

    private var _wifiLock: WifiLock? = null
        // 初始化wifi锁
        get() {
            if (field != null) return field
            val lock = UtilKWifiManager.get(_context).createWifiLock(WifiManager.WIFI_MODE_FULL, TAG)
            return lock.also { field = it }
        }

    private var _mediaKAudioFocusManager: MediaKAudioFocusManager? = null
        //音频焦点监听器
        get() {
            if (field != null) return field
            val manager = MediaKAudioFocusManager(_context, this)
            return manager.also { field = it }
        }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    private val _taskKAudioProgressUpdate by lazy { TaskKPollInfinite() }//更新进度Task

    private val _dataBusAudioProgressUpdate by lazy { PostKLiveDataEventBus.with<MAudioKProgress?>(CAudioEvent.EVENT_PROGRESS_UPDATE) }//发布更新进度Event

    private var _isPausedByFocusLossTransient = false

    private var _currentAudioK: MAudioK? = null

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun load(audio: MAudioK) {
        try {
            _currentAudioK = audio
            if (audio.id.isEmpty() || audio.url.isEmpty()) throw Exception("your path or id must not be null.")
            _mediaKStatusPlayer!!.apply {
                reset()
                if (audio.url.contains("/")) {
                    setDataSource(audio.url)
                } else {
                    val assetFileDescriptor = UtilKAsset.openFd(audio.url)
                    setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                }
                prepareAsync()
            }
            //发送加载音频事件，UI类型处理事件
            setAudioEvent(CAudioEvent.EVENT_AUDIO_LOAD, _currentAudioK)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            setAudioEvent(CAudioEvent.EVENT_AUDIO_ERROR, _currentAudioK)
        }
    }

    override fun resume() {
        if (getPlayStatus() == EPlayStatus.PAUSED) start()
    }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class)
    override fun pause() {
        if (getPlayStatus() != EPlayStatus.STARTED) return
        _mediaKStatusPlayer!!.pause()
        // 关闭wifi锁
        if (_wifiLock!!.isHeld) {
            _wifiLock!!.release()
        }
        // 取消音频焦点
        abandonAudioFocus()
        //停止发送进度消息
        _taskKAudioProgressUpdate.cancel()
        //发送暂停事件,UI类型事件
        setAudioEvent(CAudioEvent.EVENT_AUDIO_PAUSE, _currentAudioK)
    }

    @OptIn(ALintKOptIn_ApiInit_ByLazy::class)
    override fun release() {
        if (_mediaKStatusPlayer == null) return
        _mediaKStatusPlayer!!.release()
        _mediaKStatusPlayer = null
        // 取消音频焦点
        abandonAudioFocus()
        _mediaKAudioFocusManager = null
        // 关闭wifi锁
        if (_wifiLock != null && _wifiLock!!.isHeld) {
            _wifiLock!!.release()
            _wifiLock = null
        }
        _taskKAudioProgressUpdate.cancel()
        //发送销毁播放器事件,清除通知等
        setAudioEvent(CAudioEvent.EVENT_AUDIO_RELEASE, _currentAudioK)
    }

    override fun getCurrentPlayPosition(): Int =
        if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) _mediaKStatusPlayer!!.currentPosition else 0


    override fun getDuration(): Int =
        if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) _mediaKStatusPlayer!!.duration else 0

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun getPlayStatus(): EPlayStatus =
        _mediaKStatusPlayer?.getPlayStatus() ?: EPlayStatus.STOPPED

    override fun isPlayComplete(): Boolean =
        _mediaKStatusPlayer?.isPlayComplete() ?: true

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun getAudioManager(): AudioManager =
        _mediaKAudioFocusManager!!.getAudioManager()

    override fun setVolume(volume: Int) {
        _mediaKAudioFocusManager!!.setVolume(volume)
    }

    override fun setVolumePercent(volumePercent: Float) {
        _mediaKAudioFocusManager!!.setVolumePercent(volumePercent)
    }

    override fun requestAudioFocus(): Boolean =
        _mediaKAudioFocusManager!!.requestAudioFocus()

    override fun abandonAudioFocus() {
        _mediaKAudioFocusManager!!.abandonAudioFocus()
    }

    override fun getVolumeCurrent(): Int =
        _mediaKAudioFocusManager!!.getVolumeCurrent()

    override fun getVolumeMax(): Int =
        _mediaKAudioFocusManager!!.getVolumeMax()

    override fun getVolumeMin(): Int =
        _mediaKAudioFocusManager!!.getVolumeMin()

    override fun getVolumeInterval(): Int =
        _mediaKAudioFocusManager!!.getVolumeInterval()

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCompletion(mp: MediaPlayer?) {
        //发送播放完成事件,逻辑类型事件
        setAudioEvent(CAudioEvent.EVENT_AUDIO_COMPLETE, _currentAudioK)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onPrepared(mp: MediaPlayer?) {
        start()
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        //发送当次播放实败事件,逻辑类型事件
        setAudioEvent(CAudioEvent.EVENT_AUDIO_ERROR, _currentAudioK)
        return false
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onFocusGrant() {
        //重新获得焦点
        setMediaVolume(1.0f, 1.0f)
        if (_isPausedByFocusLossTransient) resume()
        _isPausedByFocusLossTransient = false
    }

    override fun onFocusLoss() {
        //永久失去焦点，暂停
        pause()
    }

    override fun onFocusLossTransient() {
        //短暂失去焦点，暂停
        pause()
        _isPausedByFocusLossTransient = true
    }

    override fun onFocusLossDuck() {
        //瞬间失去焦点
        setMediaVolume(0.5f, 0.5f)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * prepare以后自动调用start方法,外部不能调用
     */
    @OptIn(ALintKOptIn_ApiInit_ByLazy::class, ALintKOptIn_ApiCall_BindLifecycle::class)
    private fun start() {
        if (!requestAudioFocus()) {// 获取音频焦点,保证我们的播放器顺利播放
            Log.e(TAG, "获取音频焦点失败")
        }
        _mediaKStatusPlayer!!.start()
        _wifiLock!!.acquire()//启用wifi锁
        _taskKAudioProgressUpdate.bindLifecycle(_owner)
        _taskKAudioProgressUpdate.start(2000) {
            //暂停也要更新进度，防止UI不同步，只不过进度一直一样
            if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) {
                //UI类型处理事件
                _dataBusAudioProgressUpdate.postValue(MAudioKProgress(getPlayStatus(), getCurrentPlayPosition(), getDuration(), _currentAudioK).also {
                    Log.d(TAG, "start: progress_update $it")
                })
            }
        }
        setAudioEvent(CAudioEvent.EVENT_AUDIO_START, _currentAudioK)
    }

    private fun setAudioEvent(eventName: String, audio: MAudioK?) {
        Log.d(TAG, "setAudioEvent: eventName [$eventName] audio $audio")
        PostKLiveDataEventBus.with<MAudioK?>(eventName).postValue(audio)
    }

    private fun setMediaVolume(left: Float, right: Float) {
        _mediaKStatusPlayer!!.setVolume(left, right)
    }
}