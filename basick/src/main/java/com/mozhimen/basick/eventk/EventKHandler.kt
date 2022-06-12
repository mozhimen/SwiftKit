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
class EventKHandler<T>(private val _cls: T) : HandlerRef<T>(_cls), DefaultLifecycleObserver {
    init {
        if (_cls is LifecycleOwner){
            _cls.lifecycle.addObserver(this)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (_cls is LifecycleOwner){
            this.removeAll()
        }
    }
}