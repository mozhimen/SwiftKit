package com.mozhimen.basick.elemk.handler

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.utilk.exts.removeAll
import com.mozhimen.basick.elemk.handler.bases.BaseWeakClazzHandler
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName EventKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 11:34
 * @Version 1.0
 */
class LifecycleOwnerHandler<T : LifecycleOwner>(private val _clazz: T) : BaseWeakClazzHandler<T>(_clazz), IDefaultLifecycleObserver {
    init {
        _clazz.lifecycleScope.launch(Dispatchers.Main) {
            _clazz.lifecycle.addObserver(this@LifecycleOwnerHandler)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        this.removeAll()
        this.clear()
        _clazz.lifecycle.removeObserver(this@LifecycleOwnerHandler)
    }
}