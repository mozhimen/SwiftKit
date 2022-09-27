package com.mozhimen.basick.eventk

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.eventk.commons.HandlerRef
import com.mozhimen.basick.extsk.removeAll

/**
 * @ClassName EventKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 11:34
 * @Version 1.0
 */
class EventKHandler<T : LifecycleOwner>(private val _clazz: T) : HandlerRef<T>(_clazz), DefaultLifecycleObserver {
    init {
        _clazz.lifecycle.addObserver(this)
    }

    override fun onPause(owner: LifecycleOwner) {
        this.removeAll()
        this.clear()
        _clazz.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}