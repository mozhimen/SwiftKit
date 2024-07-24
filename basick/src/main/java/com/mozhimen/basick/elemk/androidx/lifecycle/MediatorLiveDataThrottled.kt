package com.mozhimen.basick.elemk.androidx.lifecycle

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @ClassName ThrottledLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/13
 * @Version 1.0
 */
/**
 * LiveData throttling value emissions so they don't happen more often than [delayMilliseconds].
 */
class MediatorLiveDataThrottled<T>(source: LiveData<T>, delayMilliseconds: Long) : MediatorLiveData<T>() {
    var delayMilliseconds = delayMilliseconds
        private set

    private val _handler = Handler(Looper.getMainLooper())
    private var _isValueDelayed = false
    private var _delayedValue: T? = null
    private var _delayRunnable: Runnable? = null
        set(value) {
            field?.let { _handler.removeCallbacks(it) }
            value?.let { _handler.postDelayed(it, delayMilliseconds) }
            field = value
        }
    private val _objDelayRunnable = Runnable {
        if (consumeDelayedValue())
            startDelay()
    }

    //////////////////////////////////////////////////////////////////////

    init {
        addSource(source) { newValue ->
            if (_delayRunnable == null) {
                value = newValue
                startDelay()
            } else {
                _isValueDelayed = true
                _delayedValue = newValue
            }
        }
    }

    //////////////////////////////////////////////////////////////////////

    override fun onInactive() {
        super.onInactive()
        consumeDelayedValue()
    }

    //////////////////////////////////////////////////////////////////////

    // start counting the delay or clear it if conditions are not met
    private fun startDelay() {
        _delayRunnable = if (delayMilliseconds > 0 && hasActiveObservers()) _objDelayRunnable else null
    }

    private fun consumeDelayedValue(): Boolean {
        _delayRunnable = null
        return if (_isValueDelayed) {
            value = _delayedValue
            _delayedValue = null
            _isValueDelayed = false
            true
        } else false
    }
}
