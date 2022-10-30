package com.mozhimen.componentk.audiok.helpers

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WifiLock
import android.os.PowerManager
import android.util.Log
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.componentk.audiok.commons.IAudioFocusListener
import com.mozhimen.componentk.audiok.commons.IAudioProgressListener
import com.mozhimen.componentk.audiok.mos.PlayStatus

/**
 * @ClassName AudioPlayer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/30 19:00
 * @Version 1.0
 */
class AudioPlayer :
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    IAudioFocusListener {
    private val TAG = "AudioPlayer>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!
    private var _customMediaPlayer: CustomMediaPlayer? = null
        get() {
            if (field != null) return field
            val customMediaPlayer = CustomMediaPlayer()
            customMediaPlayer.setWakeMode(_context, PowerManager.PARTIAL_WAKE_LOCK)// 使用唤醒锁
            customMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            customMediaPlayer.setOnCompletionListener(this)
            customMediaPlayer.setOnPreparedListener(this)
            customMediaPlayer.setOnBufferingUpdateListener(this)
            customMediaPlayer.setOnErrorListener(this)
            return customMediaPlayer.also { field = it }
        }

    // 初始化wifi锁
    private val _wifiLock: WifiLock = (_context.getSystemService(Context.WIFI_SERVICE) as WifiManager).createWifiLock(WifiManager.WIFI_MODE_FULL, TAG)

    //音频焦点监听器
    private val _audioFocusManager: AudioFocusManager = AudioFocusManager(_context, this)

    private var _isPausedByFocusLossTransient = false

    private var _audioProgressListener: IAudioProgressListener? = null

    /**
     * 对外提供的播放方法
     */
    fun resume() {
        if (getStatus() == PlayStatus.PAUSED) {
            start()
        }
    }

    /**
     * 获取播放器状态
     * @return PlayStatus
     */
    fun getStatus(): PlayStatus =
        _customMediaPlayer?.getState() ?: PlayStatus.STOPPED

    /**
     * 设置音量
     * @param left Float
     * @param right Float
     */
    fun setVolume(left: Float, right: Float) {
        _customMediaPlayer!!.setVolume(left, right)
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        TODO("Not yet implemented")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onFocusGrant() {
        //重新获得焦点
        setVolume(1.0f, 1.0f)
        if (_isPausedByFocusLossTransient) resume()
        _isPausedByFocusLossTransient = false
    }

    override fun onFocusLoss() {
        TODO("Not yet implemented")
    }

    override fun onFocusLossTransient() {
        TODO("Not yet implemented")
    }

    override fun onFocusLossDuck() {
        TODO("Not yet implemented")
    }

    /**
     * prepare以后自动调用start方法,外部不能调用
     */
    private fun start() {
        if (!_audioFocusManager.requestAudioFocus()) {// 获取音频焦点,保证我们的播放器顺利播放
            Log.e(TAG, "获取音频焦点失败")
        }
        _customMediaPlayer!!.start()
        _wifiLock.acquire()//启用wifi锁

        mHandler.sendEmptyMessage(AudioPlayer.TIME_MSG)        //更新进度
        EventBus.getDefault().post(AudioStartEvent())        //发送start事件，UI类型处理事件
    }
}