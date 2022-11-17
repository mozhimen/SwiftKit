package com.mozhimen.basick.taskk

import android.os.CountDownTimer
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.taskk.commons.ITaskK

/**
 * @ClassName UtilKCountDown
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/26 15:57
 * @Version 1.0
 */
class TaskKCountDown(owner: LifecycleOwner) : ITaskK(owner) {

    private var _taskKCountDownListener: ITaskKCountDownListener? = null
    private var _countDownTimer: CountDownTimer? = null

    interface ITaskKCountDownListener {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }

    fun start(countDownMilliseconds: Long, listener: ITaskKCountDownListener? = null) {
        listener?.let { _taskKCountDownListener = it }
        _countDownTimer = UtilKCountDownTimer(countDownMilliseconds)
        _countDownTimer!!.start()
    }

    override fun cancel() {
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