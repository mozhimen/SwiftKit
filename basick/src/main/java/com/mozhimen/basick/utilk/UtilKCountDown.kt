package com.mozhimen.basick.utilk

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @ClassName UtilKCountDown
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 15:57
 * @Version 1.0
 */
class UtilKCountDown(private val _lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {

    private var _utilKCountDownListener: IUtilKCountDownListener? = null
    private var _countDownTimer: CountDownTimer? = null

    init {
        _lifecycleOwner.lifecycle.addObserver(this)
    }

    interface IUtilKCountDownListener {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }

    fun start(countDownMilliseconds: Long, listener: IUtilKCountDownListener? = null) {
        listener?.let { _utilKCountDownListener = it }
        _countDownTimer = UtilKCountDownTimer(countDownMilliseconds)
        _countDownTimer!!.start()
    }

    fun stop() {
        _countDownTimer?.cancel()
        _countDownTimer = null
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stop()
        _lifecycleOwner.lifecycle.removeObserver(this)
    }

    private inner class UtilKCountDownTimer(countDownMilliseconds: Long) : CountDownTimer(countDownMilliseconds, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _utilKCountDownListener?.onTick(millisUntilFinished)
        }

        override fun onFinish() {
            _utilKCountDownListener?.onFinish()
        }
    }
}