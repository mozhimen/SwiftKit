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
class EventKHandler<T>(private val _clazz: T) : HandlerRef<T>(_clazz), DefaultLifecycleObserver {
    init {
        if (_clazz is LifecycleOwner){
            _clazz.lifecycle.addObserver(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (_clazz is LifecycleOwner){
            this.removeAll()
        }
    }
}