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

    fun onCreate(name: String) {
        Log.v(TAG, "onCreate:  $name")
        lifecycleRegistry.handleLifecycleEventOnCreate()
    }

    fun onStart(name: String) {
        Log.v(TAG, "onStart:   $name")
        lifecycleRegistry.handleLifecycleEventOnStart()
    }

    fun onResume(name: String) {
        Log.v(TAG, "onResume:  $name")
        lifecycleRegistry.handleLifecycleEventOnResume()
    }

    fun onPause(name: String) {
        Log.v(TAG, "onPause:   $name")
        lifecycleRegistry.handleLifecycleEventOnPause()
    }

    fun onStop(name: String) {
        Log.v(TAG, "onStop:    $name")
        lifecycleRegistry.handleLifecycleEventOnStop()
    }

    fun onDestroy(name: String) {
        Log.v(TAG, "onDestroy: $name")
        lifecycleRegistry.handleLifecycleEventOnDestroy()
    }

    ///////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}