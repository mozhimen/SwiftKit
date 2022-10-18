package com.mozhimen.basick.prefabk.handler

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.extsk.removeAll

/**
 * @ClassName EventKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 11:34
 * @Version 1.0
 */
class PrefabKHandler<T : LifecycleOwner>(private val _clazz: T) : RefHandler<T>(_clazz), DefaultLifecycleObserver {
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