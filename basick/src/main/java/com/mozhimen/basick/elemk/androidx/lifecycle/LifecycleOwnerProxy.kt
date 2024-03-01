package com.mozhimen.basick.elemk.androidx.lifecycle

import android.util.Log
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
import com.mozhimen.basick.utilk.bases.IUtilK

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
        Log.d(TAG, "onCreate: ")
        lifecycleRegistry.handleLifecycleEventOnCreate()
    }

    fun onStart() {
        Log.d(TAG, "onStart: ")
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    fun onResume() {
        Log.d(TAG, "onResume: ")
        lifecycleRegistry.handleLifecycleEventOnResume()
    }

    fun onPause() {
        Log.d(TAG, "onPause: ")
        lifecycleRegistry.handleLifecycleEventOnPause()
    }

    fun onStop() {
        Log.d(TAG, "onStop: ")
        lifecycleRegistry.handleLifecycleEventOnStop()
    }

    fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        lifecycleRegistry.handleLifecycleEventOnDestroy()
    }

    ///////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}