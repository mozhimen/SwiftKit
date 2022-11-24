package com.mozhimen.basick.elemk.lifecycle.bases

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName LifecycleDelegate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/21 21:22
 * @Version 1.0
 */
open class BaseDelegateLifecycleObserver(private val _owner: LifecycleOwner) : DefaultLifecycleObserver {
    init {
        _owner.lifecycleScope.launch(Dispatchers.Main) {
            _owner.lifecycle.addObserver(this@BaseDelegateLifecycleObserver)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }
}