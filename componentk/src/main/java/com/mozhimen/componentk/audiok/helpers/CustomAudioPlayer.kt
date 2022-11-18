package com.mozhimen.componentk.audiok.helpers

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WifiLock
import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.componentk.audiok.commons.IAudioKFocusListener
import com.mozhimen.componentk.audiok.cons.CAudioKEvent
import com.mozhimen.componentk.audiok.cons.EPlayStatus
import com.mozhimen.componentk.audiok.mos.MAudioK
import com.mozhimen.componentk.audiok.mos.MAudioKProgress

/**
 * @ClassName AudioPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:00
 * @Version 1.0
 */
class CustomAudioPlayer(owner: LifecycleOwner) :
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    IAudioKFocusListener {
    private val TAG = "AudioPlayer>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!
    private var _statusMediaPlayer: StatusMediaPlayer? = null
        get() {
            if (field != null) return field
            val statusMediaPlayer = StatusMediaPlayer()
            statusMediaPlayer.setWakeMode(_context, PowerManager.PARTIAL_WAKE_LOCK)// 使用唤醒锁
            statusMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            statusMediaPlayer.setOnCompletionListener(this)
            statusMediaPlayer.setOnPreparedListener(this)
            statusMediaPlayer.setOnBufferingUpdateListener(this)
            statusMediaPlayer.setOnErrorListener(this)
            return statusMediaPlayer.also { field = it }
        }

    // 初始化wifi锁
    private var _wifiLock: WifiLock? = null
        get() {
            if (field != null) return field
            val lock = (_context.getSystemService(Context.WIFI_SERVICE) as WifiManager).createWifiLock(WifiManager.WIFI_MODE_FULL, TAG)
            return lock.also { field = it }
        }

    //音频焦点监听器
    private var _audioFocusManager: AudioFocusManager? = null
        get() {
            if (field != null) return field
            val manager = AudioFocusManager(_context, this)
            return manager.also { field = it }
        }

    //更新进度Task
    private val _taskKProUpd by lazy { TaskKPoll(owner) }

    //发布更新进度Event
    private val _dataBusProUpd by lazy { UtilKDataBus.with<MAudioKProgress?>(CAudioKEvent.progress_update) }

    private var _isPausedByFocusLossTransient = false

    private var _currentAudio: MAudioK? = null

    /**
     * 加载音频
     * @param audio MAudioK
     */
    fun load(audio: MAudioK) {
        try {
            _currentAudio = audio
            if (audio.id.isEmpty() || audio.url.isEmpty()) throw Exception("your path or id must not be null.")
            _statusMediaPlayer!!.apply {
                reset()
                setDataSource(audio.url)
                prepareAsync()
            }
            //发送加载音频事件，UI类型处理事件
            setAudioEvent(CAudioKEvent.audio_load, _currentAudio)
        } catch (e: Exception) {
            e.printStackTrace()
            setAudioEvent(CAudioKEvent.audio_error, _currentAudio)
        }
    }

    /**
     * 对外提供的播放方法
     */
    fun resume() {
        if (getPlayStatus() == EPlayStatus.PAUSED) start()
    }

    /**
     * pause方法
     */
    fun pause() {
        if (getPlayStatus() != EPlayStatus.STARTED) return
        _statusMediaPlayer!!.pause()
        // 关闭wifi锁
        if (_wifiLock!!.isHeld) {
            _wifiLock!!.release()
        }
        // 取消音频焦点
        _audioFocusManager?.abandonAudioFocus()
        //停止发送进度消息
        _taskKProUpd.cancel()
        //发送暂停事件,UI类型事件
        setAudioEvent(CAudioKEvent.audio_pause, _currentAudio)
    }

    /**
     * 销毁唯一_statusMediaPlayer实例,只有在退出app时使用
     */
    fun release() {
        if (_statusMediaPlayer == null) return
        _statusMediaPlayer!!.release()
        _statusMediaPlayer = null
        // 取消音频焦点
        _audioFocusManager?.abandonAudioFocus()
        _audioFocusManager = null
        // 关闭wifi锁
        if (_wifiLock != null && _wifiLock!!.isHeld) {
            _wifiLock!!.release()
            _wifiLock = null
        }
        _taskKProUpd.cancel()
        //发送销毁播放器事件,清除通知等
        setAudioEvent(CAudioKEvent.audio_release, _currentAudio)
    }

    /**
     * 获取播放器状态
     * @return PlayStatus
     */
    fun getPlayStatus(): EPlayStatus =
        _statusMediaPlayer?.getState() ?: EPlayStatus.STOPPED

    /**
     * 获取当前进度
     * @return Int
     */
    fun getCurrentPosition(): Int =
        if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) _statusMediaPlayer!!.currentPosition else 0

    /**
     * 获取当前音乐总时长,更新进度用
     * @return Int
     */
    fun getDuration(): Int =
        if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) _statusMediaPlayer!!.duration else 0

    /**
     * 设置音量
     * @param volume Int
     */
    fun setVolume(volume: Int) {
        _audioFocusManager!!.setVolume(volume)
    }

    fun getVolume(): Int {
        return _audioFocusManager!!.getVolume()
    }

    fun getVolumeMin(): Int {
        return _audioFocusManager!!.getVolumeMin()
    }

    fun getVolumeMax(): Int {
        return _audioFocusManager!!.getVolumeMax()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        //发送播放完成事件,逻辑类型事件
        setAudioEvent(CAudioKEvent.audio_complete, _currentAudio)
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }

    override fun onPrepared(mp: MediaPlayer?) {
        start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        //发送当次播放实败事件,逻辑类型事件
        setAudioEvent(CAudioKEvent.audio_error, _currentAudio)
        return false
    }

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

    /**
     * prepare以后自动调用start方法,外部不能调用
     */
    private fun start() {
        if (!_audioFocusManager!!.requestAudioFocus()) {// 获取音频焦点,保证我们的播放器顺利播放
            Log.e(TAG, "获取音频焦点失败")
        }
        _statusMediaPlayer!!.start()
        _wifiLock!!.acquire()//启用wifi锁
        _taskKProUpd.start(2000) {
            //暂停也要更新进度，防止UI不同步，只不过进度一直一样
            if (getPlayStatus() == EPlayStatus.STARTED || getPlayStatus() == EPlayStatus.PAUSED) {
                //UI类型处理事件
                _dataBusProUpd.postValue(MAudioKProgress(getPlayStatus(), getCurrentPosition(), getDuration(), _currentAudio))
            }
        }
        setAudioEvent(CAudioKEvent.audio_start, _currentAudio)
    }

    private fun setAudioEvent(eventName: String, audio: MAudioK?) {
        UtilKDataBus.with<MAudioK?>(eventName).postValue(audio)
    }

    private fun setMediaVolume(left: Float, right: Float) {
        _statusMediaPlayer!!.setVolume(left, right)
    }
}