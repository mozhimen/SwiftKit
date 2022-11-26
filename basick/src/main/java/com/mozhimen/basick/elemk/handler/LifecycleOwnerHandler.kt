package com.mozhimen.basick.elemk.handler

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.exts.removeAll
import com.mozhimen.basick.elemk.handler.bases.BaseWeakClazzHandler

/**
 * @ClassName EventKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 11:34
 * @Version 1.0
 */
class LifecycleOwnerHandler<T : LifecycleOwner>(private val _clazz: T) : BaseWeakClazzHandler<T>(_clazz), DefaultLifecycleObserver {
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