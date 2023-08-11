package com.mozhimen.underlayk.fpsk.helpers

import android.view.Choreographer
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.underlayk.fpsk.commons.IFpsKListener
import java.util.concurrent.TimeUnit

/**
 * @ClassName FrameMonitor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 20:01
 * @Version 1.0
 */
internal class FrameMonitor : Choreographer.FrameCallback, IUtilK {
    private val _choreographer by lazy { Choreographer.getInstance() }
    private var _frameStartTime: Long = 0//这个是记录上一针到达的时间戳
    private var _frameCount = 0//1s内确切绘制了多少帧

    private var _listeners = arrayListOf<IFpsKListener>()

    fun addListener(vararg listener: IFpsKListener) {
        _listeners.addAll(listener)
    }

    fun removeListeners() {
        _listeners.clear()
    }

    fun start() {
        _choreographer.postFrameCallback(this)
    }

    fun stop() {
        _frameStartTime = 0
        _choreographer.removeFrameCallback(this)
    }

    override fun doFrame(frameTimeNanos: Long) {
        val currentTimeMills: Long = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)
        if (_frameStartTime > 0) {
            //计算两针之间的时间差
            // 500ms 100ms
            val timeSpan: Long = currentTimeMills - _frameStartTime
            //fps每秒多少帧frame per second
            _frameCount++
            if (timeSpan > 1000) {
                val fps: Double = _frameCount * 1000 / timeSpan.toDouble()
                for (listener: IFpsKListener in _listeners) {
                    listener.onFrame(fps)
                }
                _frameCount = 0
                _frameStartTime = currentTimeMills
            }
        } else {
            _frameStartTime = currentTimeMills
        }
        start()
    }
}
