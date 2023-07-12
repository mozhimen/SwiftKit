package com.mozhimen.basick.elemk.androidx.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle

/**
 * @ClassName ResultLauncherProxy
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/16 22:40
 * @Version 1.0
 */
@ALintKOptIn_ApiCall_BindLifecycle
@ALintKOptIn_ApiInit_ByLazy
class ActivityResultLauncherProxy<T>() : BaseWakeBefDestroyLifecycleObserver() {
    private var _activityResultLauncher: ActivityResultLauncher<T>? = null

    fun getResultLauncher(): ActivityResultLauncher<T>? =
        _activityResultLauncher

    override fun onDestroy(owner: LifecycleOwner) {
        _activityResultLauncher?.unregister()
        super.onDestroy(owner)
    }
}