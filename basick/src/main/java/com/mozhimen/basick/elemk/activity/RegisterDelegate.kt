package com.mozhimen.basick.elemk.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver

/**
 * @ClassName RegisterDelegate
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/16 22:40
 * @Version 1.0
 */
class RegisterDelegate<T>() : BaseWakeBefDestroyLifecycleObserver() {
    private var _resultLauncher: ActivityResultLauncher<T>? = null

    fun getResultLauncher(): ActivityResultLauncher<T>? =
        _resultLauncher

    override fun onCreate(owner: LifecycleOwner) {

    }

    override fun onDestroy(owner: LifecycleOwner) {
        _resultLauncher?.unregister()
        super.onDestroy(owner)
    }
}