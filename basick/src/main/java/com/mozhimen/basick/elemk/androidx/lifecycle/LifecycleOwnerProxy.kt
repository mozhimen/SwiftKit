package com.mozhimen.basick.elemk.androidx.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnCreate
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnPause
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnResume
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStart
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStop

/**
 * @ClassName LifecycleOwnerProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/29
 * @Version 1.0
 */
@OApiInit_ByLazy
class LifecycleOwnerProxy : LifecycleOwner {
    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    ///////////////////////////////////////////////////////////////////////

    fun onCreate() {
        lifecycleRegistry.handleLifecycleEventOnCreate()
    }

    fun onStart() {
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    fun onResume() {
        lifecycleRegistry.handleLifecycleEventOnResume()
    }

    fun onPause() {
        lifecycleRegistry.handleLifecycleEventOnPause()
    }

    fun onStop() {
        lifecycleRegistry.handleLifecycleEventOnStop()
    }

    fun onDestroy() {
        lifecycleRegistry.handleLifecycleEventOnStop()
    }

    ///////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}