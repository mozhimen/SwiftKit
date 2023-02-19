package com.mozhimen.basick.elemk.lifecycle.bases

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName BaseDelegateLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/21 21:22
 * @Version 1.0
 */
open class BaseLifecycleObserver(private val _owner: LifecycleOwner) : IDefaultLifecycleObserver {
    protected open val TAG = "${this.javaClass.simpleName}>>>>>"

    init {
        _owner.lifecycleScope.launch(Dispatchers.Main) {
            _owner.lifecycle.addObserver(this@BaseLifecycleObserver)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this@BaseLifecycleObserver)
    }
}