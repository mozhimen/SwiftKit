package com.mozhimen.basick.elemk.androidx.lifecycle

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnCreate
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnDestroy
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnPause
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnResume
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStart
import com.mozhimen.basick.utilk.androidx.lifecycle.handleLifecycleEventOnStop
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName LifecycleOwnerProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/29
 * @Version 1.0
 */
@OApiInit_ByLazy
class LifecycleOwnerProxy : LifecycleOwner, IUtilK {
    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    ///////////////////////////////////////////////////////////////////////

    fun onCreate() {
        UtilKLogWrapper.dt(TAG, "onCreate: ")
        lifecycleRegistry.handleLifecycleEventOnCreate()
    }

    fun onStart() {
        UtilKLogWrapper.dt(TAG, "onStart: ")
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    fun onResume() {
        UtilKLogWrapper.dt(TAG, "onResume: ")
        lifecycleRegistry.handleLifecycleEventOnResume()
    }

    fun onPause() {
        UtilKLogWrapper.dt(TAG, "onPause: ")
        lifecycleRegistry.handleLifecycleEventOnPause()
    }

    fun onStop() {
        UtilKLogWrapper.dt(TAG, "onStop: ")
        lifecycleRegistry.handleLifecycleEventOnStop()
    }

    fun onDestroy() {
        UtilKLogWrapper.dt(TAG, "onDestroy: ")
        lifecycleRegistry.handleLifecycleEventOnDestroy()
    }

    ///////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}