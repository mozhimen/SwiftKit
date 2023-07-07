package com.mozhimen.basick.elemk.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver

/**
 * @ClassName RegisterDelegate
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/16 22:40
 * @Version 1.0
 */
@AOptLazyInit
class ResultLauncherDelegate<T>() : BaseWakeBefDestroyLifecycleObserver() {
    private var _activityResultLauncher: ActivityResultLauncher<T>? = null

    fun getResultLauncher(): ActivityResultLauncher<T>? =
        _activityResultLauncher

    override fun onDestroy(owner: LifecycleOwner) {
        _activityResultLauncher?.unregister()
        super.onDestroy(owner)
    }
}