package com.mozhimen.basick.taskk.temps

import android.os.CountDownTimer
import com.mozhimen.basick.taskk.bases.BaseTaskK

/**
 * @ClassName UtilKCountDown
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 15:57
 * @Version 1.0
 */
class TaskKCountDown : BaseTaskK() {

    private var _taskKCountDownListener: ITaskKCountDownListener? = null
    private var _countDownTimer: CountDownTimer? = null

    interface ITaskKCountDownListener {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }

    fun isActive() = _countDownTimer != null

    fun start(countDownMilliseconds: Long, listener: ITaskKCountDownListener? = null) {
        if (isActive()) return
        listener?.let { _taskKCountDownListener = it }
        _countDownTimer = UtilKCountDownTimer(countDownMilliseconds)
        _countDownTimer!!.start()
    }

    override fun cancel() {
        if (!isActive()) return
        _countDownTimer?.cancel()
        _countDownTimer = null
    }

    private inner class UtilKCountDownTimer(countDownMilliseconds: Long) : CountDownTimer(countDownMilliseconds, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _taskKCountDownListener?.onTick(millisUntilFinished)
        }

        override fun onFinish() {
            _taskKCountDownListener?.onFinish()
        }
    }
}